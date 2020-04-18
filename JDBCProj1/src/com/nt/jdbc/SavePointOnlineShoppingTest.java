package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Scanner;

/*SQL> select * from jdbc_bank_account;

ACNO HOLDERNAME              BALANCE
---------- -------------------- ----------
1001 raja                      75000
1002 rajesh                    94000

SQL> select * from product;

PID PNAME                     PRICE        QTY
---------- -------------------- ---------- ----------
  1 table                      9000         10
  2 chair                      8000          3
  3 sofa                      18000          3
*/

public class SavePointOnlineShoppingTest {
  private static final String PURCHASE_PRODUCT_QUERY="UPDATE PRODUCT SET QTY=QTY-1 WHERE PID=?";
  private static final String PAYMENT_QUERY="UPDATE JDBC_BANK_ACCOUNT SET BALANCE=BALANCE-(SELECT PRICE FROM PRODUCT WHERE PID=?) WHERE ACNO=?";
	public static void main(String[] args) {
		Scanner sc=null;
		int pid=0;
		int acno=0;
		Connection con=null;
		PreparedStatement ps1=null,ps2=null;
		int result1=0,result2=0;
		Savepoint sp=null;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Product Id::");
				pid=sc.nextInt();
				System.out.println("Enter  Bank Accoint number:");
				acno=sc.nextInt();
			}
				
				//Load jdbc driver class
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//establish the connection
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
				//Begin Tx
				con.setAutoCommit(false);
				//create PreparedStatement objects
				if(con!=null) {
				   ps1=con.prepareStatement(PURCHASE_PRODUCT_QUERY);
				   ps2=con.prepareStatement(PAYMENT_QUERY);
				}
				//set values to query params and execute purchase query
				if(ps1!=null) {
				    ps1.setInt(1,pid);
				    result1=ps1.executeUpdate();
				}
				//create Named SavePoint
				if(con!=null)
					sp=con.setSavepoint("sp1");
				
				//set values to query and execute payment query
				if(ps2!=null) {
					ps2.setInt(1, pid);
					ps2.setInt(2, acno);
					result2=ps2.executeUpdate();
				}
				
				if(result1==1 && result2==1) {
					con.commit();
					System.out.println("Tx Committed , Purchase and payment is done..");
				}
				else if(result1==0) {
					con.rollback();
					System.out.println("Tx rolledback, Becoz product is not avaliable");
				}
				else if(result1==1 && result2==0) {
					  con.rollback(sp);
					  con.commit();
					  System.out.println("Purchase is done ,Payment is failed ,So the Cash Delivery is enabled");
				}
					
			
			
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
				if(ps2!=null)
					ps2.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			
			try {
				if(ps1!=null)
					ps1.close();
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
