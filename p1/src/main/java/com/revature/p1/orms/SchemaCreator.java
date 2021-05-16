package com.revature.p1.orms;

import com.revature.p1.entities.Column;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemaCreator
{
    private Map<String, List<Column>> tables;
    private List<Class<?>> classes;

    public SchemaCreator(List<Class<?>> classes)
    {
        this.tables = new HashMap<>();
        this.classes = classes;
    }

    public boolean createSchema()
    {


        return false;
    }


}
