package onlineMovie;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class Login extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnRegister,btnLogin,btnSkip;
	static Login frame;
	public boolean flg = true;
	private JButton btnAdmin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(6, 6, 438, 217);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User ID    :");
		lblNewLabel.setBounds(99, 66, 95, 25);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("      Welcome to Online Movie Booking System");
		lblNewLabel_1.setFont(UIManager.getFont("MenuItem.font"));
		lblNewLabel_1.setBounds(47, 6, 339, 37);
		panel.add(lblNewLabel_1);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setBounds(99, 103, 95, 25);
		panel.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(173, 65, 141, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(173, 103, 141, 26);
		panel.add(passwordField);
		
		JLabel lblNewLabel_2 = new JLabel("Design by--sqy,yq,cl");
		lblNewLabel_2.setBounds(267, 179, 165, 32);
		panel.add(lblNewLabel_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(6, 227, 438, 45);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnRegister = new JButton("Register");
		btnRegister.setBounds(6, 10, 109, 29);
		btnRegister.addActionListener(this);
		panel_1.add(btnRegister);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(111, 10, 109, 29);
		btnLogin.addActionListener(this);
		panel_1.add(btnLogin);
		
		btnSkip = new JButton("Skip");
		btnSkip.setBounds(215, 10, 109, 29);
		btnSkip.addActionListener(this);
		panel_1.add(btnSkip);
		
		btnAdmin = new JButton("Admin");
		btnAdmin.setBounds(323, 10, 109, 29);
		panel_1.add(btnAdmin);
	}

	/**
	 * The function of Login.
	 */
	
	public boolean fLogin(){
	    boolean f = false;
		
		OracleDbManager oc = new OracleDbManager();
		PreparedStatement ps;
		Connection conn;
		String sql="select * "
                   +"  from customer"
                  +"  where customer.customerid=? "
                    +"  and customer.password= ? ";
       
        try {
        int customerID = Integer.valueOf(textField.getText());
        String password = passwordField.getText();
        System.out.println(password);
		conn = oc.getConnection();
        ps = conn.prepareStatement(sql);
     
		ps.setInt(1, customerID);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			 return !f;
		}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NumberFormatException e1){
			JOptionPane.showMessageDialog(null,"Please input the number ID", "System Information", JOptionPane.ERROR_MESSAGE);
			textField.setText("");
		}
	
       return f;
        
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnLogin){
			
			boolean f = fLogin();
			System.out.println(f);
			if(f){
				int ID = Integer.valueOf(textField.getText());
				MainWin mw = new MainWin(ID);
				mw.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(null,"Wrong User ID or Password, please input again!", "System Information", JOptionPane.ERROR_MESSAGE);
				textField.setText("");
				passwordField.setText(null);
			}
			frame.setVisible(false);
			
		}
		
		if(e.getSource()==btnSkip){
			
			StringBuilder str=new StringBuilder();
			Random random=new Random();
			for(int i=0;i<8;i++){
				str.append(random.nextInt(10));
			}
			int ID=Integer.parseInt(str.toString());
			
			OracleDbManager oc = new OracleDbManager();
			PreparedStatement ps;
			Connection conn;
			String sql ="insert into users (userid,name) values (?,'public guest') ";
			try {
				
			conn = oc.getConnection();
	        ps = conn.prepareStatement(sql);
	        
			ps.setInt(1, ID);
			int rs = ps.executeUpdate();
			
			if(rs==1){
			MainWin mw = new MainWin(ID);
			mw.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(null,"System error, please try again", "System Information", JOptionPane.ERROR_MESSAGE);
				textField.setText("");
				passwordField.setText(null);
			}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.setVisible(false);
		}
	}
}
