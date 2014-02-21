import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class Persistence { //created abstract class to hold database connection string
	
	protected static String url = "jdbc:odbc:Registration";	//changed reggie to registration
	protected static Connection connection;
	
	static { 
		try { 
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); 
		}
		catch (Exception ignored) {} 
	}
	
	public static Statement openConnectionAndCreateStatement() {
		try {
			connection = DriverManager.getConnection(url);
			return connection.createStatement();
		} catch (SQLException e) {
			return null;
		}
		
	}
}
