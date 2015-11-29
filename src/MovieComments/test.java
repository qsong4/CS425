package MovieComments;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import onlineMovie.OracleDbManager;


public class test {

	public static void main(String[] args) throws SQLException {
		Connection connection=null;
		OracleDbManager odm=new OracleDbManager();
		connection=odm.getConnection();
		int userID,commentID,CreditPoints;
		Scanner sc = new Scanner(System.in);

		int MovieID=2011001;
		int UserID=20358445;
		String content="i like this movie very much";
		boolean LikeOrNot=true;
		int CommentsID=1;
		
		Logic.SubmitComments(connection,UserID,MovieID,content,LikeOrNot,CommentsID);
		//UserManagement.SetCreditPoints(connection, userID,commentID,CreditPoints);
	}

}

