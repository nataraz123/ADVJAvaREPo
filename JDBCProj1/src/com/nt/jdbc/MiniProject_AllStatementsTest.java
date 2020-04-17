package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*SQL> create table  All_student(sno number(5) primary key,sname varchar2(20),m1 number(5),m2 number(5),m3 number(5));

create or replace procedure p_find_pass_fail(m1 in number,m2 in number,m3 in number,result out varchar)
as
begin
   if(m1<35 or m2<35 or m3<35)then
       result:='FAIL';
  else
     result:='PASS';
end if;
end;
/
*/

public class MiniProject_AllStatementsTest extends  JFrame implements ActionListener,WindowListener {
	private static final String  GET_STUDENT_BY_NO="SELECT SNAME,M1,M2,M3 FROM ALL_STUDENT WHERE SNO=?";
	private static final String CALL_PROCEDURE="{CALL P_FIND_PASS_FAIL(?,?,?,?)}";
	private static final String GET_ALL_SNOS="SELECT SNO FROM ALL_STUDENT";
	
	private  JLabel lno,lname,lm1,lm2,lm3,lresult;
	private JTextField tname,tm1,tm2,tm3,tresult;
	private  JComboBox<Integer>  tno;
	private JButton bDetails,bResult;
	private Connection con;
	private Statement st;
	private PreparedStatement ps;
	private CallableStatement cs;
	ResultSet rs1,rs2;
	
	public  MiniProject_AllStatementsTest() {
		System.out.println("MiniProject_AllStatementsTest::0-param constructor");
		setSize(400,400);
		setBackground(Color.GRAY);
		setTitle("Mini Project");
		setLayout(new FlowLayout());
		
		// add comps
		lno=new JLabel("select sno::");
		add(lno);
		tno=new JComboBox<Integer>();
		add(tno);
		
		bDetails=new JButton("Details");
		add(bDetails);
		bDetails.addActionListener(this);
		
		lname=new JLabel("Select sname");
		add(lname);
		tname=new JTextField(10);
		add(tname);
		
		lm1=new JLabel("Marks1");
		add(lm1);
		tm1=new JTextField(10);
		add(tm1);
		
		lm2=new JLabel("Marks2");
		add(lm2);
		tm2=new JTextField(10);
		add(tm2);
		
		lm3=new JLabel("Marks3");
		add(lm3);
		tm3=new JTextField(10);
		add(tm3);
		
		bResult=new JButton("Result");
		add(bResult);
		bResult.setToolTipText("click here for result");
		bResult.addActionListener(this);
		
		lresult=new JLabel("Result");
		add(lresult);
		tresult=new JTextField(10);
		
		add(tresult);
		
		setVisible(true);
		
		//disable editing on text boxes
		tm1.setEditable(false);
		tm2.setEditable(false);
		tm3.setEditable(false);
		tname.setEditable(false);
		tresult.setEditable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//add WindowListener to frame window
		this.addWindowListener(this);
		
		initializeJdbc();
	}
	
	private void  initializeJdbc() {
		System.out.println("MiniProject_AllStatementsTest.initializeJdbc()");
		try {
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","System" ,"manager");
			//create Simple Statement object
			st=con.createStatement();
			//create PreparedStatement object
			ps=con.prepareStatement(GET_STUDENT_BY_NO);
			//create CallableStatment object
			cs=con.prepareCall(CALL_PROCEDURE);
			cs.registerOutParameter(4,Types.VARCHAR);
			
			//add snos to ComboBox
			rs1=st.executeQuery(GET_ALL_SNOS);
			//copy snos of rs1 to ComboBox
			while(rs1.next()) {
				tno.addItem(rs1.getInt(1));
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
				if(rs1!=null)
					rs1.close();
			}
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
		}//finally
	}//initializeJdbc
	
	

	public static void main(String[] args) {
		System.out.println("MiniProject_AllStatementsTest.main()(start)");
	  new MiniProject_AllStatementsTest();
	  System.out.println("MiniProject_AllStatementsTest.main()(end)");

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
	  System.out.println("MiniProject_AllStatementsTest.actionPerformed()");
	  int no=0;
	  int m1=0,m2=0,m3=0;
	  if(ae.getSource()==bDetails) {
		  System.out.println("Details button is clicked");
		  try {
			  //get Selected Item from Text box
			  no=(int) tno.getSelectedItem();
			  //set query param values
			  ps.setInt(1, no);
			  //execute the query
			  	rs2=ps.executeQuery();
			  	//process the rs2 and set values to text boxes
			  	if(rs2.next()) {
			  		tname.setText(rs2.getString(1));
			  		tm1.setText(rs2.getString(2));
			  		tm2.setText(rs2.getString(3));
			  		tm3.setText(rs2.getString(4));
			  	}
			  	tresult.setText("");
		  }//try
		  catch(SQLException se) {
			  se.printStackTrace();
		  }
		  
	  }
	  else {
		  System.out.println("Result Button is clicked");
		  
		  try {
			  //read text box values and set them query params values
			  m1=Integer.parseInt(tm1.getText());
			  m2=Integer.parseInt(tm2.getText());
			  m3=Integer.parseInt(tm3.getText());
			  cs.setInt(1, m1);
			  cs.setInt(2, m2);
			  cs.setInt(3, m3);
			  //call PL/SQL procedure
			  cs.execute();
			  //gather result from OUT Param and set to Text box
			  tresult.setText(cs.getString(4));
			  
		  }//try
		  catch(SQLException se) {
			  se.printStackTrace();
		  }
		  
	  }//else
		  
	  
		
	}//actionPerformed(-)

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
        System.out.println("MiniProject_AllStatementsTest.windowClosing()");
		//close jdbbc objs
        try {
        	if(rs2!=null)
        		rs2.close();
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
        	if(cs!=null)
        		cs.close();
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

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
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

}//class
