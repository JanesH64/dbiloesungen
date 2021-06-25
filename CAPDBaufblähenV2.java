package praktikumsaufgabe3;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/*
 * In dieser zweiten Version werden die Werte von zB dem Kunden beim Erstellen des Eintrags 
 * in ein Hilfsarray geschrieben, um das Erstellen der Tabelle orders zu optimieren, 
 * die Laufzeit wird verkürzt.
 */
public class CAPDBaufblähenV2 {
	
	//Hilfsarrays für zufällige Auswahl von Städten, (Produkt-) Namen, und Monaten	
	private static String[] stadtArray = new String[10];
	private static String[] namensArray = new String[25];
	private static String[] produktArray = new String[20];
	private static String[] monatsArray = new String[12];
	
	//Hilfsarrays zum Beschleunigen des Füllens von cap.orders mittels Zwischenspeichern
	private static Object[][] customers = new Object[10000][2];
	private static String[] agents = new String[100];
	private static Object[][] products = new Object[100][2];

	public static void main(String[] args) throws SQLException
	{
		//Setzen der ersten Uhrzeit für die Zeitmessung
		long uhr1 = System.currentTimeMillis();
		
		//Füllen der Hilfsarrays für namen, staedte und produkte
		namen();
		staedte();
		produkte();
		monate();
		
		//Aufbau einer Verbindung mithilfe eines Connection-Ojekts
		Connection conn = null;
		conn = getConnection("jdbc:mysql://localhost:3306/cap", "root", "P@ssw0rd");
		conn.setAutoCommit(false);
		
		System.out.println("Connection mit der Datenbank 'cap' wurde aufgebaut.");
		
		//Erstellen eines Statement-Objekts für das Erstellen der Tabellen
		Statement stmt = conn.createStatement();
		
		//Erstellen und Füllen der DB mit zufälligen Werten
		erstellen(stmt);
		agentsFüllen(conn);
		productsFüllen(conn);
		customersFüllen(conn);
		ordersFüllen(conn);
		
		//Zählen der Anzahl an Datensätzen in den Tabellen
		überprüfen(conn);
		
		//Schließen von Statement und Connection
		if (stmt!=null) stmt.close();
		if (conn!=null) conn.close();
        System.out.println("\nDisconnected!");
        
        //Setzen der zweiten Uhrzeit und Ausgeben der Differenz
        long uhr2 = System.currentTimeMillis();
		String time = getTime(uhr1, uhr2);
        System.out.println("\nBenötigte Zeit 3b: " + time);
	}

	private static void staedte() 
	{
		//Füllen des Hilfsarrays
		stadtArray[0] = "Berlin";
		stadtArray[1] = "Hamburg";
		stadtArray[2] = "Muenchen";
		stadtArray[3] = "Koeln";
		stadtArray[4] = "Frankfurt am Main";
		stadtArray[5] = "Stuttgart";
		stadtArray[6] = "Duesseldorf";
		stadtArray[7] = "Leipzig";
		stadtArray[8] = "Dortmund";
		stadtArray[9] = "Essen";
	}
	
	private static void namen() 
	{
		//Füllen des Hilfsarrays
		namensArray[0] = "Mueller";
		namensArray[1] = "Schmidt";
		namensArray[2] = "Schneider";
		namensArray[3] = "Fischer";
		namensArray[4] = "Meyer";
		namensArray[5] = "Weber";
		namensArray[6] = "Hofmann";
		namensArray[7] = "Wagner";
		namensArray[8] = "Becker";
		namensArray[9] = "Schulz";
		namensArray[10] = "Schaefer";
		namensArray[11] = "Koch";
		namensArray[12] = "Bauer";
		namensArray[13] = "Richter";
		namensArray[14] = "Klein";
		namensArray[15] = "Schroeder";
		namensArray[16] = "Wolf";
		namensArray[17] = "Neumann";
		namensArray[18] = "Schwarz";
		namensArray[19] = "Schmitz";
		namensArray[20] = "Krueger";
		namensArray[21] = "Braun";
		namensArray[22] = "Zimmermann";
		namensArray[23] = "Lange";
		namensArray[24] = "Krause";
	}
	
