import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SchedulePersistence extends Persistence {	//create this class to separate model classes from persistence classes

	public static void deleteAll() throws Exception {
		try {
			Statement statement = openConnectionAndCreateStatement();
			statement.executeUpdate("DELETE * FROM schedule;");
		} 
		finally {
			try { 
				connection.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
	
	public static Schedule create(String name) throws Exception {
		try {
			Statement statement = openConnectionAndCreateStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
			return new Schedule(name);
		} 
		finally {
			try { 
				connection.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
	
	public static Schedule find(String name) {
		try {
			Statement statement = openConnectionAndCreateStatement();
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
				connection.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static List<Schedule> all() throws Exception {
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		try {
			Statement statement = openConnectionAndCreateStatement();
			ResultSet results = statement.executeQuery("SELECT DISTINCT Name FROM schedule;");
			while (results.next())
			result.add(SchedulePersistence.find(results.getString("Name")));	//changed Schedule to SchedulePersistance
		} 
		finally {
			try { 
				connection.close(); 
			} 
			catch (Exception ignored) {}
		}
		return result;
	}

	public static void update(Schedule schedule) throws Exception {	//changed to static method and added Schedule parameter
		try {
			Statement statement = openConnectionAndCreateStatement();
			statement.executeUpdate("DELETE FROM schedule WHERE name = '" + schedule.getName() + "';");
			for (int i = 0; i < schedule.offerings.size(); i++) {
				Offering offering = schedule.offerings.get(i);
				statement.executeUpdate("INSERT INTO schedule VALUES('" + schedule.getName() + "','" + offering.getId() + "');");
			}
		} 
		finally {
			try { 
				connection.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	

}
