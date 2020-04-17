package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIScrollFrameTest  extends  JFrame implements ActionListener{
	private static final String GET_ALL_STUDENTS="SELECT SNO,SNAME,SADD,AVG FROM STUDENT";
	private  JLabel lno,lname,ladd,lavg;
	private JTextField tno,tname,tadd,tavg;
	private JButton bFirst,bNext,bLast,bPrevious;
	 private Connection con;
	 private PreparedStatement ps;
	 private ResultSet rs;

	
	public GUIScrollFrameTest() {
		System.out.println("GUIScrollFrameTest:: 0-param constructor");
		setTitle("GUI Scroll Frame");
		setSize(300,300);
		setBackground(Color.GRAY);
		setLayout(new FlowLayout());
		//add comps
		lno=new JLabel("student number::");
		add(lno);
		tno=new JTextField(10);
		add(tno);
		
		lname=new JLabel("student name::");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		
		ladd=new JLabel("student address::");
		add(ladd);
		tadd=new JTextField(10);
		add(tadd);
		
		lavg=new JLabel("student avg::");
		add(lavg);
		tavg=new JTextField(10);
		add(tavg);
		
		bFirst=new JButton("first");
		bFirst.addActionListener(this);
		add(bFirst);
		bNext=new JButton("next");
		bNext.addActionListener(this);
		add(bNext);
		bLast=new JButton("Last");
		bLast.addActionListener(this);
		add(bLast);
		bPrevious=new JButton("previous");
		bPrevious.addActionListener(this);
		add(bPrevious);
	
		  setVisible(true);
		  setDefaultCloseOperation(EXIT_ON_CLOSE);
		  
		  initailizeJdbc();
		  
		  //disable text boxes editing
		  tno.setEditable(false);
		  tname.setEditable(false);
		  tavg.setEditable(false);
		  tadd.setEditable(false);
		  
		  this.addWindowListener(new WindowAdapter() {
			  
			  @Override
			public void windowClosing(WindowEvent e) {
				  System.out.println("GUIScrollFrameTest.GUIScrollFrameTest().new WindowAdapter() {...}.windowClosing()");
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
			  
			  }
		});
	
	}
	
	private void  initailizeJdbc() {
		System.out.println("GUIScrollFrameTest.initailizeJdbc()");
		try {
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create JDBC PreparedStatement object
			ps=con.prepareStatement(GET_ALL_STUDENTS,
					                                       ResultSet.TYPE_SCROLL_SENSITIVE,
					                                      ResultSet.CONCUR_UPDATABLE);
			//create Scrollable ResultSet object
			rs=ps.executeQuery();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}//initializeJdbc
	

	public static void main(String[] args) {
		System.out.println("GUIScrollFrameTest.main()");
		new GUIScrollFrameTest();	

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("GUIScrollFrameTest.actionPerformed()");
		boolean flag=false;
		
		if(ae.getSource()==bFirst) {
			System.out.println("First Button is clicked");
			try {
				rs.first();
				flag=true;
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
		else if(ae.getSource()==bNext) {
			System.out.println("Next Button is  clicked");
			try {
				if(!rs.isLast()) {
				 rs.next();	
				flag=true;
				}
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}
		else if(ae.getSource()==bPrevious) {
			System.out.println("Previous Button is clicked");
			try {
				if(!rs.isFirst()) {
				 rs.previous();
				flag=true;
				}
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			
		}
		else {
			try {
				
				 rs.last();
				flag=true;
			}
			
			catch(SQLException se) {
				se.printStackTrace();
			}
			System.out.println("Last Button is clicked");
		}//else
		
		//write  ResultSet record values to Text boxes
		if(flag==true) {
			try {
			tno.setText(rs.getString(1));
			tname.setText(rs.getString(2));
			tadd.setText(rs.getString(3));
			tavg.setText(rs.getString(4));
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//if
		
		
	}//actionPerformed(-)

}//class
