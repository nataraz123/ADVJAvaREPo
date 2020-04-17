package com.nt.jdbc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MySQLBLOBRetrieve {
 private static final String  BLOB_RETRIEVE_QUERY="SELECT ARTISTID,ARTISTNAME,ARTISTADDRS,PHOTO FROM CINEMA_ARTIST WHERE ARTISTID=? ";
	public static void main(String[] args) {
		Scanner sc=null;
		int artistId=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String artistName=null,artistAddrs=null;
		InputStream is=null;
		OutputStream os=null;
		byte[] buffer=new byte[4096];
		int count=0;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter artist ID::");
				artistId=sc.nextInt();
			}
			//register JDBC driver class
			Class.forName("com.mysql.cj.jdbc.Driver");
			//establish the connecction
			con=DriverManager.getConnection("jdbc:mysql:///ntaj1113db1", "root","root");
			//create PreparedStatement object
			if(con!=null)
				ps=con.prepareStatement(BLOB_RETRIEVE_QUERY);
			//set query param values
			if(ps!=null)
				ps.setInt(1, artistId);
			//execute the query
			if(ps!=null)
				rs=ps.executeQuery();
			
			//process the ResultSet object
			if(rs.next()) {
				// get Record content
				artistId=rs.getInt(1);
				artistName=rs.getString(2);
				artistAddrs=rs.getString(3);
				is=rs.getBinaryStream(4);
				//create OuputStream pointing to empty destination file
				os=new FileOutputStream("new_pict.jpg");
				//write buffer based logic to copy is based BLOB value to os based empty Destination file
				while((count=is.read(buffer))!=-1) {
					os.write(buffer,0,count);
				}//while
				System.out.println("BLOB value retrieved");
			}//if
			else {
				System.out.println("Record not found");
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
				if(is!=null)
					is.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			
			try {
				if(os!=null)
					os.close();
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
