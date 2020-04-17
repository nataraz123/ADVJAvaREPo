package com.nt.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class CSVInsertTest {
  private static final String INSERT_STUDENT_QUERY="INSERT INTO FILE1.CSV VALUES(?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc=null;
		int sno=0;
		String sname=null,sadd=null;
		float avg=0.0f;
		Connection con=null;
		PreparedStatement ps=null;
		int count=0;
				
		try {
		 //read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Student number:");
				sno=sc.nextInt();
				System.out.println("Enter Student name::");
				sname=sc.next();
				System.out.println("Enter Student Addrs");
				sadd=sc.next();
				System.out.println("Enter Student avg::");
				avg=sc.nextFloat();
			}
			
					
			
			//register JDBC driver
			Class.forName("com.hxtt.sql.text.TextDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:Text:///E:\\WorkSpaces\\advjava\\NTAJ1113");
			
			//create Jdbc PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(INSERT_STUDENT_QUERY);
			
			//set values to query params
			if(ps!=null ) {
				ps.setInt(1,sno);
				ps.setString(2,sname);
				ps.setString(3,sadd);
				ps.setFloat(4,avg);
			}
			//execute the query
			if(ps!=null)
				count=ps.executeUpdate();
			
			//process the result
			if(count==0)
				System.out.println("Record not inserted");
			else
				System.out.println("Record inserted");
				
			}//try
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objs
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
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}//finally

	}//main
}//class
