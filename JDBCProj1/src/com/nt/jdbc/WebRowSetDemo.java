package com.nt.jdbc;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import oracle.jdbc.rowset.OracleWebRowSet;

public class WebRowSetDemo {

	public static void main(String[] args) {
		OracleWebRowSet rowset=null;
		OutputStream os=null;
	  try {
		  rowset=new OracleWebRowSet();
		  rowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		  rowset.setUsername("system");
		  rowset.setPassword("manager");
		  rowset.setCommand("SELECT EMPNO,ENAME,JOB,SAL FROM EMP");
		  rowset.execute();
		  //process the rowset
		  while(rowset.next()) {
			  System.out.println(rowset.getInt(1)+"  "+rowset.getString(2)+"  "+rowset.getString(3)+" "+rowset.getFloat(4));
		  }
	   System.out.println("..................................");
	   rowset.writeXml(System.out);
	   System.out.println(".............................");
	   os=new FileOutputStream("emp.xml");
	   rowset.writeXml(os);
	    System.out.println("open emp.xml");
		  
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
