package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*create or replace function fx_getEmpDetailsByNo(no in number, name out varchar, desg out varchar)return number
as
 salary number(10,2);
begin
  select ename,job,sal into name,desg,salary  from emp where empno=no;
  return salary;
end;
/*/

public class CsFxGetEmpDetailsTest {
  private static final String  CALL_FUNCTION="{ ?=call FX_GETEMPDETAILSBYNO(?,?,?) }";
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
				cs=con.prepareCall(CALL_FUNCTION);
			//reggister OUT params,return with JDBC data types
			if(cs!=null) {
				cs.registerOutParameter(1,Types.FLOAT);  //return param
				cs.registerOutParameter(3,Types.VARCHAR);
				cs.registerOutParameter(4,Types.VARCHAR);
			}
			//set values to IN params
			if(cs!=null)
				cs.setInt(2,no);
			//execute/call PL/SQL procedure
			if(cs!=null)
				cs.execute();
			//gather results from OUT params,return Param
			if(cs!=null) {
				System.out.println("Emp salary:::"+cs.getFloat(1));  //return param
				System.out.println("Emp name:::"+cs.getString(3));
				System.out.println("Emp desg::"+cs.getString(4));
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
