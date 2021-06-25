package bb.study.dbi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App 
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException, IOException {
        // generateRandom();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Reader reader = Files.newBufferedReader(Paths.get("statements.json"));
        List<Map<String, String>> json = gson.fromJson(reader, List.class);
        List<ProfileStatement> statements = ProfileQueries.convert(json);
        ProfileQueries.test(statements);

        System.out.println(gson.toJson(statements));
        // Writer writer = new FileWriter("executedStatements.json");
        // gson.toJson(statements, writer);
        // writer.close();
    }

    public static void generateRandom() throws SQLException, ClassNotFoundException {
        InsertRandom.insertCustomers(10000);
        InsertRandom.insertAgents(100);
        InsertRandom.insertProducts(100);
        InsertRandom.insertOrders(1000000);
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql:dbi", "u", "p");
        connection.setAutoCommit(false);
        return connection;
    }
}
