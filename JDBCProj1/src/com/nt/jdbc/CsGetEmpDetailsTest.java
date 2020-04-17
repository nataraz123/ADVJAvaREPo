package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*CREATE OR REPLACE PROCEDURE P_GETEMPDETAILSBYNO 
(
  NO IN NUMBER 
, NAME OUT VARCHAR2 
, DESG OUT VARCHAR2 
, SALARY OUT float
) AS 

BEGIN

 select ename,job,sal into name,desg,salary from emp where empno=no;

END P_GETEMPDETAILSBYNO;
*/
public class CsGetEmpDetailsTest {
  private static final String  CALL_PROCEDURE="{CALL P_GETEMPDETAILSBYNO(?,?,?,?) }";
	public static void main(String[] args) {
	  Scanner sc=null;
	  int no=0;
	  Connection con=null;
	  CallableStatement cs=null;
	  
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter employee number:::");
				no=sc.nextInt();
			}
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the onnection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create CallableStatement object
			if(con!=null)
				cs=con.prepareCall(CALL_PROCEDURE);
			//reggister OUT params with JDBC data types
			if(cs!=null) {
				cs.registerOutParameter(2,Types.VARCHAR);
				cs.registerOutParameter(3,Types.VARCHAR);
				cs.registerOutParameter(4,Types.FLOAT);
			}
			//set values to IN params
			if(cs!=null)
				cs.setInt(1,no);
			//execute/call PL/SQL procedure
			if(cs!=null)
				cs.execute();
			//gather results from OUT params
			if(cs!=null) {
				System.out.println("Emp name:::"+cs.getString(2));
				System.out.println("Emp Desg:::"+cs.getString(3));
				System.out.println("Emp salary::"+cs.getFloat(4));
			}
	}//try
		catch(SQLException se) {
			se.printStackTrace();
			if(se.getErrorCode()==1403)
			System.out.println("Employee Not found");
			else
				System.out.println("unknow DB problems");
		}
		catch(ClassNotFoundException  cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objs
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
