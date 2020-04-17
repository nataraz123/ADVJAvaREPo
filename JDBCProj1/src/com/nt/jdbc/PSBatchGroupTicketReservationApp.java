package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*SQL> create table Rail_Reservation(tktId number(5) primary key, psgnrName varchar2(15),sourcePlace varchar2(15),destPlace varchar2(15),fare float);

Table created.

SQL> create sequence tktId start with 1 increment by 1;

Sequence created.
*/
public class PSBatchGroupTicketReservationApp {
  private static final String  TICKET_RESERVATION="INSERT INTO RAIL_RESERVATION VALUES(TKTID.NEXTVAL,?,?,?,?)";
	public static void main(String[] args) {
			Scanner sc=null;
			String srcPlace=null,destPlace=null;
			float fare=0.0f;
			int count=0;
			Connection con=null;
			PreparedStatement ps=null;
			String psgnrName=null;
			int  result[]=null;
		//read inputs
		try {
			//create Scanner
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Passegener's group count::");
				count=sc.nextInt();
				System.out.println("Enter start place of the journey::");
				srcPlace=sc.next();
				System.out.println("Enter Dest Place of the journey::");
				destPlace=sc.next();
				System.out.println("Enter Ticket Fare Amount::");
				fare=sc.nextFloat();
			}
			
			//register JDBC Driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system" ,"manager");
			//create  Create PreparedStatement object having PreCompiled SQL Query
			if(con!=null)
				ps=con.prepareStatement(TICKET_RESERVATION);
			//add query param values to batch by group member details..
			if(ps!=null && sc!=null) {
				for(int i=1;i<=count;++i) {
					System.out.println("Enter "+i+" passenger details::");
					psgnrName=sc.next();
					///add query param values to the batch
					ps.setString(1, psgnrName);
					ps.setString(2,srcPlace);
					ps.setString(3, destPlace);
					ps.setFloat(4, fare);
					ps.addBatch();
				}//for
				
			}//if
			
			//execut the batch
			if(ps!=null) 
				result=ps.executeBatch();
			
		  //process the results
			if(result!=null) 
				System.out.println("Group Ticket reservation is done successfully");
			else
				System.out.println("Group Ticket reservation is  failed");
			
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
