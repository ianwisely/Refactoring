import java.sql.ResultSet;
import java.sql.Statement;


public class CoursePersistence extends Persistence {	//create this class to separate model classes from persistence classes

	public static Course create(String name, int credits) throws Exception {
		try {
			Statement statement = openConnectionAndCreateStatement();
			statement.executeUpdate("DELETE FROM course WHERE name = '" + name + "';");
			statement.executeUpdate("INSERT INTO course VALUES ('" + name + "', '" + credits + "');");
			return new Course(name, credits);
		} 
		finally {
			try { 
				connection.close(); 
			} 
			catch (Exception ignored) {}
		}
	}

	public static Course find(String name) {
		try {
			Statement statement = openConnectionAndCreateStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM course WHERE Name = '" + name + "';");
			if (!result.next())
				return null;
			return new Course(name, result.getInt("Credits"));
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

	public static void update(Course course) throws Exception {
		try {
			Statement statement = openConnectionAndCreateStatement();
			statement.executeUpdate("DELETE FROM COURSE WHERE name = '" + course.getName() + "';");
			statement.executeUpdate("INSERT INTO course VALUES('" + course.getName() + "','" + course.getCredits() + "');");
		} 
		finally {
			try { 
				connection.close(); 
			} 
			catch (Exception ignored) {}
		}
	}
}
