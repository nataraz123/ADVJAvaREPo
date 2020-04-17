package com.nt.jdbc;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CLOBInsert {
  private static final String  CLOB_INSERT_QUERY="INSERT INTO EMPALL VALUES(?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		int eno=0;
		String ename=null;
		String eadd=null,resumePath=null;
		File file=null;
	    long length=0;
	    Reader reader=null;
	    Connection con=null;
	    PreparedStatement ps=null;
	    int count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter employee ID::");
				eno=sc.nextInt();
				System.out.println("Enter Employee name::");
				ename=sc.next();
				System.out.println("Enter  Employee addrs");
				eadd=sc.next();
				System.out.println("Enter resume Path::");
				resumePath=sc.next();
			}
			
			// create java.io.File obj having name and location of resume doc
			file=new File(resumePath);
			length=file.length();
			//create Reader STream pointing to file
			reader=new FileReader(file);
			// register JDBC Driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			//create PreparedStatement obj having pre-comipled SQL Query
			if(con!=null)
				ps=con.prepareStatement(CLOB_INSERT_QUERY);
			//set values to query params
			if(ps!=null) {
				ps.setInt(1,eno);
				ps.setString(2,ename);
				ps.setString(3,eadd);
				ps.setCharacterStream(4, reader,length);
							}
			//execute the SQL query
			if(ps!=null) {
				count=ps.executeUpdate();
			}
			
			//process the result
			if(count==0)
				System.out.println("Reccord not inserted");
			else
				System.out.println("Record inserted");
			
		}//try
		catch(SQLException se) {
			se.printStackTrace();
			System.out.println("Problem in record insertion");
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objs and streams
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			
			try {
				if(reader!=null)
					reader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}//finally

	}//main

}//class
