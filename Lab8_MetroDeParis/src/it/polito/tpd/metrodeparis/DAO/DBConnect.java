package it.polito.tpd.metrodeparis.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private static String jdbcURL="jdbc:mysql://localhost/metroparis?user=root";
	private static Connection conn;
	

	public static Connection getConnection(){
		if(conn==null){
		try {
			if(conn==null)
			conn = DriverManager.getConnection(jdbcURL);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Cannot get connection " + jdbcURL, e);
		}
		}
		else{
		return conn;
		}
	}


	public static Connection releaseConnection()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
