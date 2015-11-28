package onlineMovie;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

public class MainWin extends JFrame implements ActionListener, ItemListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JButton btnSearch;
	private JComboBox comboBox;
	private JMenuItem purchase,comment;
	public int ID;
	
	
	JPopupMenu popupMenu;
	
	public static String select[] = {"Address","Movie Name"};
	public static int row = 20;
	public static String ar[][] = new String[row][7];
	public static final  Object columnName[] = {
	        "Movie Name", "Theatre Name","Director","Time", "Price","Location","ID"
	    };

	public String type = "Address";


	/**
	 * Create the frame.
	 */
	public MainWin(int iD) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ID = iD;
		System.out.println(ID);
		
		setTitle("Online Ticket");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 758, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 746, 40);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome  to Online Ticket System");
		lblNewLabel.setBounds(37, 6, 223, 28);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(299, 7, 191, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(select));
		comboBox.setBounds(502, 8, 124, 27);
		comboBox.addItemListener(this);
		panel.add(comboBox);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(623, 7, 117, 29);
		panel.add(btnSearch);
		btnSearch.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 58, 611, 363);
		contentPane.add(scrollPane);
		
		table = new JTable(ar,columnName);
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(629, 58, 123, 363);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
	
	
		table.addMouseListener(new MouseAdapter() {
	          public void mousePressed(MouseEvent e) {
	              if (e.isPopupTrigger()) {  
	      		
	    				 int row = e.getY() / table.getRowHeight();
	                     popupMenu.show(e.getComponent(), e.getX(), e.getY());
	    			
	              }
	          }
		});
		
		popupMenu = new JPopupMenu();
		purchase = new JMenuItem("Purchase");
        comment = new JMenuItem("Comment");
        purchase.addActionListener(this);
        comment.addActionListener(this);
        popupMenu.add(purchase);
        popupMenu.add(comment);

	
	}


	public void search(String parameter){
		OracleDbManager oc = new OracleDbManager();
		PreparedStatement ps ;
		Connection conn;
		String sql2 = " select x.moviename,x.theatrename,x.director,y.DATETIME,y.PRICE,x.location,y.scheduleid from(  "
                +"select a.movieID,a.name moviename,b.name theatrename,a.director,b.location location"
                +"  from movie a, theatre b, play c"
                +" where a.movieID = c.movieID"
                +"   and c.theatreID = b.theatreID"
                +"   and b.location like '%?%') x, schedule y, movieschedule z"
             +" where y.scheduleID = z.scheduleID"
             +"  and z.movieID = x.movieID ";
		
	    String sql3 = "select x.moviename,x.theatre,x.director,y.DATETIME,y.PRICE,x.location,y.scheduleid from ("
				      +"select a.name moviename,c.name theatre,a.director,c.location,a.movieID"
				       +" from movie a, play b, theatre c"
				       +" where a.movieID = b.movieID"
				        +" and c.theatreID = b.theatreID"
				         +" and a.name like '%?%') x, schedule y,movieschedule z"
				 +" where x.movieid = z.movieid"
				  +" and z.scheduleid = y.scheduleid" ;
		
		try{
		
		if(type.equals("Address")){
			boolean f1 = true;
		String sql =  sql2.replace("?", parameter);
		
			conn = oc.getConnection();
	        ps = conn.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        int j=0;
	    	    while(rs.next()){
	    	    	f1 = false;
	    	    	for(int i=0;i<7;i++){
	    	    	ar[j][i] = rs.getString(i+1);
	    	    	}
	    	    	
	    	        j++;
	    	    
	    	        }
	    	    
	    	    if(f1){
	    	    	JOptionPane.showMessageDialog(null,"Can not Find the movie", "System Information", JOptionPane.ERROR_MESSAGE);
	    			textField.setText("");
	    	    }
	    	    
		}
		
		else if(type.equals("Movie Name")){
            boolean f2 = true;
			String sql4 =  sql3.replace("?", parameter);
			conn = oc.getConnection();
			
	        ps = conn.prepareStatement(sql4);
	        ResultSet rs = ps.executeQuery();
	        int j=0;
	    	    while(rs.next()){
	    	    	f2 = false;
	    	    	for(int i=0;i<7;i++){
	    	    	ar[j][i] = rs.getString(i+1);
	    	    	}
	    	    	
	    	        j++;
	    	    
	    	        }
	    	    
	    	    if(f2){
	    	    	JOptionPane.showMessageDialog(null,"Can not Find the movie", "System Information", JOptionPane.ERROR_MESSAGE);
	    			textField.setText("");
	    	    }
		}
	        

	    
	    table.repaint();
	    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnSearch){
			String parameter = textField.getText();
	
			search(parameter);
			
		}
		
		else if(e.getSource()==purchase){
			
			int row = table.getSelectedRow();
			String movieName = (String)table.getValueAt(row, 0);
			String theatreName = (String)table.getValueAt(row, 1);
			String time = (String)table.getValueAt(row, 3);
			String price = (String)table.getValueAt(row, 4);
			String scheduleid = (String)table.getValueAt(row, 6);
			String id = String.valueOf(ID);
            String items[] ={movieName,theatreName,time,price,scheduleid,id};
            
			purchase p = new purchase(items);
			p.setVisible(true);
			
			
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==comboBox){
			if(e.getStateChange()==ItemEvent.SELECTED){
				type=(String)comboBox.getSelectedItem();
			}
		}
	}
}
