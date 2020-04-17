package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ResusltSetMetaDataTest {
  private static final String GET_ALL_STUDENTS_QUERY="SELECT SNO,SNAME,SADD,AVG FROM STUDENT";
    public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		int colCnt=0;
		try {
			
			//Load jdbc driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create Statement object
			if(con!=null)
			   st=con.createStatement();
			//send and execute SQL Query to DB s/w
			if(st!=null)
			  rs=st.executeQuery(GET_ALL_STUDENTS_QUERY);
			//create ResusltSetMetaData object
			if(rs!=null)
				rsmd=rs.getMetaData();
			//get columns count
			if(rsmd!=null){
			  colCnt=rsmd.getColumnCount();
			
			//print col names 
			  if(rsmd!=null) {
				  for(int i=1;i<=colCnt;++i)
					  System.out.print(rsmd.getColumnLabel(i)+" ("+rsmd.getColumnTypeName(i)+")       ");
			  }
			  }
			  
			System.out.println();
			
			if(rs!=null) {
				while(rs.next()) {
					for(int i=1;i<=colCnt;++i) {
						System.out.print(rs.getString(i)+"                                             ");
					}
					System.out.println();
				}//while
			}
			
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
			catch(ClassNotFoundException cnf) {
				cnf.printStackTrace();
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
				catch(SQLException se) {
					se.printStackTrace();
				}
				
				try {
					if(st!=null)
						st.close();
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
				
			}//finally

	}//main
}//class
