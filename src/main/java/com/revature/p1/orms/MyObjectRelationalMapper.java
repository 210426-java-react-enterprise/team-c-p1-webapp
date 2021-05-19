package com.revature.p1.orms;

import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;
import com.revature.p1.annotations.MyEntity;
import com.revature.p1.entities.Credential;
import com.revature.p1.entities.MySavable;
import com.revature.p1.exceptions.NotSavableObjectException;
import com.revature.p1.persistance.ConnectionManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class MyObjectRelationalMapper<T>
{
    private Connection connection;

    public MyObjectRelationalMapper(Connection connection)
    {
        this.connection = connection;
    }

    public T read(T savable) throws SQLException
    {
        List<Field> fields = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try
        {
            if (!savable.getClass().isAnnotationPresent(MyEntity.class))
                throw new NotSavableObjectException("Object is not a 'Savable' type!");
            fields.addAll(Arrays.asList(savable.getClass().getDeclaredFields()));
            List<Field> pks = fields.stream()
                                    .filter(field -> field.isAnnotationPresent(MyColumn.class) &&
                                            field.getAnnotation(MyColumn.class).pk())
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
            sb.append("SELECT * FROM ").append("project1.").append(savable.getClass().getAnnotation(MyEntity.class).name())
              .append(" WHERE ").append(pks.get(0).getAnnotation(MyColumn.class).name()).append(" = ")
              .append(pks.get(0).getAnnotation(MyColumn.class).type() == ColumnType.VARCHAR ? "'" : "")
              .append(pks.get(0).get(savable))
              .append(pks.get(0).getAnnotation(MyColumn.class).type() == ColumnType.VARCHAR ? "'" : "");
        } catch (IllegalAccessException | NotSavableObjectException e)
        {
            e.printStackTrace();
        }

        System.out.println(sb);
        //((Credential)savable).setPassword(sb.toString());

        PreparedStatement statement = connection.prepareStatement(sb.toString());
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
        {
            List<Method> methods = new ArrayList<>(Arrays.asList(savable.getClass().getDeclaredMethods()));
            fields.forEach(field ->
                           {
                               methods.forEach(method ->
                                               {
                                                   if (method.getName().toLowerCase(Locale.ROOT).startsWith("set"))
                                                   {
                                                       String methodName = method.getName().toLowerCase(Locale.ROOT).substring(3);
                                                       if (field.getName().toLowerCase(Locale.ROOT).equals(methodName))
                                                       {
                                                           System.out.println("field: " + field.getName() + "\tmethod: " + method.getName());
                                                           try
                                                           {
                                                               field.setAccessible(true);
                                                               method.invoke(savable, resultSet.getString(field.getAnnotation(MyColumn.class).name()));
                                                               field.setAccessible(false);
                                                           } catch (IllegalAccessException | SQLException | InvocationTargetException e)
                                                           {
                                                               e.printStackTrace();
                                                           }
                                                       }
                                                   }
                                               });
                           });
        }
        return savable;
    }

}
