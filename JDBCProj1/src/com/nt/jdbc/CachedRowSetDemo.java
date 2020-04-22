package com.nt.jdbc;

import java.sql.SQLException;

import oracle.jdbc.rowset.OracleCachedRowSet;

public class CachedRowSetDemo {

	public static void main(String[] args) {
		OracleCachedRowSet rowset=null;
	  try {
		  rowset=new OracleCachedRowSet();
		  rowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		  rowset.setUsername("system");
		  rowset.setPassword("manager");
		  rowset.setCommand("SELECT EMPNO,ENAME,JOB,SAL FROM EMP");
		  rowset.execute();
		  //process the rowset
		  while(rowset.next()) {
			  System.out.println(rowset.getInt(1)+"  "+rowset.getString(2)+"  "+rowset.getString(3)+" "+rowset.getFloat(4));
		  }
		  
		  Thread.sleep(40000);  //during this stop the Db s/w
		  
		  rowset.setReadOnly(false);
		  rowset.absolute(3);
		  rowset.updateFloat(4,10001);
		  rowset.updateRow();
		  System.out.println("Rowset updated in offline");
		  Thread.sleep(60000);  //during this restart the Db s/w
          rowset.acceptChanges();		  
          
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
