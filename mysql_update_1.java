import java.sql.*;

public class mysql_update_1
{
  public static void main(String[] args)
  {
		String mysql_username = "myusername";
		String mysql_user_password = "mypassword";
		String url = "jdbc:mysql://localhost/mydatabase?autoReconnect=true&useSSL=false";
		String db_error_message = new String();
		Connection conn;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			db_error_message = "Can't get MySQL Instance : " + e;
			System.out.println(db_error_message + "\n");
			System.exit(1);
		} // CATCH
		System.out.println("Successfully loaded database driver\n");

		try {
			conn = DriverManager.getConnection(url,mysql_username,mysql_user_password);
	  String query = "update authors set name = ? where id = ?";
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setString   (1, "Jackson London");
      preparedStmt.setInt      (2, 1);

      preparedStmt.executeUpdate(); // execute the java preparedstatement
      conn.close();

		} catch (Exception e) {
			db_error_message = "Database error : " + e;
			System.out.println(db_error_message + "\n");
			System.exit(1);
		} // CATCH
  } // end of main
} // end of mysql_update_1