	private static void produkte() 

	{
		//Füllen des Hilfsarrays
		produktArray[0] = "Stift";
		produktArray[1] = "Heft";
		produktArray[2] = "Mappe";
		produktArray[3] = "Buch";
		produktArray[4] = "Buch";
		produktArray[5] = "Buch";
		produktArray[6] = "Buch";
		produktArray[7] = "Block";
		produktArray[8] = "Kugelschr";
		produktArray[9] = "Radierer";
		produktArray[10] = "Füller";
		produktArray[11] = "Lineal";
		produktArray[12] = "Zirkel";
		produktArray[13] = "Geodreieck";
		produktArray[14] = "Block";
		produktArray[15] = "Kalender";
		produktArray[16] = "Pinsel";
		produktArray[17] = "Ordner";
		produktArray[18] = "Ordner";
		produktArray[19] = "Mappe";
	}
	
	private static void monate()
	{
		//Füllen des Hilfsarrays
		monatsArray[0] = "jan";
		monatsArray[1] = "feb";
		monatsArray[2] = "mär";
		monatsArray[3] = "apr";
		monatsArray[4] = "mai";
		monatsArray[5] = "jun";
		monatsArray[6] = "jul";
		monatsArray[7] = "aug";
		monatsArray[8] = "sep";
		monatsArray[9] = "okt";
		monatsArray[10] = "nov";
		monatsArray[11] = "dez";
	}

	private static void erstellen(Statement pStmt) 
			throws SQLException 
	{
		//Löschen vorheriger Tabellen
		pStmt.executeUpdate(
				"DROP TABLE if EXISTS cap.orders;");
		pStmt.executeUpdate(
				"DROP TABLE if EXISTS cap.products;");
		pStmt.executeUpdate(
				"DROP TABLE if EXISTS cap.agents;");
		pStmt.executeUpdate(
				"DROP TABLE if EXISTS cap.customers;");
		
		//Erstellen neuer Tabellen ohne Inhalt
		pStmt.executeUpdate(
				"create table cap.customers( " +
				"	cid		char(5) not null, " +
				"	cname	varchar(13), " +
				"	city	varchar(20), " +
				"	discnt	dec(4,2), " +
				"	primary key (cid));"
				);
		pStmt.executeUpdate(
				"create table cap.agents( " +
				"	aid		char(3) not null, " +
				"	aname	varchar(13), " +
				"	city	varchar(20), " +
				"	percent	int, " +
				"	primary key (aid)); "
				);
		pStmt.executeUpdate(
				"create table cap.products( " +
				"	pid		char(3) not null, " +
				"	pname	varchar(13), " +
				"	city	varchar(20), " +
				"	quantity int, " +
				"	price	dec(10,2), " +
				"	primary key (pid)); "
				);
		pStmt.executeUpdate(
				"create table cap.orders( " +
				"	ordno	int not null, " +
				"	month	char(3), " +
				"	cid		char(5), " +
				"	aid		char(3), " +
				"	pid		char(3), " +
				"	qty		int, " +
				"	dollars	dec(10,2), " +
				"	primary key (ordno), " +
				"	foreign key (cid) references cap.customers(cid), " +
				"	foreign key (aid) references cap.agents(aid), " +
				"	foreign key (pid) references cap.products(pid) ); "
				);
		System.out.println("\nDie 4 Tabellen wurden in der Datenbank 'cap' leer erstellt.");
	}
	
