package bb.study.dbi;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProfileQueries {
    public static void test(List<ProfileStatement> statements) throws SQLException, ClassNotFoundException {
        System.out.println("Profiling Queries in statements.json");
        Connection connection = App.getConnection();
        Statement statement = connection.createStatement();

        for (ProfileStatement profileStatement: statements)
        {
            System.out.println("'"+profileStatement.slow+"' vs '"+profileStatement.fast+"'...");
            long start = System.currentTimeMillis();

            System.out.println("Executing slow statement...");
            statement.executeQuery(profileStatement.slow);

            long end = System.currentTimeMillis();
            profileStatement.slowMillis = end-start;
            profileStatement.slowTime = niceTime(profileStatement.slowMillis);

            start = System.currentTimeMillis();

            System.out.println("Executing fast statement...");
            statement.executeQuery(profileStatement.fast);

            end = System.currentTimeMillis();
            profileStatement.fastMillis = end-start;
            profileStatement.fastTime = niceTime(profileStatement.fastMillis);

            profileStatement.deltaMillis = profileStatement.slowMillis - profileStatement.fastMillis;
            profileStatement.deltaTime = niceTime(profileStatement.deltaMillis);

            profileStatement.fastMillis = null;
            profileStatement.slowMillis = null;
            profileStatement.deltaMillis = null;

            System.out.println("");
        }
        System.out.println("done.");
    }

    private static String niceTime(long millis) {
        String prepend = "";
        if (millis < 0)
        {
            millis *=-1;
            prepend = "-";
        }
        Date date = new Date(millis);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return prepend+formatter.format(date);
    }

    public static List<ProfileStatement> convert(List<Map<String, String>> maps) {
        List<ProfileStatement> statements = new ArrayList<>();
        for (Map<String, String> entry : maps) {
            ProfileStatement profileStatement = new ProfileStatement();
            profileStatement.slow = entry.get("slow");
            profileStatement.fast = entry.get("fast");
            statements.add(profileStatement);
        }
        return statements;
    }
}
