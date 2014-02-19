import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class SchedulePersistence {	//create this class to separate model classes from persistence classes
	
	
	
	static String url = "jdbc:odbc:Registration";	//changed reggie to registration
	
	static { 
		try { 
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); 
		}
		catch (Exception ignored) {} 
	}

	public static void deleteAll() throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE * FROM schedule;");
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
	
	public static Schedule create(String name) throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
			return new Schedule(name);
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
	
	public static Schedule find(String name) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE Name= '" + name + "';");
			Schedule schedule = new Schedule(name);
			while (result.next()) {
				Offering offering = OfferingPersistence.find(result.getInt("OfferingId"));	//changed Offering to OfferingPersistance and removed variable offeringId
				schedule.offerings.add(offering);
			}
			return schedule;
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

	public static List<Schedule> all() throws Exception {
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery("SELECT DISTINCT Name FROM schedule;");
			while (results.next())
			result.add(SchedulePersistence.find(results.getString("Name")));	//changed Schedule to SchedulePersistance
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
		return result;
	}

	public static void update(Schedule schedule) throws Exception {	//changed to static method and added Schedule parameter
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);	//take out unnecessary parameters
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + schedule.getName() + "';");
			for (int i = 0; i < schedule.offerings.size(); i++) {
				Offering offering = (Offering) schedule.offerings.get(i);
				statement.executeUpdate("INSERT INTO schedule VALUES('" + schedule.getName() + "','" + offering.getId() + "');");
			}
		} 
		finally {
			try { 
				conn.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	

}
