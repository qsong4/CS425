package MovieComments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class QueryInputAndOutput {
	public static  int getInt(Connection connection,String query,String attribute_name) throws SQLException
	{
		int a = 0;
		PreparedStatement ps=connection.prepareStatement(query);
		ResultSet rs=ps.executeQuery();
		while (rs.next())
		{
			a=rs.getInt(attribute_name);
		}
		return a; 
	}
	
	public static  String getString(Connection connection,String query,String attribute_name) throws SQLException
	{
		String a ="";
		PreparedStatement ps=connection.prepareStatement(query);
		ResultSet rs=ps.executeQuery();
		while (rs.next())
		{
			a=rs.getString(attribute_name);
		}
		return a; 
	}
	
	public static  void Set(Connection connection,String query) throws SQLException
	{
		PreparedStatement ps=connection.prepareStatement(query);
		ResultSet rs=ps.executeQuery();
	}
}
