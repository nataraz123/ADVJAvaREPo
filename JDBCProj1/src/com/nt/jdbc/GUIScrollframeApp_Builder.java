package com.nt.jdbc;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;

public class GUIScrollframeApp_Builder extends JFrame {
  private static final String  GET_ALL_STUDENTS="SELECT SNO,SNAME,SADD,AVG FROM STUDENT";
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIScrollframeApp_Builder frame = new GUIScrollframeApp_Builder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIScrollframeApp_Builder() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sno");
		lblNewLabel.setBounds(42, 25, 46, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setForeground(Color.BLUE);
		textField.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField.setEditable(false);
		textField.setBounds(110, 22, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("sname");
		lblNewLabel_1.setBounds(42, 68, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setForeground(Color.BLUE);
		textField_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_1.setEnabled(false);
		textField_1.setEditable(false);
		textField_1.setBounds(110, 65, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("sadd");
		lblNewLabel_2.setBounds(42, 114, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setForeground(Color.BLUE);
		textField_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_2.setEnabled(false);
		textField_2.setEditable(false);
		textField_2.setBounds(110, 111, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("avg");
		lblNewLabel_3.setBounds(42, 161, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		textField_3 = new JTextField();
		textField_3.setForeground(Color.BLUE);
		textField_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_3.setEnabled(false);
		textField_3.setEditable(false);
		textField_3.setBounds(110, 158, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("first");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rs.first();
					//set record values to text boxes
					textField.setText(rs.getString(1));
					textField_1.setText(rs.getString(2));
					textField_2.setText(rs.getString(3));
					textField_3.setText(rs.getString(4));
					
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
				
			}//actionPerfomed(-)
		});
		btnNewButton.setBounds(22, 200, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("next");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(!rs.isLast() && !rs.isAfterLast()) {
						rs.next();
					//set record values to text boxes
					textField.setText(rs.getString(1));
					textField_1.setText(rs.getString(2));
					textField_2.setText(rs.getString(3));
					textField_3.setText(rs.getString(4));
					}
					
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
			}//actionPerfomed(-)
		});
		btnNewButton_1.setBounds(131, 200, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("previous");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!rs.isFirst() && !rs.isBeforeFirst()) {
						rs.previous();
					//set record values to text boxes
					textField.setText(rs.getString(1));
					textField_1.setText(rs.getString(2));
					textField_2.setText(rs.getString(3));
					textField_3.setText(rs.getString(4));
					}
					
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
				
			}//actionPerfomed(-)
		});
		btnNewButton_2.setBounds(230, 200, 89, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("last");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				
						rs.last();
					//set record values to text boxes
					textField.setText(rs.getString(1));
					textField_1.setText(rs.getString(2));
					textField_2.setText(rs.getString(3));
					textField_3.setText(rs.getString(4));
					
					
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
			}//actionPerfomed(-)
		});
		btnNewButton_3.setBounds(335, 200, 89, 23);
		contentPane.add(btnNewButton_3);
	
		initializeJdbc();
		/*
		 * textField_1.setEditable(false); textField_2.setEditable(false);
		 * textField_3.setEditable(false);
		 */
		
	}//constructor
	
	
	private  void initializeJdbc() {
		System.out.println("GUIScrollframeApp_Builder.initializeJdbc()");
		try {
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//estalish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system", "manager");
			//create PreparedStatement object
			ps=con.prepareStatement(GET_ALL_STUDENTS, ResultSet.TYPE_SCROLL_SENSITIVE,
					                                                                            ResultSet.CONCUR_UPDATABLE);
			//create ScrollableResultSet object
			rs=ps.executeQuery();
			
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
			
	}//initializeJdbc
}
