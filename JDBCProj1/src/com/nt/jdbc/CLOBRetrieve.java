package com.nt.jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CLOBRetrieve {
 private static final String CLOB_RERIEVE_QUERY="SELECT ENO,ENAME,EADDRS,RESUME FROM EMPALL WHERE ENO=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int eno=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String name=null,addrs=null;
		Reader reader=null;
		Writer writer =null;
		char[] buffer=new char[2048];
		int count=0;
				try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter employee number::");
				eno=sc.nextInt();
			}
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			//create PreparedStatement obj having pre-comipled SQL Query
			if(con!=null)
				ps=con.prepareStatement(CLOB_RERIEVE_QUERY);
			//set values to query params
			if(ps!=null)
				ps.setInt(1, eno);
			//execute the SQL query
			if(ps!=null) {
				rs=ps.executeQuery();
			}
			//process the ResultSEt object
			if(rs!=null) {
				if(rs.next()) {
					eno=rs.getInt(1);
					name=rs.getString(2);
					addrs=rs.getString(3);
					reader=rs.getCharacterStream(4);
					//create Writer stream pointing dest text file
					writer =new FileWriter("new_resume.txt");
					//use buffer based streams logics to copy the content
					while((count=reader.read(buffer))!=-1) {
						writer.write(buffer,0,count);
					}
					System.out.println("CLOB Retrieved");
					System.out.println(eno+"  "+name+"   "+addrs);
				}
				else {
					System.out.println("Record not found");
				}
				
			}
			
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
						if(rs!=null)
							rs.close();
					}
					catch(SQLException se)
					{
						se.printStackTrace();
					}
					
					try {
						if(ps!=null)
							ps.close();
					}
					catch(SQLException se)
					{
						se.printStackTrace();
					}
					
					try {
						if(con!=null)
						con.close();
					}
					catch(SQLException se)
					{
						se.printStackTrace();
					}
					
					try {
						if(reader!=null)
							reader.close();
					}
					catch(IOException ioe)
					{
						ioe.printStackTrace();
					}
					
					try {
						if(writer!=null)
						writer.close();
					}
					catch(IOException se)
					{
						se.printStackTrace();
					}
					
					try {
						if(sc!=null)
							sc.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}//finally
				
	}//main
}//class
