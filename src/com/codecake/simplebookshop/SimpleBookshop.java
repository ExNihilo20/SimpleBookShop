package com.codecake.simplebookshop;
import java.sql.*;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.*;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SimpleBookshop {

	private JFrame frame;
	private JTextField txtBName;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTable table;
	private JTable table_1;
	private JTextField txtbID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleBookshop window = new SimpleBookshop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimpleBookshop() {
		initialize();
		connectToDatabase();
		table_load();
	}
	
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	
	
	
	public void connectToDatabase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String connectionString = "jdbc:mysql://localhost:3306/book_shop?user=root&password=ACBord872*12345";
			connection = DriverManager.getConnection(connectionString);
		} catch (ClassNotFoundException e){
			
		} catch (SQLException e) {
			
		}
	}
	
	
	public void table_load() {
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM book");
			resultSet = preparedStatement.executeQuery();
			table_1.setModel(DbUtils.resultSetToTableModel(resultSet));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1002, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(409, 10, 209, 85);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(41, 90, 407, 216);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(25, 49, 88, 25);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(25, 91, 88, 25);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(25, 126, 88, 25);
		panel.add(lblNewLabel_1_1_1);
		
		txtBName = new JTextField();
		txtBName.setBounds(128, 51, 212, 25);
		panel.add(txtBName);
		txtBName.setColumns(10);
		
		txtEdition = new JTextField();
		txtEdition.setColumns(10);
		txtEdition.setBounds(128, 91, 212, 25);
		panel.add(txtEdition);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(128, 126, 212, 25);
		panel.add(txtPrice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name, edition, price;
				
				name = txtBName.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();
				
				try {
					// this autoexecutes your code when you click the save button
					preparedStatement = connection.prepareStatement("INSERT INTO book(name,edition,price)VALUES(?,?,?)");
					preparedStatement.setString(1, name);
					preparedStatement.setString(2,  edition);
					preparedStatement.setString(3,  price);
					preparedStatement.executeUpdate();
					// feedback for me if execution successful
					JOptionPane.showMessageDialog(null, "Record Added");
					// load new table data whenever the savea button is pressed
					table_load();
					// clear the book name, edition, and price textboxes after the save button is pressed
					txtBName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBName.requestFocus();
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(41, 316, 120, 63);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(182, 316, 120, 63);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtBName.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
				txtBName.requestFocus();
				
			}
		});
		btnClear.setBounds(328, 316, 120, 63);
		frame.getContentPane().add(btnClear);
		
		table = new JTable();
		table.setBounds(535, 341, 186, -178);
		frame.getContentPane().add(table);
		
		JScrollPane scrollPaneTable = new JScrollPane();
		scrollPaneTable.setBounds(471, 88, 492, 291);
		frame.getContentPane().add(scrollPaneTable);
		
		table_1 = new JTable();
		scrollPaneTable.setViewportView(table_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(41, 389, 407, 81);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Book ID");
		lblNewLabel_1_1_2.setBounds(49, 35, 67, 20);
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblNewLabel_1_1_2);
		
		txtbID = new JTextField();
		txtbID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					String id = txtbID.getText();
					
					preparedStatement = connection.prepareStatement("SELECT name,edition,price FROM book WHERE id = ?");
					preparedStatement.setString(1, id);
					ResultSet resultSet = preparedStatement.executeQuery();
					
					if (resultSet.next() == true) {
						
						String name = resultSet.getString(1);
						String edition = resultSet.getString(2);
						String price = resultSet.getString(3);
						
						txtBName.setText(name);
						txtEdition.setText(edition);
						txtPrice.setText(price);
						
					} else {
						
						txtBName.setText("");
						txtEdition.setText("");
						txtPrice.setText("");
						
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		txtbID.setBounds(126, 38, 160, 20);
		txtbID.setColumns(10);
		panel_1.add(txtbID);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name, edition, price, bID;
				
				name = txtBName.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();
				bID = txtbID.getText();
				
				try {
					// this autoexecutes your code when you click the save button
					preparedStatement = connection.prepareStatement("UPDATE book SET name=?,edition=?,price=? WHERE id=?");
					preparedStatement.setString(1, name);
					preparedStatement.setString(2,  edition);
					preparedStatement.setString(3,  price);
					preparedStatement.setString(4, bID);
					
					preparedStatement.executeUpdate();
					// feedback for me if execution successful
					JOptionPane.showMessageDialog(null, "Record Updated");
					// load new table data whenever the save button is pressed
					table_load();
					// clear the book name, edition, and price textboxes after the save button is pressed
					txtBName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBName.requestFocus();
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
			}
		});
		btnUpdate.setBounds(560, 389, 120, 63);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnClear_1_1 = new JButton("Delete");
		btnClear_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bID;
				bID = txtbID.getText();
				
				try {
					// this autoexecutes your code when you click the save button
					preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id=?");
					
					preparedStatement.setString(1, bID);
					
					preparedStatement.executeUpdate();
					// feedback for me if execution successful
					JOptionPane.showMessageDialog(null, "Record Deleted");
					// load new table data whenever the save button is pressed
					table_load();
					// clear the book name, edition, and price textboxes after the save button is pressed
					txtBName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBName.requestFocus();
					
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnClear_1_1.setBounds(771, 389, 120, 63);
		frame.getContentPane().add(btnClear_1_1);
	}
}
