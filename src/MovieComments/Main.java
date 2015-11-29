package MovieComments;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;

import onlineMovie.OracleDbManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {

	private static JFrame frame;
	private static JLabel lblNewLabel;
	private static JLabel lblDislikes;
	static Connection connection=null;
	static int MovieID=0;
	static int UserID=0;
	static int CommentID=0;
	static int likes=0;
	static int dislikes=0;
	static String content="";
	static String userName="";
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//
//					Main window = new Main(c,2011001,20358445);
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public Main() throws SQLException {
		initialize();
	}
	public Main(Connection conn,int movie,int user) throws SQLException
	{
		connection=conn;
		MovieID=movie;
		UserID=user;
		initialize();
		System.out.println("1");
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private static void initialize() throws SQLException {
		System.out.println("2");
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//list component
		String [] component=new String [100];
		int CommentNumber=QueryInputAndOutput.getInt(connection, "select count(*) from moviecomments", "count(*)");
		for (int i=0;i<CommentNumber;i++)
		{
			int userid=QueryInputAndOutput.getInt(connection, "select userid from moviecomments where commentid="+i, "userid");
			String name=QueryInputAndOutput.getString(connection, "select name from USERS where userid="+userid, "name");
			String content=QueryInputAndOutput.getString(connection, "select content from moviecomments where commentid="+i, "content");;
			component[i]=name+":"+content;
		}
		//component[0]="i love it!";
		//component[1]="i love it too!";
		//for (int i=2;i<30;i++)
			//component[i]="i love it "+i;
		JList list = new JList(component);
		CommentID=QueryInputAndOutput.getInt(connection, "select count(*) from moviecomments", "count(*)")+1;
		JScrollPane jScrollPane = new JScrollPane(list);
		
		jScrollPane.setBounds(0,32,434,193);
		frame.getContentPane().add(jScrollPane);

		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnAddComments = new JButton("Add Comments");
		btnAddComments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//once clicked refresh jlist,refresh likes and dislikes,update data to database
				//open Add
				Add ad = null;
				try {
					ad = new Add(connection,MovieID,UserID,CommentID);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ad.frame.setVisible(true);
				//need refresh
			}
		});
		btnAddComments.setBounds(281, 235, 130, 23);
		frame.getContentPane().add(btnAddComments);
		
		JLabel lblMovie = new JLabel(" Movie:");
		lblMovie.setBounds(0, 0, 248, 32);
		frame.getContentPane().add(lblMovie);
		
		likes=QueryInputAndOutput.getInt(connection, "select sum(vote) from moviecomments where movieid="+MovieID, "SUM(VOTE)");
		lblNewLabel = new JLabel("Likes:"+likes);
		lblNewLabel.setBounds(250, 0, 87, 32);
		frame.getContentPane().add(lblNewLabel);
		
		dislikes=QueryInputAndOutput.getInt(connection, "select count(*) from moviecomments where movieid="+MovieID+" and vote=0", "COUNT(*)");		
		lblDislikes = new JLabel("DisLikes:"+dislikes);
		lblDislikes.setBounds(337, 0, 97, 32);
		frame.getContentPane().add(lblDislikes);
		frame.setVisible(true);
		
	}
	public static void refresh() throws SQLException
	{
		initialize();
	}
}
