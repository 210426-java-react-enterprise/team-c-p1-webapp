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

public class ObjectSaver<T>
{
    private List<MySavable> objects;

    public ObjectSaver(List<MySavable> objects)
    {
        this.objects = objects;
    }

    public boolean saveData()
    {
        System.out.println("************ saveData() queries ************");
        objects.forEach(object -> {
            if (!object.getClass().isAnnotationPresent(MyEntity.class))
                throw new NotSavableObjectException("Object provided to saveData() is not 'Savable'!");
            List<Field> fields = new ArrayList<>(Arrays.asList(object.getClass().getDeclaredFields()));
            System.out.println(saveQuery(fields,object));
        });
        System.out.println("********************************************");
        return false;
    }

    private String saveQuery(List<Field> fields, MySavable object)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(object.getClass().getAnnotation(MyEntity.class).name()).append(" (");
        fields.forEach(field -> {
            if (field.isAnnotationPresent(MyColumn.class))
            sb.append(field.getAnnotation(MyColumn.class).name()).append(",");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");
        fields.forEach(field -> {
            try
            {
                if (field.isAnnotationPresent(MyColumn.class))
                {
                    sb.append(field.getAnnotation(MyColumn.class).type() == ColumnType.VARCHAR ? "'" : "");
                    field.setAccessible(true);
                    sb.append(field.get(object)).append(field.getAnnotation(MyColumn.class).type() == ColumnType.VARCHAR ? "'," : ",");
                    field.setAccessible(false);
                }
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        return sb.toString();

    }
}
