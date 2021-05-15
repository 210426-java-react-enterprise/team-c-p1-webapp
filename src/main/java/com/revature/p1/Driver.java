package com.revature.p1;


import com.revature.p1.annotations.MyEntity;
import com.revature.p1.annotations.MyField;
import com.revature.p1.entities.Person;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Driver
{

    public static void main(String[] args) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException
    {
        Person person = new Person();
        guessWho(person);
    }

    public static void guessWho(Object o) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException
    {
        Class<?> objectClass = Objects.requireNonNull(o.getClass());

        if (containsCorrectAnnotations(objectClass))
        {
            Annotation[] classAnnotations = objectClass.getAnnotations();
            if (classAnnotations[0].annotationType().getSimpleName().equals("Entity"))
            {
                Field[] classFields = objectClass.getFields();
                for (Field classField : classFields)
                {
                    System.out.println(classField.getName());
                }

            }


        } else System.out.println("Class is not annotated!");
    }

    public static boolean containsCorrectAnnotations(Class<?> clazz) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException
    {
        List<Annotation> classAnnotations = new ArrayList<>(Arrays.asList(clazz.getAnnotations()));
        List<Field> classFields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        List<List<Annotation>> fieldsAnnotations = new ArrayList<>();

        for (Field classField : classFields)
        {
            List<Annotation> fieldAnnotations = new ArrayList<>(Arrays.asList(classField.getDeclaredAnnotations()));
            fieldsAnnotations.add(fieldAnnotations);
        }

        System.out.println("\nClass: '" + clazz.getSimpleName() + "'");
        System.out.println("\nClass annotations: ");

        for (Annotation classAnnotation : classAnnotations)
        {

            System.out.println("\t" + classAnnotation.annotationType().getSimpleName());
            if (classAnnotation instanceof MyEntity annotation)
            {
                System.out.println("\t\t" + annotation.name());
            }
        }

        System.out.println("\nFields and field annotations: ");

        for (int i = 0; i < classFields.size(); i++)
        {
            System.out.println("Field: " + classFields.get(i).getType().getSimpleName() + ": '" + classFields.get(i).getName() + "'");
            System.out.println("\tField annotations: ");
            for (Annotation annotation : fieldsAnnotations.get(i))
            {
                System.out.println("\t\tAnnotation type: " + annotation.annotationType().getSimpleName());
                if (annotation instanceof MyField)
                System.out.println("\t\t\tAnnotation parameter: "  + ((MyField)annotation).name());
            }
            System.out.println();
        }



        return true;
    }
}
