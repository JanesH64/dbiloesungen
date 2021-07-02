package com.company;

import java.io.*;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class DatabaseService {
    private final String user;
    private final String password;
    private final String url;

    private BufferedReader stdin;

    public DatabaseService() {
        var secrets = ReadSecretsFile();

        user = secrets[0];
        password = secrets[1];
        url = secrets[2];

        stdin = new BufferedReader(new InputStreamReader(System.in));
    }


    public void TestConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("Select * from dbi.customers;");

            while(rs.next())
            {
                System.out.print(rs.getString("cid"));
                System.out.print(" | ");
                System.out.print(rs.getString("cname"));
                System.out.print(" | ");
                System.out.print(rs.getString("city"));
                System.out.print(" | ");
                System.out.print(rs.getString("discnt"));
                System.out.println();
            }

            rs.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void TestCustomerOverview() throws SQLException {

        Connection         conn = null;
        PreparedStatement  stmt = null;
        ResultSet          rs   = null;

        try
        {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            System.out.println("\nConnected to sample database!\n");

            stmt = conn.prepareStatement(
                    "select agents.aid, agents.aname, sum(dollars) " +
                            "from dbi.orders, dbi.agents " +
                            "where cid=? and orders.aid=agents.aid " +
                            "group by agents.aid, agents.aname" );

            String customerId = getInput("Please enter customer id: ");

            while ((customerId!=null) && (customerId.length()!=0))
            {
                stmt.setString(1,customerId);
                rs = stmt.executeQuery();

                System.out.println();
                System.out.println("AID|        ANAME|    DOLLARS");
                System.out.println("---|-------------|-----------");

                while (rs.next())
                {
                    System.out.println(rs.getString(1) + "\t\t" +
                            rs.getString(2) + "\t\t" + rs.getDouble(3));
                }
                rs.close();
                conn.commit();
                stmt.clearParameters();
                customerId = getInput("\nPlease enter customer id: ");
            }

            stmt.close();
            conn.close();
            System.out.println("\nDisconnected!\n");

        }
        catch (SQLException e)
        {
            System.err.println(e.toString());
            System.exit(1);
        }
        finally // close used resources
        {
            if (rs!=null)   rs.close();
            if (stmt!=null) stmt.close();
            if (conn!=null) conn.close();
        }
    }


    public void FillCustomersTable() throws SQLException
    {
        Connection         conn = null;
        PreparedStatement  stmt = null;

        try
        {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            System.out.println("\nConnected to sample database!\n");

            stmt = conn.prepareStatement(
                    "Insert into dbi.customers (cid, cname, city, discnt) values (? , ? , ? , ?)");

            String customerId;
            String name;
            String city;
            double discnt;
            var rnd = new Random();

            System.out.println("Filling Customers...");

            for(int i = 0; i <  10000; i++)
            {
                customerId = Integer.toString(i);
                name = "Customer " + i;
                city = "City " + i;
                discnt = rnd.nextDouble();

                stmt.setString(1,customerId);
                stmt.setString(2,name);
                stmt.setString(3,city);
                stmt.setDouble(4,discnt);

                stmt.executeUpdate();
            }

            conn.commit();
            stmt.clearParameters();

            System.out.println("Done!");
        }
        catch (SQLException e)
        {
            System.err.println(e.toString());
            System.exit(1);
        }
        finally // close used resources
        {
            stmt.close();
            conn.close();
            System.out.println("\nDisconnected!\n");
        }
    }


    public void FillAgentsTable() throws SQLException
    {
        Connection         conn = null;
        PreparedStatement  stmt = null;

        try
        {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            System.out.println("\nConnected to sample database!\n");

            stmt = conn.prepareStatement(
                    "Insert into dbi.agents (aid, aname, city, percent) values (? , ? , ? , ?)");

            String agentId;
            String name;
            String city;
            double percent;
            var rnd = new Random();

            System.out.println("Filling Agents...");

            for(int i = 0; i <  100; i++)
            {
                agentId = Integer.toString(i);
                name = "Agent " + i;
                city = "City " + i;
                percent = rnd.nextDouble();

                stmt.setString(1,agentId);
                stmt.setString(2,name);
                stmt.setString(3,city);
                stmt.setDouble(4,percent);

                stmt.executeUpdate();


            }

            conn.commit();
            stmt.clearParameters();

            System.out.println("Done!");
        }
        catch (SQLException e)
        {
            System.err.println(e.toString());
            System.exit(1);
        }
        finally // close used resources
        {
            stmt.close();
            conn.close();
            System.out.println("\nDisconnected!\n");
        }
    }


    public void FillProductsTable() throws SQLException
    {
        Connection         conn = null;
        PreparedStatement  stmt = null;

        try
        {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            System.out.println("\nConnected to sample database!\n");

            stmt = conn.prepareStatement(
                    "Insert into dbi.products (pid, pname, city, quantity, price) values (? , ? , ? , ? , ?)");

            String productId;
            String name;
            String city;
            int quantity = 200;
            double price = 0.5;
            var rnd = new Random();

            System.out.println("Filling Products...");

            for(int i = 0; i <  5; i++)
            {
                productId = Integer.toString(i);
                name = "Product " + i;
                city = "City " + i;

                stmt.setString(1,productId);
                stmt.setString(2,name);
                stmt.setString(3,city);
                stmt.setInt(4,quantity);
                stmt.setDouble(5,price);

                stmt.executeUpdate();
            }

            conn.commit();
            stmt.clearParameters();

            System.out.println("Done!");
        }
        catch (SQLException e)
        {
            System.err.println(e.toString());
            System.exit(1);
        }
        finally // close used resources
        {
            stmt.close();
            conn.close();
            System.out.println("\nDisconnected!\n");
        }
    }


    public void FillOrdersTable() throws SQLException
    {
        Connection         conn = null;
        PreparedStatement  stmt = null;

        try
        {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            System.out.println("\nConnected to sample database!\n");

            stmt = conn.prepareStatement(
                    "Insert into dbi.orders (ordno, month, cid, aid, pid, qty, dollars) values (? , ? , ? , ? , ? , ? , ?)");

            int orderNumber;
            String month = "Aug";
            String agentId;
            String customerId;
            String productId;
            int quantity;
            double dollars = 100;
            var rnd = new Random();

            System.out.println("Filling Orders...");

            for(int i = 0; i <  1000000; i++)
            {

                orderNumber =  i;
                agentId = Integer.toString(rnd.nextInt((96-0) + 1));
                customerId = Integer.toString(rnd.nextInt((9996-0) + 1));
                productId = Integer.toString(rnd.nextInt(2 + 1));
                quantity = rnd.nextInt();

                stmt.setInt(1,orderNumber);
                stmt.setString(2,month);
                stmt.setString(3,customerId);
                stmt.setString(4,agentId);
                stmt.setString(5,productId);
                stmt.setDouble(6,quantity);
                stmt.setDouble(7,dollars);

                stmt.executeUpdate();

                if(i == 250000 || i == 500000 || i == 750000) {
                    conn.commit();
                }

                System.out.println("OrderNumber: "+i);
            }

            conn.commit();
            stmt.clearParameters();

            System.out.println("Done!");
        }
        catch (SQLException e)
        {
            System.err.println(e.toString());
            System.exit(1);
        }
        finally // close used resources
        {
            stmt.close();
            conn.close();
            System.out.println("\nDisconnected!\n");
        }
    }


    public void DeleteTable(String table) throws SQLException
    {
        Connection         conn = null;
        PreparedStatement  stmt = null;

        try
        {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            System.out.println("\nConnected to sample database!\n");

            stmt = conn.prepareStatement(
                    "DELETE FROM dbi.orders where cid Not LIKE '%c%'");

            int row = stmt.executeUpdate();
            conn.commit();

            System.out.println(row);

        }
        catch (SQLException e)
        {
            System.err.println(e.toString());
            System.exit(1);
        }
        finally // close used resources
        {
            stmt.close();
            conn.close();
            System.out.println("\nDisconnected!\n");
        }
    }


    public void OptimizerTest(String sql) throws SQLException
    {
        Connection         conn = null;
        Statement statement = null;
        int size = 0;

        try
        {
            System.out.println("Testing query: '" + sql + "'...");

            var start = System.currentTimeMillis();

            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            System.out.println("\nConnected to sample database!\n");

            ResultSet rs = statement.executeQuery(sql);
            conn.commit();

            var finish = System.currentTimeMillis();

            var result = finish - start;

            if(rs != null)
            {
                rs.last();
                size = rs.getRow();
            }


            System.out.println("Found " + size + " rows in " + result + " ms");
        }
        catch (SQLException e)
        {
            System.err.println(e.toString());
            System.exit(1);
        }
        finally // close used resources
        {
            statement.close();
            conn.close();
            System.out.println("\nDisconnected!\n");
        }
    }







    private String[] ReadSecretsFile()
    {
        try {
            File secrets = new File("C:\\Users\\jhor\\IdeaProjects\\Datenbanken und Informationssysteme\\src\\secrets.txt");
            Scanner reader = new Scanner(secrets);
            var data = new String[3];


            for (int i = 0; i<3; i++)
            {
                data[i] = reader.nextLine();
            }

            return data;
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
            return null;
        }
    }

    private String getInput(String prompt)
    {
        try
        {
            System.out.println(prompt);
            return stdin.readLine();
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
            return null;
        }
    }
}
