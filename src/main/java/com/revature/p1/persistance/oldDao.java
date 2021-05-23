package com.revature.p1.persistance;

public class oldDao
{
//    private final Connection connection;
//
//    public DAO(Connection connection)
//    {
////        Class.forName("org.postgresql.Driver");
//        this.connection = connection;
//    }
///*
//    This method checks the database to find out if the username is already used.
// */
//    public boolean tryNewUsername(String username) throws SQLException
//    {
//        Statement statement = connection.createStatement();
//        String query = "select user_name from project0.credentials where user_name = '" + username + "'";
//
//        ResultSet result = statement.executeQuery(query);
//        return result.next();
//    }
//
//    /*
//    This method checks the database to find out if the SSN has been used before.
//     */
//    public boolean tryNewSSN(String ssn) throws SQLException
//    {
//        Statement statement = connection.createStatement();
//        String query = "select ssn from project0.customers where ssn = '" + ssn + "'";
//
//        ResultSet result = statement.executeQuery(query);
//        return result.next();
//    }
//
//    /*
//    This method adds a new customer to the database.
//     */
//    public void addCustomer(Customer customer) throws SQLException
//    {
//        String sqlInsertNewCustomer = "insert into project0.customers (first_name,last_name,ssn,email,phone) values (?,?,?,?,?);";
//        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertNewCustomer);
//        preparedStatement.setString(1, customer.getFirstName());
//        preparedStatement.setString(2, customer.getLastName());
//        preparedStatement.setString(3, customer.getSsn());
//        preparedStatement.setString(4, customer.getEmail());
//        preparedStatement.setString(5, customer.getPhone());
//
//        int rowsAffected = preparedStatement.executeUpdate();
//        if (rowsAffected == 0) System.out.println("Something went wrong!");
//
//        sqlInsertNewCustomer = "insert into project0.credentials (user_name,password,customer_ssn) values (?,?,?);";
//        preparedStatement = connection.prepareStatement(sqlInsertNewCustomer);
//        preparedStatement.setString(1, customer.getUsername());
//        preparedStatement.setString(2, customer.getPassword());
//        preparedStatement.setString(3, String.valueOf(customer.getSsn()));
//
//        rowsAffected = preparedStatement.executeUpdate();
//
//        if (rowsAffected == 0) System.out.println("Something went wrong!");
//
//        sqlInsertNewCustomer = "insert into project0.addresses (unit,street,city,\"state\",zip,customer_ssn) values (?,?,?,?,?,?)";
//        preparedStatement = connection.prepareStatement(sqlInsertNewCustomer);
//        preparedStatement.setString(1, customer.getUnit());
//        preparedStatement.setString(2, customer.getStreet());
//        preparedStatement.setString(3, customer.getCity());
//        preparedStatement.setString(4, customer.getState());
//        preparedStatement.setString(5, customer.getZip());
//        preparedStatement.setString(6, customer.getSsn());
//
//        rowsAffected = preparedStatement.executeUpdate();
//
//        if (rowsAffected == 0) System.out.println("Something went wrong!");
//
//    }
//
//    /*
//    This method checks to find out if the entered password matches the password stored in the database.
//     */
//    public String isCorrectPassword(String username) throws SQLException
//    {
//        String query = "select * from project0.credentials where user_name = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//        preparedStatement.setString(1, username);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        if (resultSet.next())
//        {
//            return resultSet.getString("password");
//        }
//        return null;
//    }
//
//    /*
//    This method pulls all the user information from the database and builds a customer object.
//     */
//    public Customer getCustomer(String username) throws SQLException, IllegalInputException
//    {
//        String query_1 =
//                "select  customers.first_name,customers.last_name,customers.ssn,customers.email,customers.phone," +
//                        "addresses.unit,addresses.street,addresses.city,addresses.state,addresses.zip," +
//                        "credentials.password " +
//                        "from project0.credentials " +
//                        "join project0.addresses on project0.credentials.customer_ssn = project0.addresses.customer_ssn " +
//                        "join project0.customers on project0.credentials.customer_ssn = project0.customers.ssn " +
//                        "where project0.credentials.user_name = '" + username + "'";
//
//        String query_2 =
//                "select * " +
//                        "from   project0.accounts " +
//                        "where  project0.accounts.user_name = ?";
//
//        PreparedStatement preparedStatement_1 = connection.prepareStatement(query_1);
//        PreparedStatement preparedStatement_2 = connection.prepareStatement(query_2);
//        preparedStatement_2.setString(1, username);
//
//        ResultSet resultSet_1 = preparedStatement_1.executeQuery();
//        ResultSet resultSet_2 = preparedStatement_2.executeQuery();
//
//        MyList<String> accounts = new MyList<>();
//        Customer customer = null;
//        if (resultSet_1.next())
//        {
//            customer = new Customer(resultSet_1.getString("first_name"), resultSet_1.getString("last_name"), resultSet_1.getString("ssn"),
//                                    resultSet_1.getString("email"), resultSet_1.getString("phone"), username, resultSet_1.getString("password"),
//                                    resultSet_1.getString("unit"), resultSet_1.getString("street"), resultSet_1.getString("city"),
//                                    resultSet_1.getString("state"), resultSet_1.getString("zip"));
//        }
//        //all account numbers for the customer
//        while (resultSet_2.next() && customer != null)
//        {
//            String accountType = resultSet_2.getString("account");
//            String number = resultSet_2.getString("number");
//
//            Account account = null;
//            switch (accountType)
//            {
//                case "checking":
//                    account = new CheckingAccount(number);
//                    break;
//                case "savings":
//                    account = new SavingsAccount(number);
//                    break;
//                case "trust":
//                    account = new TrustAccount(number);
//                    break;
//                default:
//                    throw new RuntimeException("Illegal account type");
//            }
//            String query = "select * from project0.transactions where account_number = '" + number + "'";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next())
//            {
//                account.getTransactions().add(new Transaction(resultSet.getString("type"), resultSet.getDouble("amount"),
//                                                              resultSet.getDouble("balance")));
//            }
//            account.updateBalance();
//            customer.getAccounts().add(account);
//        }
//        return customer;
//    }
//
//    /*
//    This method adds a new bank account to the database for a customer.
//     */
//    public String addAccount(String identifier) throws SQLException, IllegalInputException
//    {
//        int lastTableNumber = 0;
//        String query = "select max(number) from project0.accounts";
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        if (resultSet.next())
//        {
//            lastTableNumber = resultSet.getInt(1);
//        }
//
//        query = "insert into project0.accounts (user_name,account,customer_ssn,number) values(?,?,?,?);";
//        PreparedStatement preparedStatement1 = connection.prepareStatement(query);
//        preparedStatement1.setString(1, CurrentCustomer.getInstance().getCustomer().getUsername());
//        preparedStatement1.setString(2, identifier);
//        preparedStatement1.setString(3, CurrentCustomer.getInstance().getCustomer().getSsn());
//        preparedStatement1.setInt(4, ++lastTableNumber);
//
//        preparedStatement1.executeUpdate();
//        CurrentCustomer.getInstance().setCustomer(getCustomer(CurrentCustomer.getInstance().getCustomer().getUsername()));
//        return String.valueOf(lastTableNumber);
//
//    }
//
//    /*
//    This method pulls data from the database and updates the local account object state.
//     */
//    public void updateAccount(Account account) throws SQLException
//    {
//        String query = "insert into project0.transactions (type,amount,balance,account_number,customer_ssn) values (?,?,?,?,?)";
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//        preparedStatement.setString(1, account.getTransactions().at(account.getTransactions().size() - 1).getType());
//        preparedStatement.setDouble(2, account.getTransactions().at(account.getTransactions().size() - 1).getAmount());
//        preparedStatement.setDouble(3, account.getTransactions().at(account.getTransactions().size() - 1).getBalance());
//        preparedStatement.setString(4, account.getNumber());
//        preparedStatement.setString(5, CurrentCustomer.getInstance().getCustomer().getSsn());
//        preparedStatement.executeUpdate();
//
//    }
}