	private static void agentsFüllen(Connection pConn) 
			throws SQLException
	{
		System.out.println("\nDie Tabelle cap.agents wird jetzt gefüllt.");
		
		//Erstellen eines PreparedStatement-Objekts
		PreparedStatement prepStmt = pConn.prepareStatement("insert into cap.agents values (?, ?, ?, ?);");
		
		//Schleife zum Erstellen von 100 Datensätzen
		for (int i = 0; i<= 99; i++)
		{
			//If-Verzweigung, damit die ID über alle Datensätze hinweg einheitlich ist
			if(i<=9)	
			{
				prepStmt.setString(1, "a0"+i);
				
				//Diese Arrayeinträge dienen dem späteren Erstellen von "orders"
				agents[i] = "a0"+i;
			}
			else 
			{
				prepStmt.setString(1, "a"+i);
				agents[i] = "a"+i;
			}
			
			//Zufälliges Setzen der restlichen Parameter
			prepStmt.setString(2, namensArray[(int) (Math.random()*(25-0))+0]);
			prepStmt.setString(3, stadtArray[(int) (Math.random()*(10-0))+0]);
			prepStmt.setInt(4, (int) (Math.random()*(9-4))+4);
			
			//Ausführen des Statements
			prepStmt.executeUpdate();
		}
		
		//Bestätigen und Schließen des Statements
		pConn.commit();
		if (prepStmt!=null) prepStmt.close();
		
		System.out.println("Fertig mit dem Füllen von cap.agents.");
	}
	
	private static void productsFüllen(Connection pConn) 
			throws SQLException
	{
		System.out.println("\nDie Tabelle cap.products wird jetzt gefüllt.");
		
		//Siehe Doku von Agents
		PreparedStatement prepStmt = pConn.prepareStatement("insert into cap.products values (?, ?, ?, ?, ?);");
		for (int i = 0; i<= 99; i++)
		{
			double price = (int)((Math.random()*(20-0))+2);
			products[i][1] = price/10;
			
			if(i<=9)
			{
				prepStmt.setString(1, "p0"+i);
				products[i][0] = "p0"+i;	
			}
			else 
			{
				prepStmt.setString(1, "p"+i);
				products[i][0] = "p"+i;
			}
						
			prepStmt.setString(2, produktArray[(int) (Math.random()*(20-0))+0]);
			prepStmt.setString(3, stadtArray[(int) (Math.random()*(10-0))+0]);
			prepStmt.setInt(4, (int) ((Math.random()*(2500-1000))+1000)*100);
			prepStmt.setDouble(5, price/10);
			prepStmt.executeUpdate();
		}
		
		pConn.commit();
		if (prepStmt!=null) prepStmt.close();
		
		System.out.println("Fertig mit dem Füllen von cap.products.");
	}
	
	private static void customersFüllen(Connection pConn) 
			throws SQLException
	{
		System.out.println("\nDie Tabelle cap.customers wird jetzt gefüllt.");
		
		//Siehe Doku von Agents
		PreparedStatement prepStmt = pConn.prepareStatement("insert into cap.customers values (?, ?, ?, ?);");
		for (int i=0; i<=9999; i++)
		{
			int discnt = (int) ((Math.random()*(7-0))*2);
			customers[i][1] = discnt;

			if(i<=9)
			{
				prepStmt.setString(1, "c000"+i);
				customers[i][0] = "c000"+i;
			}
			else if (i<=99)	
			{
				prepStmt.setString(1, "c00"+i);
				customers[i][0] = "c00"+i;
			}
			else if (i<=999)
			{
				prepStmt.setString(1, "c0"+i);
				customers[i][0] = "c0"+i;
			}
			else
			{
				prepStmt.setString(1, "c"+i);
				customers[i][0] = "c"+i;
			}
			
			prepStmt.setString(2, namensArray[(int) (Math.random()*(25-0))+0]);
			prepStmt.setString(3, stadtArray[(int) (Math.random()*(10-0))+0]);
			prepStmt.setInt(4, discnt);
			prepStmt.executeUpdate();
		}
		pConn.commit();
		if (prepStmt!=null) prepStmt.close();
		
		System.out.println("Fertig mit dem Füllen von cap.customers.");
	}
	
