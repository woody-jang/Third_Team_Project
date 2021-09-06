package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

	public static Connection getConnection() throws SQLException {
		try {
			String URL = "jdbc:mysql://127.0.0.1:3306/greenchat";
			String ID = "root";
			String PW = "root";
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, ID, PW);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
