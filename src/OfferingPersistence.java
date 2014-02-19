import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class OfferingPersistence {	//create this class to separate model classes from persistence classes
	
	static String url = "jdbc:odbc:Registration";	//changed reggie to registration
	static { 
		try { 
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); 
			}
		catch (Exception ignored) {} 
	}

	public static Offering create(Course course, String daysTimesCsv) throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM offering;");
			result.next();
			int newId = 1 + result.getInt(1);
			statement.executeUpdate("INSERT INTO offering VALUES ('"+ newId + "','" + course.getName() + "','" + daysTimesCsv + "');");
			return new Offering(newId, course, daysTimesCsv);
		} 
		finally {
			try { 
				conn.close(); 
				} 
			catch (Exception ignored) {}
		}
	}

	public static Offering find(int id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE ID =" + id + ";");
			if (result.next() == false)
				return null;
			Course course = CoursePersistence.find(result.getString("Course"));	//changed Course to CoursePersistance and took out variable courseName
			String dateTime = result.getString("DateTime");
			conn.close();
			return new Offering(id, course, dateTime);
		} 
		catch (Exception ex) {
			try { 
				conn.close(); 
			} catch (Exception ignored) {}
			return null;
		}
	}
	
	public static void update(Offering offering) throws Exception {	//changed to static and added Offering parameter
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + offering.getId() + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + offering.getId() + "','" + offering.getCourse().getName() + "','" + offering.getDaysTimes() + "');");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

}
