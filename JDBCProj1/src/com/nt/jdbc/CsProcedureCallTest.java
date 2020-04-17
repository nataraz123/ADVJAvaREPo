package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*create or replace procedure p_first(x in number,y in number, z out number)as
begin
   z:=x+y;
end;
/*/


public class CsProcedureCallTest {
   private static final String CALL_PROCEDURE="{ CALL P_FIRST(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc=null;
		int val1=0,val2=0;
		Connection con=null;
		CallableStatement cs=null;
	  try {
		  //read inputs
		  sc=new Scanner(System.in);
		  if(sc!=null) {
			  System.out.println("Enter first value::");
			  val1=sc.nextInt();
			  System.out.println("enter second value::");
			  val2=sc.nextInt();
		  }
		  //register JDBC driver s/w
		  Class.forName("oracle.jdbc.driver.OracleDriver");
		  //establish the connection
		  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
		  //create CallableStatement object 
		  if(con!=null)
			  cs=con.prepareCall(CALL_PROCEDURE);
		  //register OUT params with JDBC types
		  if(cs!=null)
			  cs.registerOutParameter(3, Types.INTEGER);
		  //set values to IN params
		  if(cs!=null) {
			  cs.setInt(1,val1);
			  cs.setInt(2,val2);
		  }
		  //execute PL/SQL Procudre
		  if(cs!=null)
			  cs.execute();
		//gather resukts from OUT params
		  if(cs!=null)
			  System.out.println("Sum of "+val1+" and"+val2 +" is"+cs.getInt(3));
		  
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
		  //close jdbc ohjs
		  try {
			  if(cs!=null)
				  cs.close();
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
