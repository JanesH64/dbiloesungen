//Created by Alexander Eisner 
//last update: 25.06.2021

import java.sql.Connection;
import java.util.*; 
//import java.io.BufferedReader;
import java.io.FileNotFoundException;
//import java.io.Reader;
//import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
//import org.apache.ibatis.jdbc.ScriptRunner;


public class TEST {

	
	private String url = "jdbc:postgresql://localhost/XXXXXX";
	private String user = "XXXXXXX";
	private String password = "XXXXXXX";
	
	
	public Connection connect() throws FileNotFoundException {
		
		Connection conn = null; 
		try {
			conn = DriverManager.getConnection(url, user, password);
			
			System.out.println("Connected to the PostgreSQL server successfully.");
			//ScriptRunner sr = new ScriptRunner(conn);
			//Reader reader = new BufferedReader(new FileReader("'C:\\DOCKER\\cap.sql"));
			//sr.runScript(reader);
			
			Statement smt = conn.createStatement(); 
			
			//old 
			int a = 1500;
			int qty = 400;
			
			for( int i =0 ; i<1000;i++) {
				a=a+1;
				qty=qty+1;
				smt.executeUpdate(
					
					"insert into orders "+
							"values (" + a + ", 'mar', 'c006', 'a06', 'p01'," + qty + ", 400.00); "
					);
					
			}
			
			} catch (SQLException e) {
         			   System.out.println(e.getMessage());
        			}
			return conn;
		}
//-----------------------------------------------------------------
	public Connection insertA() throws FileNotFoundException {
		
		Connection conn = null; 
		try {
			conn = DriverManager.getConnection(url, user, password);
			
			System.out.println("Connected to the PostgreSQL server successfully.");
		
			PreparedStatement smt = conn.prepareStatement("insert into agents " + "values(  ? , ?, ?, ?  )");
			int aid = 0; 
			String aname = "walter";
			String city = "berlin"; 
			int percent = 0;

			
			for( int i =0 ; i<100;i++) {
				
				aid = aid +1; 
				
				if(i==25) {
					
					aname="peter"; 
					city="london"; 
				}
				
					if(i==50) {
					
					aname="dieter"; 
					city="koeln"; 
				}
					
					if(i==75) {
						
						aname="hans"; 
						city="wesel"; 
					}
					
					
				percent = this.getRandomNumberInRange(0, 25); 
				
				
				
				smt.setString(1,String.valueOf(aid)); 
				smt.setString(2,aname); 
				smt.setString(3,city); 
				smt.setInt(4,percent); 
				smt.executeUpdate();
			}
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
			return conn;
		}
	
//-------------------------------------------------------------

public Connection insertP() throws FileNotFoundException {
	
	Connection conn = null; 
	try {
		conn = DriverManager.getConnection(url, user, password);
		
		System.out.println("Connected to the PostgreSQL server successfully.");
	
		PreparedStatement smt = conn.prepareStatement("insert into products " + "values(  ? , ?, ?, ?,?  )");
		int pid = 0; 
		String pname = "walter";
		String city = "berlin"; 
		int price = 0;
		int qty = 0; 

		
		for( int i =0 ; i<100;i++) {
			
			pid = pid +1; 
			
			if(i==20) {
				
				pname="stift"; 
				city="london"; 
				price = this.getRandomNumberInRange(0, 25); 
			}
			
			if(i==40) {
				
				pname="Papier"; 
				city="wesel"; 
				price = this.getRandomNumberInRange(0, 10); 
			}
			
			if(i==60) {
				
				pname="lineal"; 
				city="koeln"; 
				price = this.getRandomNumberInRange(0, 35); 
			}
			
			if(i==80) {
				
				pname="Tablet"; 
				city="london"; 
				price = this.getRandomNumberInRange(255, 899); 
			}	
				
			
			qty = this.getRandomNumberInRange(0, 999);
			
			
			smt.setString(1,String.valueOf(pid)); 
			smt.setString(2,pname); 
			smt.setString(3,city); 
			smt.setInt(4,qty); 
			smt.setInt(5,price); 
			smt.executeUpdate();
		}
	} catch (SQLException e) {
        System.out.println(e.getMessage());
    }
		return conn;
	}
//----------------------------------------------------------------------------
	
public Connection insertC() throws FileNotFoundException {
	
	Connection conn = null; 
	try {
		conn = DriverManager.getConnection(url, user, password);
		
		System.out.println("Connected to the PostgreSQL server successfully.");
	
		PreparedStatement smt = conn.prepareStatement("insert into customers " + "values(  ? , ?, ?, ? )");
		int cid = 0; 
		String cname = "walter";
		String city = "berlin"; 
		
		int disc = 0; 

		
		for( int i =0 ; i<10000;i++) {
			
			cid = cid +1; 
			
			if(i==1000) {
				
				cname="Hansi"; 
				city="Frankfurt"; 
				
			}
			
			if(i==2000) {
				
				cname="Walter"; 
				city="wesel"; 
		
			}
			
			if(i==3000) {
				
				cname="Tine"; 
				city="koeln"; 
				
			}
			
			if(i==4000) {
				
				cname="Timo"; 
				city="london"; 
				
			}	
			
			
			if(i==5000) {
				
				cname="Phil"; 
				city="dortmund"; 
				
			}
			
			if(i==6000) {
				
				cname="Anna"; 
				city="München"; 
				
			}
			
			
			
			if(i==7000) {
				
				cname="Ludger"; 
				city="Penig"; 
				
			}
			
			
			if(i==8000) {
				
				cname="Sam"; 
				city="New York"; 
				
			}
			
			
				if(i==9000) {
				
				cname="Lena"; 
				city="Tokyo"; 
				
			}
				
			
			disc = this.getRandomNumberInRange(0, 10);
			
			
			smt.setString(1,String.valueOf(cid)); 
			smt.setString(2,cname); 
			smt.setString(3,city); 
			smt.setInt(4,disc); 
		
			smt.executeUpdate();
		}
	} catch (SQLException e) {
        System.out.println(e.getMessage());
    }
		return conn;
	}

//-----------------------------------------------

public Connection insertO() throws FileNotFoundException {
	
	Connection conn = null; 
	try {
		conn = DriverManager.getConnection(url, user, password);
		
		System.out.println("Connected to the PostgreSQL server successfully.");
	
		PreparedStatement smt = conn.prepareStatement("insert into orders " + "values(  ? , ?, ?, ?,?,?,? )");
		int ordno= XXXXXXXXXXX; 
		String month="";
		int cid = 0; 
		int aid= 0; 
		int pid = 0; 
		int qty = 0; 
		int dollars ; 
		
		

		
		for( int i =0 ; i<XXXXXXXX;i++) {
			
			ordno = ordno+1;
			int m = 0; 
			m = this.getRandomNumberInRange(1, 12);
			
			if(m==1) {
				
				month= "JAN";  	
			}
			
			if(m==2) {
				month= "FEB";  
				
			}

			if(m==3) {
				month= "MAR";  
	
			}


			if(m==4) {
				month= "APR";  
	
			}


			if(m==5) {
				month= "MAY";  
		
			}


			if(m==6) {
				month= "JUN";  
	
			}


			if(m==7) {
				month= "JUL";  
	
			}
			
			if(m==8) {
				month= "AUG";  
	
			}

			if(m==9) {	
				month= "SEP";  
				
			}
			
			
			if(m==10) {
				month= "OKT";  
				
			}
			

			if(m==11) {
				month= "NOV";  
				
			}
			
			
			if(m==12) {
				month= "DEZ";  
				
			}
			
			cid =  this.getRandomNumberInRange(1, 9999);
					
			aid =  this.getRandomNumberInRange(1, 100);
			
			pid =  this.getRandomNumberInRange(1, 100);
				
			qty =  this.getRandomNumberInRange(1, 1000);
			
			dollars =  this.getRandomNumberInRange(1,100000 ); 
			
			smt.setInt(1,ordno); 
			smt.setString(2,month);
			smt.setInt(3,(cid)); 
			smt.setInt(4,(aid)); 
			smt.setInt(5,(pid)); 
			smt.setInt(6,qty); 
			smt.setInt(7,dollars); 
		
			smt.executeUpdate();
		}
	} catch (SQLException e) {
        System.out.println(e.getMessage());
    }
		return conn;
	}

//------------------------------------------------------------------
public Connection GET() throws FileNotFoundException {
	
	Connection conn = null; 
	try {
		conn = DriverManager.getConnection(url, user, password);
		
		System.out.println("Connected to the PostgreSQL server successfully.");
	
		Statement smt = conn.createStatement(); 
		//ResultSet rs = smt.executeQuery("SELECT * FROM products;"); 
		//ResultSet rs = smt.executeQuery("SELECT ordno FROM orders"); 
		
		long start = System.nanoTime(); 
		//ResultSet rs = smt.executeQuery("SELECT ordno FROM orders"); 
		//ResultSet rs = smt.executeQuery("SELECT b.ordno FROM orders a inner join orders b on a.ordno=b.ordno;"); 
		//ResultSet rs = smt.executeQuery("SELECT b.ordno FROM orders a inner join orders b on a.ordno=b.ordno inner join customers c on b.cid = c.cid;"); 
		ResultSet rs = smt.executeQuery("SELECT c.ordno from A a inner join b B on a.ordno=B.ordno inner join C c on B.ordno = c.ordno; ");
		
		long ende = System.nanoTime(); 
		//long start = System.nanoTime(); 
		while(rs.next()) {
			
			String ausgabe = rs.getString("ordno");
			//System.out.println(ausgabe + " , "); 
		}
		
		//long ende = System.nanoTime(); 
		
		
		
		System.out.println("time in ns: " + (ende -start)); 
		
		
	} catch (SQLException e) {
        System.out.println(e.getMessage());
    }
		return conn;
	}

//-----------------------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		TEST abc = new TEST();
		//abc.insertA();
		//abc.insertP();
		//abc.insertC();
		//abc.insertO();
		abc.GET();
		}
		
//---------------------------------------------------------------------------------------------------------------------------------------
	    public static int getRandomNumberInRange(int min, int max) {

	        if (min >= max) {
	            throw new IllegalArgumentException("max must be greater than min");
	        }

	        Random r = new Random();
	        return r.nextInt((max - min) + 1) + min;
	    }
	
}
