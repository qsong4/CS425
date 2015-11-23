package onlineMovie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         OracleDbManager oc = new OracleDbManager();
         try {
			Connection conn = oc.getConnection();
		    Statement ps = conn.createStatement();
		    ResultSet rs = ps.executeQuery("select * from theatre");
		    while(rs.next()){
		    	System.out.println(rs.getString(1));
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
