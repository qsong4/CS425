package onlineMovie;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Update extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField scheduleID;
	private JTextField staffID;
	private JTextField theatreID;
	private JTextField times;
	
	JButton btnSearch,btnUpdate;
	private JButton btnModify;



	/**
	 * Create the frame.
	 */
	public Update() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 261, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 249, 227);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("scheduleID :");
		lblNewLabel.setBounds(6, 18, 87, 25);
		panel.add(lblNewLabel);
		
		scheduleID = new JTextField();
		scheduleID.setBounds(87, 17, 130, 26);
		panel.add(scheduleID);
		scheduleID.setColumns(10);
		
		JLabel lblScheduleid = new JLabel("    staffID :");
		lblScheduleid.setBounds(6, 72, 87, 25);
		panel.add(lblScheduleid);
		
		staffID = new JTextField();
		staffID.setColumns(10);
		staffID.setBounds(87, 71, 130, 26);
		panel.add(staffID);
		staffID.setEditable(false);
		
		JLabel lblTheatreid = new JLabel(" theatreID :");
		lblTheatreid.setBounds(6, 131, 87, 25);
		panel.add(lblTheatreid);
		
		theatreID = new JTextField();
		theatreID.setColumns(10);
		theatreID.setBounds(87, 130, 130, 26);
		panel.add(theatreID);
		theatreID.setEditable(false);
		
		JLabel lblDate = new JLabel("    Date :");
		lblDate.setBounds(6, 190, 87, 25);
		panel.add(lblDate);
		
		times = new JTextField();
		times.setColumns(10);
		times.setBounds(87, 189, 130, 26);
		panel.add(times);
		times.setEditable(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 245, 249, 32);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(0, 6, 85, 29);
		btnSearch.addActionListener(this);
		panel_1.add(btnSearch);
		
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(155, 6, 88, 29);
		btnUpdate.addActionListener(this);
		panel_1.add(btnUpdate);
		
		btnModify = new JButton("Modify");
		btnModify.setBounds(77, 6, 85, 29);
		btnModify.addActionListener(this);
		panel_1.add(btnModify);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void search(int scheduleid){
		
			OracleDbManager oc = new OracleDbManager();
			PreparedStatement ps ;
			Connection conn;
			int a = scheduleid;
			System.out.println(a);
			try{
			
			
			String sql = "select * from employment where employment.scheduleID = ?" ;
			
			
				conn = oc.getConnection();
		        ps = conn.prepareStatement(sql);
		        ps.setInt(1, a);
		        ResultSet rs = ps.executeQuery();
		        if(rs.next()){
		              String staff_id = rs.getString(1);
		              String theatre_id=rs.getString(2);
		              String tims = rs.getString(3);
		  			java.util.Date  date  =  new SimpleDateFormat("mm-dd-yy").parse(tims);     
					java.sql.Date  sqlDate  =  new java.sql.Date(date.getTime());  
		              String timesa = sqlDate.toString();
			       
			        	staffID.setText(staff_id);
			        	theatreID.setText(theatre_id);
			        	times.setText(timesa);
		        }else{
		        	
		        	JOptionPane.showMessageDialog(null,"The staff does not have working schedule！", "System Information", JOptionPane.ERROR_MESSAGE);
		        	scheduleID.setText("");
		        	staffID.setText("");
		        	theatreID.setText("");
		        	times.setText("");
		    	
		        }
		        

		
		    
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(NumberFormatException e1){
				JOptionPane.showMessageDialog(null,"Please input the number", "System Information", JOptionPane.ERROR_MESSAGE);
	        	scheduleID.setText("");
	        	staffID.setText("");
	        	theatreID.setText("");
	        	times.setText("");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public void update(int scheduleid){
		OracleDbManager oc = new OracleDbManager();
		PreparedStatement ps ;
	
		Connection conn;
		String sql = "update employment set staffid=? ,theatreid=? , time=to_date(?,'dd-mm-yy') where scheduleid=?";
		String a =scheduleID.getText();
		int aa = Integer.parseInt(a);

		
		String b = staffID.getText();
		String theatreID_s = theatreID.getText();
		String time_s = times.getText();
		
		try{
		

			int staffID_i = Integer.parseInt(b);
			int theatreID_i = Integer.parseInt(theatreID_s);
			java.util.Date  date  =  new SimpleDateFormat("YY-MM-DD").parse(time_s);     
			java.sql.Date  sqlDate  =  new java.sql.Date(date.getTime());  
			
			
			
			conn = oc.getConnection();
			ps = conn.prepareStatement(sql);
		    ps.setInt(1, staffID_i);
		    ps.setInt(2, theatreID_i);
		    ps.setDate(3, sqlDate);
		    ps.setInt(4, aa);
		 
		    ps.executeUpdate();
		    JOptionPane.showMessageDialog(null,"Update Successful!", "System Information", JOptionPane.ERROR_MESSAGE);
		} catch (SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null,"unique constraint violated", "System Information", JOptionPane.ERROR_MESSAGE);
			staffID.setText("");
			scheduleID.setText("");
			theatreID.setText("");
			times.setText("");
        	scheduleID.setEditable(true);
        	staffID.setEditable(false);
        	theatreID.setEditable(false);
        	times.setEditable(false);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NumberFormatException e1){
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnSearch){
			try{
			String nu = scheduleID.getText();
			
			int num = Integer.parseInt(nu);
			search(num);
			}catch(NumberFormatException e1){
				 JOptionPane.showMessageDialog(null,"Please input the right ID", "System Information", JOptionPane.ERROR_MESSAGE);
			}
        	scheduleID.setEditable(true);
        	staffID.setEditable(false);
        	theatreID.setEditable(false);
        	times.setEditable(false);
		}
		
		else if(e.getSource()==btnModify){
			System.out.println("s");
        	scheduleID.setEditable(false);
        	staffID.setEditable(true);
        	theatreID.setEditable(true);
        	times.setEditable(true);
		}
		
		else if(e.getSource()==btnUpdate){
			String nu = scheduleID.getText();
			int num = Integer.parseInt(nu);
			update(num);
		}
	}
}
