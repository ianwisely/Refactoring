import java.sql.ResultSet;
import java.sql.Statement;


public class OfferingPersistence extends Persistence {	//create this class to separate model classes from persistence classes

	public static Offering create(Course course, String daysTimesCsv) throws Exception {
		try {
			Statement statement = openConnectionAndCreateStatement();
			ResultSet result = statement.executeQuery("SELECT MAX(ID) FROM offering;");
			result.next();
			int newId = 1 + result.getInt(1);
			statement.executeUpdate("INSERT INTO offering VALUES ('"+ newId + "','" + course.getName() + "','" + daysTimesCsv + "');");
			return new Offering(newId, course, daysTimesCsv);
		} 
		finally {
			try { 
				connection.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static Offering find(int id) {
		try {
			Statement statement = openConnectionAndCreateStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE ID =" + id + ";");
			if (result.next() == false)
				return null;
			Course course = CoursePersistence.find(result.getString("Course"));	//changed Course to CoursePersistance and took out variable courseName
			String dateTime = result.getString("DateTime");
			connection.close();
			return new Offering(id, course, dateTime);
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

	public static void update(Offering offering) throws Exception {	//changed to static and added Offering parameter
		try {
			Statement statement = openConnectionAndCreateStatement();
			statement.executeUpdate("DELETE FROM Offering WHERE ID=" + offering.getId() + ";");
			statement.executeUpdate("INSERT INTO Offering VALUES('" + offering.getId() + "','" + offering.getCourse().getName() + "','" + offering.getDaysTimes() + "');");
		} 
		finally {
			try { 
				connection.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

}
