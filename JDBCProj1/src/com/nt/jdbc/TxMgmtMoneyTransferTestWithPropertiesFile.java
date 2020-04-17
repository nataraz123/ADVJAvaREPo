package com.nt.jdbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

/*SQL> select * from JDBC_BANK_ACCOUNT;

ACNO HOLDERNAME              BALANCE
---------- -------------------- ----------
1001 raja                      90000
1002 rajesh                    80000
*/

public class TxMgmtMoneyTransferTestWithPropertiesFile {

	public static void main(String[] args) {
		int srcAcno=0,destAcno=0;
		float amount=0.0f;
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		int result[]=null;
		boolean flag=false;
		Properties props=null;
		InputStream is=null;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter source  account number;;;");
				srcAcno=sc.nextInt();
				
				System.out.println("Enter Destination  account number;;;");
				destAcno=sc.nextInt();
				
				System.out.println("Enter Amount to transfer:::");
				amount=sc.nextFloat();
				
			}
			//Locate Properties file and load properties into java.util.Properties class object
			is=new FileInputStream("src/com/nt/commons/jdbc.properties");
			props=new Properties();
			props.load(is);
			
			
			//register JDBC driver s/w
			Class.forName(props.getProperty("jdbc.driver"));
			//establish the connection
			con=DriverManager.getConnection(props.getProperty("jdbc.url"),
					                                                      props.getProperty("jdbc.username"),
					                                                      props.getProperty("jdbc.pwd"));
			//Begin Tx
			if(con!=null)
				 con.setAutoCommit(false);
				
			//create JDBC Statement object
			if(con!=null)
				st=con.createStatement();
			
			//execute the Queries to that batch
			if(st!=null) {
				 //withdraw query
				st.addBatch("UPDATE JDBC_BANK_ACCOUNT SET BALANCE=BALANCE-"+amount+ "WHERE ACNO="+srcAcno);
				 //deposite query
				st.addBatch("UPDATE JDBC_BANK_ACCOUNT SET BALANCE=BALANCE+"+amount+ "WHERE ACNO="+destAcno);
			}
			
			//execute batch
			if(st!=null)
				result=st.executeBatch();
			
			
			//perform Transaction Mangememt
			 for(int i=0;i<result.length;++i) {
				 if(result[i]==0) {
					 flag=true;
					 break;
				 }
			 }
			
			
		}//try
		catch(SQLException se) {
			flag=true;
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			flag=true;
			cnf.printStackTrace();
		}
		catch(Exception se) {
			flag=true;
			se.printStackTrace();
		}
		finally {
			//Tx mgmt code
			try {
				if(con!=null) {
					if(flag) {
						con.rollback();
						System.out.println("Tx  rolledback ---->Money not tranfered");
					}//if
					else {
						con.commit();
						System.out.println("Tx  committed ---->Money  tranfered");
					}
				}//if
				}//try
				catch(SQLException se) {
					se.printStackTrace();
				}
			
			try {
				if(st!=null)
					st.close();
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
