package com.revature.p1.orms;

import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;
import com.revature.p1.annotations.MyEntity;
import com.revature.p1.entities.MySavable;
import com.revature.p1.exceptions.NotSavableObjectException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class MyObjectRelationalMapper
{
    private final Connection connection;


    public MyObjectRelationalMapper(Connection connection)
    {
        this.connection = connection;
    }

    public MySavable readRow(MySavable savable) throws SQLException
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            if (!savable.getClass()
                        .isAnnotationPresent(MyEntity.class))
            {
                throw new NotSavableObjectException("Object is not a 'Savable' type!");
            }
            List<Field> fields = new ArrayList<>(Arrays.asList(savable.getClass()
                                                                      .getDeclaredFields()));
            List<Field> pks = fields.stream()
                                    .filter(field -> field.isAnnotationPresent(MyColumn.class) &&
                                                             field.getAnnotation(MyColumn.class)
                                                                  .pk())
                                    .filter(field ->
                                                {
                                                field.setAccessible(true);
                                                try
                                                {
                                                    return field.get(savable) != null;
                                                } catch (IllegalAccessException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                return false;
                                                })
                                    .collect(Collectors.toList());
            if (pks.size() == 0) return savable;
            sb.append("SELECT * FROM project1.")
              .append(savable.getClass()
                             .getAnnotation(MyEntity.class)
                             .name())
              .append(" WHERE ")
              .append(pks.get(0)
                         .getAnnotation(MyColumn.class)
                         .name())
              .append(" = ")
              .append(pks.get(0)
                         .getAnnotation(MyColumn.class)
                         .type() == ColumnType.VARCHAR ? "'" : "")
              .append(pks.get(0)
                         .get(savable))
              .append(pks.get(0)
                         .getAnnotation(MyColumn.class)
                         .type() == ColumnType.VARCHAR ? "'" : "");
        } catch (IllegalAccessException | NotSavableObjectException e)
        {
            e.printStackTrace();
        }

        System.out.println(sb);
        //((Credential)savable).setPassword(sb.toString());

        PreparedStatement statement = connection.prepareStatement(sb.toString());
        ResultSet resultSet = statement.executeQuery();

        return buildSavables(savable, resultSet)
                       .get(0);
    }

    public List<MySavable> readRows(MySavable savable) throws SQLException
    {
        StringBuilder sb = new StringBuilder();

        if (!savable.getClass()
                    .isAnnotationPresent(MyEntity.class))
        {
            throw new NotSavableObjectException("Object is not a 'Savable' type!");
        }
        List<Field> fields = new ArrayList<>(Arrays.asList(savable.getClass()
                                                                  .getDeclaredFields()));
        sb
                .append("SELECT * FROM project1.")
                .append(savable.getClass()
                               .getAnnotation(MyEntity.class)
                               .name())
                .append(" WHERE ");
        sb.append(Objects.requireNonNull(findCondition(savable, fields)));

        PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
        ResultSet resultSet = preparedStatement.executeQuery();

        return buildSavables(savable, resultSet);
    }

    private String findCondition(MySavable savable, List<Field> fields)
    {
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(MyColumn.class))
            {
                try
                {
                    switch (field.getAnnotation(MyColumn.class)
                                 .type())
                    {
                        case VARCHAR:
                            field.setAccessible(true);
                            if (!field.get(savable)
                                      .equals(""))
                            {
                                String returnString = field.getAnnotation(MyColumn.class)
                                                           .name() + "='" + field.get(savable) + "'";
                                field.setAccessible(false);
                                return (returnString);
                            }
                            field.setAccessible(false);
                            break;
                        case INT:
                        case SERIAL:
                        case DECIMAL:
                            field.setAccessible(true);
                            if ((double) field.get(savable) != 0)
                            {
                                String returnString = field.getAnnotation(MyColumn.class)
                                                           .name() + "=" + field.get(savable);
                                field.setAccessible(false);
                                return returnString;
                            }
                            field.setAccessible(false);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private List<MySavable> buildSavables(MySavable savable, ResultSet resultSet)
    {
        List<Field> fields = new ArrayList<>(Arrays.asList(savable.getClass()
                                                                  .getDeclaredFields()));
        List<MySavable> savables = new ArrayList<>();
        try
        {
            while (resultSet.next())
            {
                List<Method> methods = new ArrayList<>(Arrays.asList(savable.getClass()
                                                                            .getDeclaredMethods()));
                fields.forEach(field ->
                                   {
                                   if (field.isAnnotationPresent(MyColumn.class))
                                   {
                                       methods.forEach(method ->
                                                           {
                                                           if (method.getName()
                                                                     .toLowerCase(Locale.ROOT)
                                                                     .startsWith("set"))
                                                           {
                                                               String methodName = method.getName()
                                                                                         .toLowerCase(Locale.ROOT)
                                                                                         .substring(3);
                                                               if (field.getName()
                                                                        .toLowerCase(Locale.ROOT)
                                                                        .equals(methodName))
                                                               {
                                                                   System.out.println("field: " + field.getName() + "\tmethod: " + method.getName());
                                                                   try
                                                                   {
                                                                       switch (field.getAnnotation(MyColumn.class)
                                                                                    .type())
                                                                       {
                                                                           case VARCHAR:
                                                                               field.setAccessible(true);
                                                                               method.invoke(savable,
                                                                                             resultSet.getString(field.getAnnotation(MyColumn.class)
                                                                                                                      .name()));
                                                                               field.setAccessible(false);
                                                                               break;
                                                                           case INT:
                                                                           case SERIAL:
                                                                               field.setAccessible(true);
                                                                               method.invoke(savable,
                                                                                             resultSet.getInt(field.getAnnotation(MyColumn.class)
                                                                                                                   .name()));
                                                                               field.setAccessible(false);
                                                                           case DECIMAL:
                                                                               field.setAccessible(true);
                                                                               method.invoke(savable,
                                                                                             resultSet.getDouble(field.getAnnotation(MyColumn.class)
                                                                                                                      .name()));
                                                                               field.setAccessible(false);
                                                                       }

                                                                       //method.invoke(savable, resultSet.getString(field.getAnnotation(MyColumn.class).name()));
                                                                       field.setAccessible(false);

                                                                   } catch (IllegalAccessException | InvocationTargetException | SQLException e)
                                                                   {
                                                                       e.printStackTrace();
                                                                   }
                                                               }
                                                           }
                                                           });
                                   }
                                   });
                Class<?> clazz = Class.forName(savable.getClass()
                                                      .getName());
                Object newObject = clazz.getConstructor(MySavable.class)
                                        .newInstance(savable);
                savables.add((MySavable) newObject);
            }
        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                         InvocationTargetException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return savables;
    }

    public int saveData(List<MySavable> savables)
    {
        int rowsAffected = 0;
        StringBuilder headBuilder = new StringBuilder();
        StringBuilder tailBuilder = new StringBuilder();
        savables.forEach(object ->
                             {
                             if (!object.getClass()
                                        .isAnnotationPresent(MyEntity.class))
                             {
                                 throw new NotSavableObjectException("Not a savable object!");
                             }
                             String tableName = object.getClass()
                                                      .getAnnotation(MyEntity.class)
                                                      .name();
                             headBuilder.append("INSERT INTO project1.")
                                        .append(tableName)
                                        .append(" (");
                             tailBuilder.append(" VALUES (");
                             List<Field> fields = Arrays.asList(object.getClass()
                                                                      .getDeclaredFields());
                             fields.forEach(field ->
                                                {
                                                if (!field.isAnnotationPresent(MyColumn.class))
                                                {
                                                    throw new NotSavableObjectException("Object not properly annotated!");
                                                }
                                                String columnName = field.getAnnotation(MyColumn.class)
                                                                         .name();
                                                headBuilder.append(columnName)
                                                           .append(",");
                                                try
                                                {
                                                    switch (field.getAnnotation(MyColumn.class)
                                                                 .type())
                                                    {
                                                        case VARCHAR:
                                                            field.setAccessible(true);
                                                            tailBuilder.append("'")
                                                                       .append(field.get(object))
                                                                       .append("'");
                                                            field.setAccessible(false);
                                                            break;
                                                        case INT:
                                                        case SERIAL:
                                                        case DECIMAL:
                                                            field.setAccessible(true);
                                                            tailBuilder.append(field.get(object))
                                                                       .append(",");
                                                            field.setAccessible(false);
                                                    }
                                                } catch (Exception e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                });
                             headBuilder.deleteCharAt(headBuilder.length() - 1);
                             tailBuilder.deleteCharAt(tailBuilder.length() - 1);
                             headBuilder.append(")");
                             tailBuilder.append(")");
                             });
        headBuilder.append(tailBuilder);
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(headBuilder.toString());
            rowsAffected += preparedStatement.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}






















