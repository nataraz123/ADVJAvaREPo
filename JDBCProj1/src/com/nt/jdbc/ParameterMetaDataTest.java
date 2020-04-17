package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ParameterMetaDataTest {
  private static final String GET_ALL_STUDENTS_QUERY="SELECT SNO,SNAME,SADD,AVG FROM STUDENT WHERE  SADD IN(?,?,?)";
    public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		ParameterMetaData pmd=null;
		int paramCnt=0;
		try {
			
			//Load jdbc driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create Statement object
			if(con!=null)
			   ps=con.prepareStatement(GET_ALL_STUDENTS_QUERY);
			//create ParameterMetaData object
			if(ps!=null)
				pmd=ps.getParameterMetaData();
			
			System.out.println("pmd class name::"+pmd.getClass());
			//get parameters count and details
			if(pmd!=null) {
				paramCnt=pmd.getParameterCount();
				
				for(int i=1;i<=paramCnt;++i) {
					System.out.println(i+" param mode::"+pmd.getParameterMode(i));
					System.out.println(i+" param type::"+pmd.getParameterTypeName(i));
					System.out.println(i+" param is signed??"+pmd.isSigned(i));
				}//for
				 
			}// if


	
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
				//close jdbc objs
			
				
				try {
					if(ps!=null)
						ps.close();
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
				
			}//finally

	}//main
}//class
