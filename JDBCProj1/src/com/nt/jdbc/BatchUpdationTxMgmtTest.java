package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchUpdationTxMgmtTest {
  
    public static void main(String[] args) {
		Connection con=null;
		Statement st=null;
	    int result[]=null;
	    int sum=0;
	    boolean flag=false;
		try {
			
			//Load jdbc driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			// Begin Tx
			if(con!=null)
			  con.setAutoCommit(false);
			
			//create Statement object
			if(con!=null)
			  st=con.createStatement();
			
			
		    //add SQL queries to the batch
			if(st!=null) {
				st.addBatch("INSERT INTO STUDENT VALUES(1004,'raja','chennai',90.44)");
				st.addBatch("UPDATE STUDENT SET AVG=AVG+10 WHERE SADD='vizag'");
				st.addBatch("DELETE FROM STUDENT WHERE SADD='delhi'");
			}
			
			//execute the batch
			if(st!=null)
				result=st.executeBatch();
			
			//perform Tx Mgmt
			 for(int i=0;i<result.length;++i) {
				 if(result[i]==0) {
					 flag=true;
					 break;
				 }
			 }
			
			}//try
			catch(SQLException se) {
				flag=true;
				se.printStackTrace();
			}
			catch(ClassNotFoundException cnf) {
				flag=true;
				cnf.printStackTrace();
			}
			catch(Exception e) {
				flag=true;
				e.printStackTrace();
			}
			finally {
				
				//perform  Tx Mgmt
				if(con!=null) {
					try {
					if(flag==true) {
						con.rollback();
						System.out.println("Tx rolled back");
					}
					else {
						con.commit();
						System.out.println("Tx committed");
					}
					}//try
					catch(SQLException se) {
						se.printStackTrace();
					}
				}
				
				
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
