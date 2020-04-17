package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*SQL> select * from userlist;

UNAME           PWD
--------------- ---------------
raja            rani
king            kingdom
jani            begum
*/

/*create or replace procedure p_authentication(username  varchar2,pass varchar2,result out varchar2)
as
  cnt number(1);
begin
   select count(*) into  cnt  from userlist where uname=username and pwd=pass;
   if(cnt<>0) then
       result:='VALID CREDENTIALS';
  else
      result:='INVALID CREDENTIALS';
  end if;
end;
/*/


public class CsAuthenticationTest {

	 private static final String  CALL_PROCEDURE="{CALL P_AUTHENTICATION(?,?,?) }";
		public static void main(String[] args) {
		  Scanner sc=null;
		    String user=null,pass=null;
		  Connection con=null;
		  CallableStatement cs=null;
		  
			try {
				//read inputs
				sc=new Scanner(System.in);
				if(sc!=null) {
					System.out.println("Enter username:::");
					user=sc.nextLine();
					System.out.println("Enter password::");
					pass=sc.nextLine();
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
					cs.registerOutParameter(3,Types.VARCHAR);
			
				}
				//set values to IN params
				if(cs!=null) {
					cs.setString(1,user);
					cs.setString(2,pass);
				}
				
				//execute/call PL/SQL procedure
				if(cs!=null)
					cs.execute();
				
				//gather results from OUT params
				if(cs!=null) {
					System.out.println("Result is:::"+cs.getString(3));
				}
		}//try
			catch(SQLException se) {
				se.printStackTrace();
			
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

}
