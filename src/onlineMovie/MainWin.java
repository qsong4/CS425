package onlineMovie;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class MainWin extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JButton btnSearch;
	
	JPopupMenu popupMenu;
	
	public static int row = 20;
	public static String ar[][] = new String[row][5];
	public static final  Object columnName[] = {
	        "Movie Name", "Theatre Name","Director","Time", "Price"
	    };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWin frame = new MainWin();
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
	public MainWin() {
		setTitle("Online Ticket");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 654, 40);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to Online Ticket System");
		lblNewLabel.setBounds(6, 6, 223, 28);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(219, 7, 191, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Address", "Movie Name"}));
		comboBox.setBounds(422, 8, 111, 27);
		panel.add(comboBox);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(531, 7, 117, 29);
		panel.add(btnSearch);
		btnSearch.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 58, 535, 363);
		contentPane.add(scrollPane);
		
		table = new JTable(ar,columnName);
		scrollPane.setViewportView(table);
	
	
		table.addMouseListener(new MouseAdapter() {
	          public void mousePressed(MouseEvent e) {
	              if (e.isPopupTrigger()) {  
	      			
	    				System.out.println("dsa");
	    				 int row = e.getY() / table.getRowHeight();
	                     popupMenu.show(e.getComponent(), e.getX(), e.getY());
	    			
	              }
	          }
		});
		
		popupMenu = new JPopupMenu();
		JMenuItem purchase = new JMenuItem("Purchase");
        JMenuItem comment = new JMenuItem("Comment");
      
        popupMenu.add(purchase);
        popupMenu.add(comment);

	
	}
	
	public void addSearch(String address){
		OracleDbManager oc = new OracleDbManager();
		PreparedStatement ps ;
		Connection conn;
		try{
		
		
		String sql = " select x.moviename,x.theatrename,x.director,y.DATETIME,y.PRICE from(  "
                          +"select a.movieID,a.name moviename,b.name theatrename,a.director"
                          +"  from movie a, theatre b, play c"
                          +" where a.movieID = c.movieID"
                          +"   and c.theatreID = b.theatreID"
                          +"   and b.location like ?) x, schedule y, movieschedule z"
                       +" where y.scheduleID = z.scheduleID"
                       +"  and z.movieID = x.movieID ";
		
		
			conn = oc.getConnection();
	        ps = conn.prepareStatement(sql);
	        ps.setString(1,address);
	        System.out.println(sql);
	        ResultSet rs = ps.executeQuery();
	    

	        
	        	
	        
	        	
	    	    while(rs.next()){
	    	    	int j=0;
	    	    	System.out.println("ssss");
	    	    	for(int i=0;i<5;i++){
	    	    	ar[j][i] = rs.getString(i+1);
	    	    	}
	    	    	
	    	        j++;
	    	    
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
			String a = textField.getText();
            String address = "'%"+a+"%'";
			System.out.println(address);
			addSearch(address);
			
		}
	}


}
