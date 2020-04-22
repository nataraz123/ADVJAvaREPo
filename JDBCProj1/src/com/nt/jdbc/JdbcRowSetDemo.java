package com.nt.jdbc;

import java.sql.SQLException;

import oracle.jdbc.rowset.OracleJDBCRowSet;

public class JdbcRowSetDemo {

	public static void main(String[] args) {
		OracleJDBCRowSet rowset=null;
	  try {
		  rowset=new OracleJDBCRowSet();
		  rowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		  rowset.setUsername("system");
		  rowset.setPassword("manager");
		  rowset.setCommand("SELECT EMPNO,ENAME,JOB,SAL FROM EMP");
		  rowset.execute();
		  //process the rowset
		  while(rowset.next()) {
			  System.out.println(rowset.getInt(1)+"  "+rowset.getString(2)+"  "+rowset.getString(3)+" "+rowset.getFloat(4));
		  }
		  
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
			  if(rowset!=null)
				  rowset.close();
		  }
		  catch(SQLException se) {
			  se.printStackTrace();
		  }
	  }//finally
	}//main
}//class