	private static void ordersFüllen(Connection pConn) 
			throws SQLException
	{
		System.out.println("\nDie Tabelle cap.orders wird jetzt gefüllt.");

		PreparedStatement prepStmt = pConn.prepareStatement("insert into cap.orders values (?, ?, ?, ?, ?, ?, ?);");
		
		//Hilfsvariablen zum Speichern von Zufallszahlen
		int qty = 0;
		int c = 0;
		int p = 0;
		int a = 0;
		
		//Schleife zum Generieren von 1.000.000 Datensätzen
		for (int i=0; i<=999999; i++)
		{
			//Generieren der Zufallszahlen
			c = (int) (Math.random()*(10000-0)+0);
			p = (int) (Math.random()*(100-0)+0);
			a = (int) (Math.random()*(100-0)+0);
			qty = ((int) (Math.random()*(16-3)+3) *100);
			
			//Setzen der Parameter, s. Doku von Agents
			prepStmt.setInt(1, i);
			prepStmt.setString(2, monatsArray[(int) (Math.random()*(12-0))]);
			prepStmt.setString(3, (String) customers[c][0]);
			prepStmt.setString(4, agents[a]);
			prepStmt.setString(5, (String) products[p][0]);
			prepStmt.setInt(6, qty);
			
			//Berechnen des Preises der Order aus mehreren Variablen, dafür braucht man die Hilfsarrays und die Hilfsvariablen
			double dollars = qty*(double) products[p][1]*(100-(int) customers[c][1])/100;
			prepStmt.setDouble(7, dollars);
			prepStmt.executeUpdate();
		}
		
		// siehe Doku von Agents
		pConn.commit();
		if (prepStmt!=null) prepStmt.close();
		
		System.out.println("Fertig mit dem Füllen von cap.orders.");
	}
	
	private static void überprüfen(Connection pConn) 
			throws SQLException
	{
		//Erstellen eines ResultSet-Objekts
		ResultSet rs = null;
		
		//Erstellen eines PreparedStatement-Objekts, Cursor füllen und ausführen der Query
		PreparedStatement prepStmt = pConn.prepareStatement("Select count(*) from cap.agents");
		rs = prepStmt.executeQuery();
		rs.next();
		
		//Ausgeben der Anzahl der Datensätze
		System.out.println("/nDie Tabelle cap.agents hat	"+rs.getInt(1)+" von 100 benötigten Einträgen.");
		
		//s.o.
		prepStmt = pConn.prepareStatement("Select count(*) from cap.products");
		rs = prepStmt.executeQuery();
		rs.next();
		System.out.println("Die Tabelle cap.products hat	"+rs.getInt(1)+" von 100 benötigten Einträgen.");
		
		//s.o.
		prepStmt = pConn.prepareStatement("Select count(*) from cap.customers");
		rs = prepStmt.executeQuery();
		rs.next();
		System.out.println("Die Tabelle cap.customers hat	"+rs.getInt(1)+" von 10000 benötigten Einträgen.");
		
		//s.o.
		prepStmt = pConn.prepareStatement("Select count(*) from cap.orders");
		rs = prepStmt.executeQuery();
		rs.next();
		System.out.println("Die Tabelle cap.orders hat	"+rs.getInt(1)+" von 1000000 benötigten Einträgen.");
		
		//Schließen der nicht mehr benötigten Objekte
		pConn.commit();
		if (rs!=null) rs.close();
		if (prepStmt!=null) prepStmt.close();
	}
	
	protected static Connection getConnection(String dbUrl, String userId, String userPwd)
			throws SQLException
	{
		//Zurückgeben eines Connection-Objekts, dass durch den Treiber erstellt wurde
		return DriverManager.getConnection(dbUrl, userId, userPwd);
	}
	
	private static String getTime(long start, long end) 
	{
		//Umrechnen der Differenz der beiden Zeiten in LocalDateTime
		LocalDateTime time = Instant.ofEpochMilli(end-start).atZone(ZoneId.systemDefault()).toLocalDateTime();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss.SSS");
		String Zeit = time.format(dtf);
				
		return Zeit;
	}
}
