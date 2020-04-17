package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIStudentRegustrationApp extends JFrame implements ActionListener ,WindowListener{
	private static final String STUDENT_INSERT_QUERY="INSERT INTO STUDENT VALUES(SNO_SEQ.NEXTVAL,?,?,?)";
         private JLabel lsname,lsadd,lavg,lresult;
         private  JButton register;
         private JTextField  tsname,tsadd,tavg;
         private  Connection con;
         PreparedStatement ps;
         
         public GUIStudentRegustrationApp() {
        	 System.out.println("GUIStudentRegustrationApp:: 0-param constructor");
        	 // Frame window settings
        	 setTitle("Student registration");
        	 setSize(400,400);
        	 setBackground(Color.GRAY);
        	 setLayout(new FlowLayout());
        	 //add comps
        	 lsname=new JLabel("Student name::");
        	 add(lsname);
        	 tsname=new JTextField(10);
        	 add(tsname);
        	 
        	 lsadd=new JLabel("Student addrs::");
        	 add(lsadd);
        	 tsadd=new JTextField(10);
        	 add(tsadd);
        	 
        	 lavg=new JLabel("Student avg:");
        	 add(lavg);
        	 tavg=new JTextField(10);
        	 add(tavg);
        	 
        	 register=new JButton("register");
        	 register.addActionListener(this); // we are linking current invoking as  ActionListener to button comp to handler ActionEvent
        	 add(register);
        	 
        	 lresult=new JLabel("");
        	 add(lresult);
        	 
        	 setVisible(true);
        	 
        	 //terminate appliccation when window is closed
        	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	 		 
        	 // create JDBC objs
        	 initializeJdbc();
        	 
        	 //Add windowListner to frame
			this.addWindowListener(this);
		}
	
	private void  initializeJdbc() {  //helper methods that will used with in the class will be taken as private methods
		System.out.println("GUIStudentRegustrationApp.initializeJdbc()");
		try {
			// Load jdbc driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			//create JDBC PreparedStatement obj
			ps=con.prepareStatement(STUDENT_INSERT_QUERY);
			
		}//try
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}//initializeJDBc
	
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
	System.out.println("GUIStudentRegustrationApp.actionPerformed()");
	String name=null,addrs=null;
	float avg=0.0f;
	int count=0;
	try {
		//read text values
		name=tsname.getText();
		addrs=tsadd.getText();
		avg=Float.parseFloat(tavg.getText());
		//set above values as the query param values
		ps.setString(1,name);
		ps.setString(2, addrs);
		ps.setFloat(3, avg);
		//execute the query
		count=ps.executeUpdate();
		//process the result
		if(count==0) {
			lresult.setForeground(Color.RED);
			lresult.setText("Student registration failed");
		}
		else {
			lresult.setForeground(Color.GREEN);
			lresult.setText("Student registration Succeded");
		}
	}//try
	catch(SQLException se) {
		se.printStackTrace();
		lresult.setForeground(Color.RED);
		lresult.setText("Student registration failed--DB Probblem::"+se.getMessage());
	}
	catch(Exception e) {
		e.printStackTrace();
		lresult.setForeground(Color.RED);
		lresult.setText("Student registration failed--unknown Probblem::");
		}
	
	
	}//actionPerformed(-)
	
	public static void main(String[] args) {
		System.out.println("start of main(-) method");
		 new GUIStudentRegustrationApp();
		 System.out.println("end of main(-) method");
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("GUIStudentRegustrationApp.windowClosing()");
		try {
			//close jdbc objs
			if(ps!=null)
				ps.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
		try {
			//close jdbc objs
			if(con!=null)
				con.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("GUIStudentRegustrationApp.windowClosed()");
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
