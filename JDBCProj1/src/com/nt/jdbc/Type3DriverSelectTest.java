package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Type3DriverSelectTest {
  private static final String GET_ALL_STUDENTS_QUERY="SELECT * FROM PRODUCT";
    public static void main(String[] args) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			
			//Load jdbc driver class
			Class.forName("ids.sql.IDSDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:ids://localhost:12/conn?dsn=accdsn");
			//create Statement object
			if(con!=null)
			  ps=con.prepareStatement(GET_ALL_STUDENTS_QUERY, 
					                                      ResultSet.TYPE_SCROLL_SENSITIVE,
					                                      ResultSet.CONCUR_UPDATABLE);
			 
			//send and execute SQL Query to DB s/w
			if(ps!=null)
			  rs=ps.executeQuery();
			if(rs!=null) {
				//display records top - bottom
				System.out.println("Top -Botton records");
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+"  "+rs.getFloat(4));
				}//while
				
				System.out.println("Bottom to Top records");
				//display records  bottom - top
				while(rs.previous()) {
					System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				}
				
				System.out.println(".....................................");
				rs.first();
				System.out.println(rs.getRow()+"---->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
	          System.out.println("......................................");
				rs.last();
				System.out.println(rs.getRow()+"---->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
               System.out.println("....................................");    
				rs.absolute(3);
                System.out.println(rs.getRow()+"---->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
                System.out.println("....................................");    
				rs.absolute(-2);
                System.out.println(rs.getRow()+"---->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
                System.out.println("....................................");    
				rs.relative(-3);
                System.out.println(rs.getRow()+"---->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
                System.out.println("....................................");    
				rs.relative(2);
                System.out.println(rs.getRow()+"---->"+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
 			
                System.out.println("Is cursor is there in First record ::"+rs.isFirst());
                System.out.println("...............................");
                System.out.println("Is Cursor is there in last record::"+rs.isLast());
			}//if
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
				if(rs!=null)
					rs.close();
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
				
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
