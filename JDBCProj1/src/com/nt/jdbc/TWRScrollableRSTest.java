package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.exceptions.RSAException;

public class TWRScrollableRSTest {
  private static final String GET_ALL_STUDENTS_QUERY="SELECT SNO,SNAME,SADD,AVG FROM STUDENT";
    public static void main(String[] args) {
	
		
			
		
			//establish the connection
			try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager")){
		    	if(con!=null) {
				  try(Statement st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE)){
		            	//send and execute SQL Query to DB s/w
			          if(st!=null) {
			               try(ResultSet rs=st.executeQuery(GET_ALL_STUDENTS_QUERY)){
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
			              }//try3
			               
			             }//if
			          }//try2
		    	}//if
			}//try1
			catch(SQLException se) {
				se.printStackTrace();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//main
}//class
