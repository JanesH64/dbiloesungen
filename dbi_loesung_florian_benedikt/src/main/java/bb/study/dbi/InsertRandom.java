package bb.study.dbi;

import bb.study.dbi.Random.RandomAgent;
import bb.study.dbi.Random.RandomCustomer;
import bb.study.dbi.Random.RandomOrder;
import bb.study.dbi.Random.RandomProduct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsertRandom {
    public static void insertCustomers(int pCount) throws SQLException, ClassNotFoundException {
        System.out.println("Inserting "+pCount+" Costumers:");
        Connection connection = App.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO dbi.customers(cname, city, discnt) VALUES(?, ?, ?);");

        for(int i = 0; i < pCount; i++)
        {
            RandomCustomer randomCustomer = new RandomCustomer();

            statement.setString(1, randomCustomer.name);
            statement.setString(2, randomCustomer.city);
            statement.setFloat(3, randomCustomer.discnt);
            statement.executeUpdate();
            showProgress(i, pCount);
        }

        connection.commit();
        System.out.println("done.");
    }

    public static void insertAgents(int pCount) throws SQLException, ClassNotFoundException {
        System.out.println("Inserting "+pCount+" Agents:");
        Connection connection = App.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO dbi.agents(aname, city, percent) VALUES(?, ?, ?);");

        for(int i = 0; i < pCount; i++)
        {
            RandomAgent randomAgent = new RandomAgent();

            statement.setString(1, randomAgent.name);
            statement.setString(2, randomAgent.city);
            statement.setInt(3, randomAgent.percent);
            statement.executeUpdate();
            showProgress(i, pCount);
        }

        connection.commit();
        System.out.println("done.");
    }

    public static void insertProducts(int pCount) throws SQLException, ClassNotFoundException {
        System.out.println("Inserting "+pCount+" Products:");
        Connection connection = App.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO dbi.products(pname, city, quantity, price) VALUES(?, ?, ?, ?);");

        for(int i = 0; i < pCount; i++)
        {
            RandomProduct randomProduct = new RandomProduct();

            statement.setString(1, randomProduct.name);
            statement.setString(2, randomProduct.city);
            statement.setInt(3, randomProduct.quantity);
            statement.setFloat(4, randomProduct.price);
            statement.executeUpdate();
            showProgress(i, pCount);
        }

        connection.commit();
        System.out.println("done.");
    }

    public static void insertOrders(int pCount) throws SQLException, ClassNotFoundException {
        System.out.println("Inserting "+pCount+" Orders:");
        Connection connection = App.getConnection();

        List<Integer> cids = new ArrayList<>();
        ResultSet cidResults = connection.createStatement().executeQuery("SELECT cid FROM dbi.customers;");
        while(cidResults.next())
            cids.add(cidResults.getInt(1));

        List<Integer> aids = new ArrayList<>();
        ResultSet aidResults = connection.createStatement().executeQuery("SELECT aid FROM dbi.agents;");
        while(aidResults.next())
            aids.add(aidResults.getInt(1));

        List<Integer> pids = new ArrayList<>();
        ResultSet pidResults = connection.createStatement().executeQuery("SELECT pid FROM dbi.products;");
        while(pidResults.next())
            pids.add(pidResults.getInt(1));

        PreparedStatement statement = connection.prepareStatement("INSERT INTO dbi.orders(month, cid, aid, pid, qty, dollars) VALUES(?, ?, ?, ?, ?, ?);");

        for(int i = 0; i < pCount; i++)
        {
            RandomOrder randomOrder = new RandomOrder(cids, aids, pids);

            statement.setString(1, randomOrder.month);
            statement.setInt(2, randomOrder.cid);
            statement.setInt(3, randomOrder.aid);
            statement.setInt(4, randomOrder.pid);
            statement.setInt(5, randomOrder.qty);
            statement.setFloat(6, randomOrder.dollars);
            statement.executeUpdate();
            showProgress(i, pCount);
        }

        connection.commit();
        System.out.println("done.");
    }

    private static void showProgress(int i, int pCount) {
        if ((((i + 1) * 100) / pCount ) % 10 == 0) {
            System.out.print((((i + 1) * 100) / pCount )+"%\r");
        }
    }
}
