package com.revature.daos;

import java.util.List;

import com.revature.models.Transaction;
import com.revature.p1.utils.EntityManager;

public class UserDAO {

    private EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }

//    public List<Transaction> loadTransactions() {
//
//    }
}
