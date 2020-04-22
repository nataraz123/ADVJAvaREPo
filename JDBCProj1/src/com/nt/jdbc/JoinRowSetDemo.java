package com.nt.jdbc;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;
import oracle.jdbc.rowset.OracleJoinRowSet;

public class JoinRowSetDemo {

	public static void main(String[] args) {
		CachedRowSet crowset1=null, crowset2=null;
		JoinRowSet jrowset=null;
		try {
			//Bean style programming
			crowset1=new OracleCachedRowSet();
			crowset1.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crowset1.setUsername("system");
			crowset1.setPassword("manager");
			crowset1.setCommand("SELECT EMPNO,ENAME,JOB,DEPTNO FROM EMP");
			crowset1.setMatchColumn(4);
			crowset1.execute();
			
			
			crowset2=new OracleCachedRowSet();
			crowset2.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crowset2.setUsername("system");
			crowset2.setPassword("manager");
			crowset2.setCommand("SELECT DEPTNO,DNAME,LOC FROM DEPT");
			crowset2.setMatchColumn(1);
			crowset2.execute();
		
			
			//create JoinRowset
			jrowset=new OracleJoinRowSet();
			jrowset.addRowSet(crowset2);
			jrowset.addRowSet(crowset1);
			
			//process the JRowset
			while(jrowset.next()) {
				System.out.println(jrowset.getString(1)+" "+jrowset.getString(2)+" "+jrowset.getString(3)+" "+jrowset.getString(4)+"  "+jrowset.getString(5)+"  "+jrowset.getString(6));
			}
		}//try
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close rowsets
			try {
				if(crowset1!=null)
					crowset1.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(crowset2!=null)
					crowset2.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(jrowset!=null)
					jrowset.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
