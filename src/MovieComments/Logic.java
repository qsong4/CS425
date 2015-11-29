package MovieComments;

import java.sql.Connection;

import java.sql.SQLException;

public class Logic {
	public static void SubmitComments(Connection connection,int MovieID,int UserID,String content,boolean LikeOrNot,int CommentID) throws SQLException
	{
		java.util.Date d=new java.util.Date ();
		java.sql.Date date=new java.sql.Date(111,1,1);
		date=new java.sql.Date(date.getTime());
		
		//java.util.Date now=new java.util.Date();
		//get votes before this submit.
		int vote=QueryInputAndOutput.getInt(connection, "select vote from  moviecomments where movieid="+MovieID, "vote");
		if (LikeOrNot==true)
			vote+=1;
		int credit=0;
		//insert a new comment.
		String query="insert into moviecomments values ("+UserID+","+MovieID+",'"+content+"',to_date('"+date+"','yyyy-mm-dd'),"+vote+","+credit+","+CommentID+")";
		System.out.println(query);
		QueryInputAndOutput.Set(connection, query);
	}
	
}
