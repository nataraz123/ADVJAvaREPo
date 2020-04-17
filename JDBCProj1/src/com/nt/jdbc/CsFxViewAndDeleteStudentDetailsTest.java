package com.nt.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

/*create or replace function fx_view_delete_student_by_sno(no in number ,
        cnt out number)return sys_refcursor
as
details  sys_refcursor;
begin
open details for
select sno,sname,sadd ,avg from student where sno=no;
delete from student where sno=no;
cnt:= SQL%ROWCOUNT;
return details;
end;
/*/


public class CsFxViewAndDeleteStudentDetailsTest {
  private static final String  CALL_FUNCTION="{ ?=call FX_VIEW_DELETE_STUDENT_BY_SNO(?,?) }";
	public static void main(String[] args) {
	  Scanner sc=null;
	  int no=0;
	  Connection con=null;
	  CallableStatement cs=null;
	  ResultSet rs=null;
	  int count=0;
	  boolean flag=false;
	  
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
				cs.registerOutParameter(1,OracleTypes.CURSOR);  //return param
				cs.registerOutParameter(3,Types.INTEGER);
			
			}
			//set values to IN params
			if(cs!=null)
				cs.setInt(2,no);
			//execute/call PL/SQL procedure
			if(cs!=null)
				cs.execute();
			//gather results from OUT params,return Param
			if(cs!=null) {
				//get ResultSEt from return param
				rs=(ResultSet)cs.getObject(1);
				count=cs.getInt(3);
			}
			
			if(rs!=null) {
				while(rs.next()) {
					flag=true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4));
				}
				if(flag==false) {
					System.out.println("record not found");
					return;
				}
				else {
					if(count==1)
						System.out.println("Record found and deleted");
				}
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
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
