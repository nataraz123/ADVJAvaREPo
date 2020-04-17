package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchUpdationTest {
  
    public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
	    int result[]=null;
	    int sum=0;
		try {
			
			//Load jdbc driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create Statement object
			if(con!=null)
			  st=con.createStatement();
			
		    //add SQL queries to the batch
			if(st!=null) {
				st.addBatch("INSERT INTO STUDENT VALUES(1002,'raja','delhi',90.44)");
				st.addBatch("UPDATE STUDENT SET AVG=AVG+5 WHERE SADD='vizag'");
				st.addBatch("DELETE FROM STUDENT WHERE SADD='mumbai'");
			}
			//execute the batch
			if(st!=null)
				result=st.executeBatch();
			
			//process the result
			for(int i=0;i<result.length;++i)
				sum=sum+result[i];
			
			System.out.println("No.of records that are effected::"+sum);
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
