package MovieComments;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JTextArea;

import java.awt.Color;

import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ButtonGroup;

public class Add{

	JFrame frame;
	private JTextField txtMoviename;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	Connection connection=null;
	int MovieID=2011001;
	int UserID=0;
	int CommentID=0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add window = new Add();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public Add() throws SQLException {
		initialize();
	}
	public Add(Connection conn,int movie,int user,int comment) throws SQLException
	{
		connection=conn;
		//store UserID and CommentID
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtMoviename = new JTextField();
		txtMoviename.setEditable(false);
		txtMoviename.setForeground(Color.BLACK);
		txtMoviename.setBackground(Color.LIGHT_GRAY);
		
		txtMoviename.setText(QueryInputAndOutput.getString(connection, "select name from movie where movieid="+MovieID, "NAME"));
		//txtMoviename.setText("movie name");
		txtMoviename.setBounds(112, 10, 312, 30);
		frame.getContentPane().add(txtMoviename);
		txtMoviename.setColumns(10);
		
		JEditorPane dtrpnConext = new JEditorPane();
		dtrpnConext.setText("Comments:");
		dtrpnConext.setBounds(10, 50, 106, 21);
		frame.getContentPane().add(dtrpnConext);
		
		JLabel lblMovieName = new JLabel("Movie Name");
		lblMovieName.setBounds(10, 10, 77, 30);
		frame.getContentPane().add(lblMovieName);
		
		
		
		final JTextArea txtrYourComments = new JTextArea();
		txtrYourComments.setBackground(Color.LIGHT_GRAY);
		txtrYourComments.setText("your comments");
		txtrYourComments.setBounds(10, 81, 414, 108);
		frame.getContentPane().add(txtrYourComments);
		
		final JRadioButton rdbtnNewRadioButton = new JRadioButton("like it");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(10, 201, 121, 23);
		frame.getContentPane().add(rdbtnNewRadioButton);
		
		final JRadioButton rdbtnSuckIt = new JRadioButton("don't like it");
		buttonGroup.add(rdbtnSuckIt);
		rdbtnSuckIt.setBounds(10, 229, 121, 23);
		frame.getContentPane().add(rdbtnSuckIt);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//get the comments.
				String movieName=txtMoviename.getText();
				String content=txtrYourComments.getText();
				boolean LikeOrNot;
				
				if (rdbtnNewRadioButton.isSelected())
				{
					LikeOrNot=true;//true stands for like
				}else LikeOrNot=false;//false stands for dislike
				System.out.println("movie="+movieName);
				System.out.println("movie content="+content);
				System.out.println("like="+LikeOrNot);
				//call function ** to sumbit
				try {
					Logic.SubmitComments(connection,Main.UserID,Main.MovieID,content,LikeOrNot,Main.CommentID);
					Main.content=content;
					if (LikeOrNot) Main.likes+=1; else Main.dislikes+=1;
					Main.refresh();
					frame.dispose();
					//JFrame.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//refresh Main window
			}
		});
		btnSubmit.setBounds(271, 229, 93, 23);
		frame.getContentPane().add(btnSubmit);
	}
}
