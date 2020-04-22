package com.nt.jdbc;

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;

import oracle.jdbc.rowset.OracleFilteredRowSet;

class Filter1 implements Predicate{
	 private   String col;
	 private   String data;
	public Filter1(String col, String data) {
		System.out.println("Filter1:: 2-param constructor");
		 this.col=col;
		 this.data=data;
	}

	@Override
	public boolean evaluate(RowSet rs) {
		System.out.println("Filter1.evaluate()");
		try {
		String val=rs.getString(col);
		 if(val.startsWith(data)) {
			 return true;
		 }
		 else {
			 return false;
		 }
		}//try
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean evaluate(Object value, int column) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean evaluate(Object value, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
}

public class FilteredRowSetDemo {
	public static void main(String[] args) {
		FilteredRowSet frowset=null;
		try {
			//create FilteredRowSet
			frowset=new OracleFilteredRowSet();
			frowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			frowset.setUsername("system");
			frowset.setPassword("manager");
			frowset.setCommand("SELECT EMPNO,ENAME,JOB,SAL FROM EMP ");
			//add Filter conditiion
			frowset.setFilter(new Filter1("ENAME","A"));
			frowset.execute();
			
			while(frowset.next()) {
				System.out.println(frowset.getInt(1)+"  "+frowset.getString(2)+"  "+frowset.getString(3)+" "+frowset.getFloat(4));
			}
			
		}//try
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objs
			try {
				if(frowset!=null)
					frowset.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
		
	}

}
