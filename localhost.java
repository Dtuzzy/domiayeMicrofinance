import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class localhost {
	
	Statement stmt = null;
	private static Connection conn;
	public static Connection dbConnector() {
		
		try {
			// call JDBC connection class
			Class.forName("com.mysql.jdbc.Driver");
			// instantiate connection variable and use deriver manager
			 conn = DriverManager.getConnection("jdbc:mysql://www.ngg.company:3306/costumerdata", "mimix",
					"Jonbellion2017");
			System.out.println("Opened Database successfully!!");

			
		} catch (Exception ex) {
			// error handlers
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
		}
		return conn;

	}
	// surround connection with try/catch to handle exceptions
			
		//public static void main(String[] args) {
		//dbConnector();
		//localhost rs  = new localhost();
		//rs.insertTable();
//}

}
