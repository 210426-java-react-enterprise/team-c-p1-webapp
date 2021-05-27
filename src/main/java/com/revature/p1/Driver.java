package com.revature.p1;


import com.revature.p1.utilities.Controller;

public class Driver
{

    public static void main(String[] args)
    {
//        List<Class<?>> list = new ArrayList<>();
//        list.add(Credential.class);
//        SchemaCreator creator = new SchemaCreator(list);
//        creator.createSchema();
        Controller controller = new Controller();
        controller.run();
//
//
//
//        List<Class<?>> classes = new ArrayList<>();
//        classes.add(Customer.class);
//        classes.add(Account.class);
//        classes.add(Transaction.class);
//
//        SchemaCreator schemaCreator = new SchemaCreator(classes);
//        schemaCreator.createSchema();
//
////******************************************************************************************************************************************
//
//        Customer sean = new Customer("sean", "taba", "458745698", "sean@gmail.com", "4758695478");
//        Customer jon =  new Customer("jon","doe","784125478","jon@yahoo.com","6541254789");
//        Credential seanCredential = new Credential("seantaba","password", "458965748");
//        Credential jonCredential  = new Credential("jondoe", "password", "124536254");
//
//        Transaction seanTransaction = new Transaction("deposit", 25.36, 540.25, "101");
//        Transaction jonTransaction = new Transaction("withdraw", 125.78, 2547.49, "124");
//
//        List<MySavable> savables = new ArrayList<>();
//
//        savables.add(sean);
//        savables.add(jon);
//        savables.add(seanCredential);
//        savables.add(jonCredential);
//        savables.add(seanTransaction);
//        savables.add(jonTransaction);
//
//        ObjectSaver<MySavable> saver = new ObjectSaver<>(savables);
//        saver.saveData();
//
//        ObjectRetriever loader = new ObjectRetriever(savables);
//        loader.loadObject();
    }


}
