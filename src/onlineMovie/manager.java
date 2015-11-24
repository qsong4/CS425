package onlineMovie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class manager extends JFrame implements ActionListener {
	

	private JPanel contentPane;
	private JTextField textField;
    private JButton btnNewButton,Insert,exit;
	
	public static int row = 20;
	public static String ar[][] = new String[row][4];
	public static final  Object columnName[] = {
	        "Staff Name", "Theatre Name", "Working Location", "Working Time"
	    };
	private JTable table;
	private JButton Modify;



	/**
	 * Create the frame.
	 */
	public manager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 417, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 402, 36);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("       Staff ID");
		lblNewLabel.setBounds(6, 6, 127, 24);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(110, 5, 130, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(this);
		
		btnNewButton.setBounds(264, 5, 117, 29);
		panel.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 54, 405, 328);
		contentPane.add(scrollPane);
		
		
		table = new JTable(ar,columnName);
		scrollPane.setViewportView(table);
		
		Insert = new JButton("Insert");
		Insert.setBounds(141, 394, 117, 36);
		Insert.addActionListener(this);
		contentPane.add(Insert);
		
		exit = new JButton("Empty");
		exit.setBounds(270, 394, 117, 36);
		exit.addActionListener(this);
		contentPane.add(exit);
		
		Modify = new JButton("Modify");
		Modify.addActionListener(this);
		Modify.setBounds(16, 394, 117, 36);
		contentPane.add(Modify);
	}
	
    private void clear(String[][] ar){
        for(int i = 0; i < row; i++){
            ar[i][0] = "";
            ar[i][1] = "";
            ar[i][2] = "";
            ar[i][3] = "";
        }
    }

	public void searchSchedule(){
		OracleDbManager oc = new OracleDbManager();
		PreparedStatement ps ;
		Connection conn;
		String a= textField.getText();
		try{
		int staffID = Integer.parseInt(a);
		
		String sql = "select a.name Staff_Name ,b.name Theatre_name,b.location,c.time"
                      +" from STAFF a,theatre b, employment c"
                      +" where a.staffid = c.staffid"
                      +"  and c.theatreid = b.theatreid"
                      +"  and a.staffid = ?";
		
		
			conn = oc.getConnection();
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, staffID);
	        ResultSet rs = ps.executeQuery();
	        if(!rs.next()){
	        	JOptionPane.showMessageDialog(null,"The staff does not have working schedule！", "System Information", JOptionPane.ERROR_MESSAGE);
				textField.setText("");	
	        }else{
	        	
	        	for(int i=0;i<4;i++){
	    	    	ar[0][i] = rs.getString(i+1);
	    	    	}
	        	
	    	    while(rs.next()){
	    	    	int j=1;
	    	    	System.out.println("ssss");
	    	    	for(int i=0;i<4;i++){
	    	    	ar[j][i] = rs.getString(i+1);
	    	    	}
	    	    	
	    	        j++;
	    	    
	    	        }
	        }
	        

	    
	    table.repaint();
	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NumberFormatException e1){
			JOptionPane.showMessageDialog(null,"Please input the number", "System Information", JOptionPane.ERROR_MESSAGE);
			textField.setText("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnNewButton){
			searchSchedule();
		}
		
		else if(e.getSource()==Modify){
			Update u = new Update();
			u.setVisible(true);
		}
		
		else if (e.getSource()==Insert){
			Insert insert = new Insert();
			insert.setVisible(true);
			
		}
		
		else if(e.getSource()==exit){
			System.exit(1);
			
		}
	}
}
