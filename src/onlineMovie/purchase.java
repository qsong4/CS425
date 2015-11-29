package onlineMovie;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class purchase extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	
	boolean flg;
	String movieName,theatreName,time,price,scheduleid,creditCardNum,CVV,expiredate,balance;
	int ID;
	String[] creditCardType={"Master", "Visa"};
	JButton btnPurchase,btnCancel;
	private JTextField textField_7;

	public boolean isCard(String str){ 
		   Pattern pattern = Pattern.compile("^\\d{19}$"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	public boolean isNum3(String str){ 
		   Pattern pattern = Pattern.compile("^\\d{3}$"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	public boolean isNum(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
    public boolean paymentInfo(){
    	boolean f = true;
		OracleDbManager oc = new OracleDbManager();
		PreparedStatement ps ;
		Connection conn;
		String sql = "select CCnumber,CCexp,CVV,balance"
				  + "   from customer "
				  + "  where customerid=? ";
		try {
		conn = oc.getConnection();
        ps = conn.prepareStatement(sql);
        ps.setInt(1,ID);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
        	creditCardNum = rs.getString(1);
            expiredate = rs.getString(2);
        	CVV = rs.getString(3);
        	balance = rs.getString(4);
        	
        	
        	textField_4.setText(creditCardNum);
        	textField_4.setEditable(false);
        	textField_5.setText(CVV);
        	textField_5.setEditable(false);
        	textField_6.setText(expiredate);
        	textField_6.setEditable(false);
        	textField_7.setText(balance);
        	textField_7.setEditable(false);

        }
        
        else{
        	
        	JOptionPane.showMessageDialog(null,"You do not hava the payment information, please input your card information", "System Information", JOptionPane.ERROR_MESSAGE);
        	f= false;
        }
        
       
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return f;
    	
    }

    public void publicPurchase(){
    	
    	OracleDbManager oc = new OracleDbManager();
		PreparedStatement ps ;
		
		Connection conn;
      int balance = Integer.parseInt(textField_7.getText());
      int a =Integer.parseInt(price);
      if(balance>a){
    	  
      
		String sql2 = "insert into purchase values (?,?,?) ";
		try {
			StringBuilder str=new StringBuilder();
			Random random=new Random();
			for(int i=0;i<8;i++){
				str.append(random.nextInt(10));
			}
			int ticketID=Integer.parseInt(str.toString());
			
			
		conn = oc.getConnection();	
		ps=conn.prepareStatement(sql2);
		ps.setInt(1, Integer.parseInt(scheduleid));
		ps.setInt(2, ID);
		ps.setInt(3, ticketID);
		int rs3 = ps.executeUpdate();
		if(rs3==1){
			JOptionPane.showMessageDialog(null,"Successful!", "System Information", JOptionPane.ERROR_MESSAGE);
            
            dispose();
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      
      else{
    	  JOptionPane.showMessageDialog(null,"Your balance is not enough!", "System Information", JOptionPane.ERROR_MESSAGE);
          
      }
    }
    
    public void CustomerPurchase(){
    	OracleDbManager oc = new OracleDbManager();
		PreparedStatement ps,ps1,ps2,ps3 ;
		Connection conn;
		String sql = "select balance"
				  + "   from customer "
				  + "  where customerid=? ";
		try {
		conn = oc.getConnection();
        ps1 = conn.prepareStatement(sql);
        ps1.setInt(1,ID);
        ResultSet rs = ps1.executeQuery();
        if(rs.next()){
        	String a = rs.getString(1);
        	int bal = Integer.parseInt(a);
        	int p = Integer.parseInt(price);
        	if(bal>p){
        		int newBalance = bal-p;
        		String sql1 = "update customer set balance=? where customerid=?";
        		ps2=conn.prepareStatement(sql1);
        		ps2.setInt(1, newBalance);
        		System.out.println(ID);
        		ps2.setInt(2, ID);
        		int rs2 = ps2.executeUpdate();

        		
        		StringBuilder str=new StringBuilder();
    			Random random=new Random();
    			for(int i=0;i<8;i++){
    				str.append(random.nextInt(10));
    			}
    			int ticketID=Integer.parseInt(str.toString());
    			System.out.println(ticketID);
        		String sql2 = "insert into purchase values (?,?,?) ";
        		ps3=conn.prepareStatement(sql2);
        		ps3.setInt(1, Integer.parseInt(scheduleid));
        		ps3.setInt(2, ID);
        		ps3.setInt(3, ticketID);
        		int rs3 = ps3.executeUpdate();
        		if(rs3==1){
        			JOptionPane.showMessageDialog(null,"Successful!", "System Information", JOptionPane.ERROR_MESSAGE);
                    
                    dispose();
        		}
        	}
        	else{
        		JOptionPane.showMessageDialog(null,"You do not have enough balance!", "System Information", JOptionPane.ERROR_MESSAGE);
        	}
        }
        
        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
	/**
	 * Create the frame.
	 */
	public purchase(String[] items) {
		 movieName = items[0];
		 theatreName =items[1];
		 time = items[2];
		 price = items[3];
		 scheduleid = items[4];
		 ID = Integer.parseInt(items[5]);
		

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 487, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.setBounds(6, 6, 475, 134);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Movie Name:");
		lblNewLabel.setBounds(6, 57, 81, 25);
		panel.add(lblNewLabel);
		
		JLabel lblTheatreName = new JLabel("    Theatre :");
		lblTheatreName.setBounds(239, 57, 81, 25);
		panel.add(lblTheatreName);
		
		textField = new JTextField(movieName);
		textField.setEditable(false);
		textField.setBounds(87, 56, 140, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField(theatreName);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(329, 56, 140, 26);

		panel.add(textField_1);
		
		JLabel lblPrice = new JLabel("         Price:");
		lblPrice.setBounds(6, 94, 81, 25);
		panel.add(lblPrice);
		
		textField_2 = new JTextField(price);
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(87, 94, 140, 26);
		panel.add(textField_2);
		
		JLabel lblTime = new JLabel("         Time:");
		lblTime.setBounds(239, 94, 81, 25);
		panel.add(lblTime);
		
		textField_3 = new JTextField(time);
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(329, 94, 140, 26);
		panel.add(textField_3);
		
		JLabel lblNewLabel_1 = new JLabel("                Welcome  to booking System");
		lblNewLabel_1.setFont(UIManager.getFont("RadioButtonMenuItem.acceleratorFont"));
		lblNewLabel_1.setBounds(67, 6, 342, 39);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setBounds(6, 306, 475, 37);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnPurchase = new JButton("Purchase");
		btnPurchase.setBounds(50, 6, 117, 29);
		btnPurchase.addActionListener(this);
		panel_1.add(btnPurchase);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(294, 6, 117, 29);
		btnCancel.addActionListener(this);
		panel_1.add(btnCancel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_2.setBounds(6, 152, 475, 142);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Payment Information :");
		lblNewLabel_2.setBounds(6, 6, 181, 16);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblCreditCardNumber = new JLabel("Credit Card Number:");
		lblCreditCardNumber.setBounds(6, 35, 140, 25);
		panel_2.add(lblCreditCardNumber);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(138, 34, 225, 26);

		panel_2.add(textField_4);
		
		JLabel lblCvv = new JLabel("      CVV:");
		lblCvv.setBounds(299, 72, 64, 25);
		panel_2.add(lblCvv);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(363, 72, 106, 26);

		panel_2.add(textField_5);
		
		JLabel lblExpireDate = new JLabel("Expire Date(MM/YY):");
		lblExpireDate.setBounds(6, 73, 130, 25);
		panel_2.add(lblExpireDate);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(138, 71, 160, 26);

		panel_2.add(textField_6);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Master", "Visa"}));
		comboBox.setBounds(363, 35, 106, 27);
		panel_2.add(comboBox);
		
		JLabel label = new JLabel("Balance :");
		label.setBounds(6, 110, 130, 25);
		panel_2.add(label);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(138, 109, 106, 26);

		panel_2.add(textField_7);
		
		
		
		 flg=paymentInfo();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnPurchase){
			if(flg){
				CustomerPurchase();
			}
			else{
			
			
				if(!isCard(textField_4.getText())){
					JOptionPane.showMessageDialog(null,"Wrong Card Number", "System Information", JOptionPane.ERROR_MESSAGE);
					textField_4.setText("");
				}
				
				else if(!isNum3(textField_5.getText())){
					JOptionPane.showMessageDialog(null,"Wrong CVV Number", "System Information", JOptionPane.ERROR_MESSAGE);
					textField_5.setText("");
					
				}
				else{
		
				publicPurchase();
				}
			}
			
		}
		
		else if(e.getSource()==btnCancel){
			dispose();
			
		}
		
	}
}
