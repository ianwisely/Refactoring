import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class CoursePersistence {	//create this class to separate model classes from persistence classes

	static String url = "jdbc:odbc:Registration";
	static { 
		try { 
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); 
		}
		catch (Exception ignored) {} 
	}

	public static Course create(String name, int credits) throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM course WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES ('" + name + "', '" + credits + "');");
			return new Course(name, credits);
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static Course find(String name) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE Name = '" + name + "';");
			if (!result.next()) return null;
			int credits = result.getInt("Credits");
			return new Course(name, credits);
		} 
		catch (Exception ex) {
			return null;
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static void update(Course course) throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + course.getName() + "';");
			statement.executeUpdate("INSERT INTO course VALUES('" + course.getName() + "','" + course.getCredits() + "');");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
}
