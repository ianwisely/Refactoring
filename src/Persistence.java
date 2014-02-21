
public abstract class Persistence {
	
	static String url = "jdbc:odbc:Registration";	//changed reggie to registration
	static { 
		try { 
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); 
		}
		catch (Exception ignored) {} 
	}
}
