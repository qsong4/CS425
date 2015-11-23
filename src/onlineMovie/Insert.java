package onlineMovie;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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

@SuppressWarnings("serial")
public class Insert extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField scheduleID;
	private JTextField staffID;
	private JTextField theatreID;
	private JTextField times;
	
	JButton btnInsert,btnReset;



	/**
	 * Create the frame.
	 */
	public Insert() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 434, 193);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 421, 97);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ScheduleID :");
		lblNewLabel.setBounds(6, 6, 80, 22);
		panel.add(lblNewLabel);
		
		scheduleID = new JTextField();
		scheduleID.setBounds(98, 4, 123, 26);
		panel.add(scheduleID);
		scheduleID.setColumns(10);
		
		JLabel lblStaffid = new JLabel("StaffID :");
		lblStaffid.setBounds(221, 6, 80, 22);
		panel.add(lblStaffid);
		
		staffID = new JTextField();
		staffID.setColumns(10);
		staffID.setBounds(292, 4, 123, 26);
		panel.add(staffID);
		
		JLabel lblTheatreid = new JLabel("TheatreID :");
		lblTheatreid.setBounds(6, 52, 80, 22);
		panel.add(lblTheatreid);
		
		theatreID = new JTextField();
		theatreID.setColumns(10);
		theatreID.setBounds(82, 50, 123, 26);
		panel.add(theatreID);
		
		JLabel lblDatemmddyy = new JLabel("Date(mm-dd-yy) :");
		lblDatemmddyy.setBounds(207, 52, 129, 22);
		panel.add(lblDatemmddyy);
		
		times = new JTextField();
		times.setColumns(10);
		times.setBounds(310, 50, 111, 26);
		panel.add(times);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 115, 421, 41);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnInsert = new JButton("Insert");
		btnInsert.setBounds(63, 6, 117, 29);
		btnInsert.addActionListener(this);
		panel_1.add(btnInsert);
		
		btnReset = new JButton("Reset");
		btnReset.setBounds(230, 6, 117, 29);
		btnReset.addActionListener(this);
		panel_1.add(btnReset);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@SuppressWarnings("static-access")
	public void insertStaddSchedule()  {
		
		OracleDbManager oc = new OracleDbManager();
		PreparedStatement ps ;
	
		Connection conn;
		String sql = "insert into EMPLOYMENT values(?,?,?,?)";
		
		String a = scheduleID.getText();
		
		String b = staffID.getText();
		   System.out.println(b);
		String theatreID_s = theatreID.getText();
		String time_s = times.getText();
		
		try{
		
			int scheduleID_i = Integer.parseInt(a);
			int staffID_i = Integer.parseInt(b);
			int theatreID_i = Integer.parseInt(theatreID_s);
			SimpleDateFormat formant = new SimpleDateFormat("mm-dd-yy");
			java.util.Date  date  = formant.parse(time_s);     
			java.sql.Date  sqlDate  =  new java.sql.Date(date.getTime());  
			
			
			
			conn = oc.getConnection();
			ps = conn.prepareStatement(sql);
		    ps.setInt(4, scheduleID_i);
		    ps.setInt(1, staffID_i);
		    ps.setInt(2, theatreID_i);
		    ps.setDate(3, sqlDate);
		 
		    int a1 = ps.executeUpdate();
			if(a1==1){
				JOptionPane.showMessageDialog(null,"insert successful!", "System Information", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null,"unique constraint violated", "System Information", JOptionPane.ERROR_MESSAGE);
			staffID.setText("");
			scheduleID.setText("");
			theatreID.setText("");
			times.setText("");
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NumberFormatException e1){
			e1.printStackTrace();
		}catch(ParseException e2){
			e2.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnInsert){
		
		
				insertStaddSchedule();
			
		}
		
		else if(e.getSource()==btnReset){
			staffID.setText("");
			scheduleID.setText("");
			theatreID.setText("");
			times.setText("");
			
		}
	}
}
