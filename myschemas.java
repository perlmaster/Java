//****************************************************************************
//
//   Filename: myschemas.java
//
//   Purpose : Java MYSQL program to list the schemas under a MYSQL server
//
// Copyright : Barry Kimelman (All rights reserved.)
//
//    Author : Barry Kimelman
//
//   Written : October 10, 2019
//
//****************************************************************************

import java.util.*;
import java.io.*;
import java.sql.*;

//*************************************************************************
//
//     Class   : myschemas
//
//     Purpose : Java MYSQL program to list the schemas under a MYSQL server
//
//       Notes : (none)
//
//*************************************************************************

public class myschemas
{
	public static void main (String args[])
	{
		Connection conn;
		Statement stmt;
		ResultSet results;
		int	count;
		String query = new String();
		String schema_name = new String();
		String mysql_username = "myusername";
		String mysql_user_password = "mypassword";
		String url = "jdbc:mysql://localhost/myschema_name?autoReconnect=true&useSSL=false";
		String db_error_message = new String();

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
			stmt = conn.createStatement();
			query = "SELECT schema_name FROM information_schema.schemata";
			// results = stmt.executeQuery("SELECT schema_name FROM information_schema.schemata");
			results = stmt.executeQuery(query);
			count = 0;
			while (results.next()) {
				count += 1;
				schema_name = results.getString(1);
				System.out.println(schema_name);
			} // WHILE displaying SELECT results
			System.out.println("\nFound " + count + " schemas\n");
		} catch (Exception e) {
			db_error_message = "Database error : " + e;
			System.out.println(db_error_message + "\n");
			System.exit(1);
		} // CATCH

	System.exit(0);
	} // end of main
} // end of myschemas
