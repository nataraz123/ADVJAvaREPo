package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMetaDataTest {

	public static void main(String[] args) {
		 Connection con=null;
		 DatabaseMetaData dbmd=null;
		try {
			/* //load jdbc driver class
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//establish the connection
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");*/
			
			 //load jdbc driver class
			Class.forName("com.mysql.cj.jdbc.Driver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:mysql:///NTAJ1113DB1", "root","root");
			
			//create Database MetaData object
			dbmd=con.getMetaData();
			System.out.println("dbmd class name:::"+dbmd.getClass());
			
			System.out.println("DB s/w name::"+dbmd.getDatabaseProductName());
			System.out.println("DB s/w version::"+dbmd.getDatabaseProductVersion());
			System.out.println("Oracle major version::"+dbmd.getDatabaseMajorVersion());
			System.out.println("Oracle Minor Version::"+dbmd.getDatabaseMinorVersion());
			System.out.println("JDBC major version::"+dbmd.getJDBCMajorVersion());
			System.out.println("JDBC Minor Versiob::"+dbmd.getJDBCMinorVersion());
			System.out.println("JDBC drivver version:;"+dbmd.getDriverMajorVersion()+"."+dbmd.getDriverMinorVersion());
			System.out.println("All SQL keywords::"+dbmd.getSQLKeywords());
			System.out.println("All numberic functions::"+dbmd.getNumericFunctions());
			System.out.println("All System functions::"+dbmd.getSystemFunctions());
			
			System.out.println("Max Db tables in select query ::"+dbmd.getMaxTablesInSelect());
			System.out.println("Max chars in Db table name ::"+dbmd.getMaxTableNameLength());
			System.out.println("Max  row size::"+dbmd.getMaxRowSize());
			System.out.println("Supports Procedures?"+dbmd.supportsStoredProcedures());
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objs
			try {
				if(con!=null)
					con.close();
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally

	}//main
}//class
