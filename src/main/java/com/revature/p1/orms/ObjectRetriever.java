package com.revature.p1.orms;

import com.revature.p1.annotations.ColumnType;
import com.revature.p1.annotations.MyColumn;
import com.revature.p1.annotations.MyEntity;
import com.revature.p1.entities.MySavable;
import com.revature.p1.exceptions.NotSavableObjectException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectRetriever
{
    private List<MySavable> objects;

    public ObjectRetriever(List<MySavable> objects)
    {
        this.objects = objects;
    }

    public boolean loadObject()
    {
        objects.forEach(object ->
                        {
                            StringBuilder sb = new StringBuilder();
                            try
                            {
                                if (!object.getClass().isAnnotationPresent(MyEntity.class))
                                    throw new NotSavableObjectException("Object is not a 'Savable' type!");
                                List<Field> fields = new ArrayList<>(Arrays.asList(object.getClass().getDeclaredFields()));
                                List<Field> pks = fields.stream()
                                                        .filter(field -> field.isAnnotationPresent(MyColumn.class) &&
                                                                field.getAnnotation(MyColumn.class).pk())
                                                        .filter(field ->
                                                                {
                                                                    field.setAccessible(true);
                                                                    try
                                                                    {
                                                                        return field.get(object) != null;
                                                                    } catch (IllegalAccessException e)
                                                                    {
                                                                        e.printStackTrace();
                                                                    }
                                                                    return false;
                                                                })
                                                        .collect(Collectors.toList());
                                if (pks.size() == 0) return;
                                sb.append("SELECT * FROM ").append(object.getClass().getAnnotation(MyEntity.class).name())
                                  .append(" WHERE ").append(pks.get(0).getAnnotation(MyColumn.class).name()).append(" = ")
                                  .append(pks.get(0).getAnnotation(MyColumn.class).type() == ColumnType.VARCHAR ? "'" : "")
                                  .append(pks.get(0).get(object))
                                  .append(pks.get(0).getAnnotation(MyColumn.class).type() == ColumnType.VARCHAR ? "'" : "");
                            } catch (IllegalAccessException e)
                            {
                                e.printStackTrace();
                            }

                            System.out.println(sb);

                        });


        return false;
    }
}
