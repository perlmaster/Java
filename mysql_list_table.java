//****************************************************************************
//
//   Filename: mysql_list_table.java
//
//   Purpose : Java MYSQL script to list the records in a MYSQL table
//
// Copyright : Barry Kimelman (All rights reserved.)
//
//    Author : Barry Kimelman
//
//   Written : October 11, 2019
//
//****************************************************************************

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;
import java.sql.*;

//*************************************************************************
//
//     Class   : mysql_list_table
//
//     Purpose : MYSQL test program
//
//       Notes : (none)
//
//*************************************************************************

public class mysql_list_table
{
	public static void main (String args[])
	{
		Connection conn;
		Statement stmt;
		ResultSet results;
		int	num_records , index , num_columns , len , colname_maxlen;
		String query = new String();
		String mysql_username = "myusername";
		String mysql_user_password = "mypassword";
		String url = "jdbc:mysql://localhost/myschema?autoReconnect=true&useSSL=false";
		String db_error_message = new String();
		String colname = new String();
		String buffer = new String();
		String table_name = new String();
		String column_type = new String();
		Vector colnames = new Vector();
		Vector column_types = new Vector();
		String column_type_pattern = new String();
		String column_value = new String();

		if (args.length == 0) {
			System.out.println("Usage : java mysql_list_table table_name\n");
			System.exit(1);
		}
		table_name = args[0];
		column_type_pattern = "char|enum|text";
        Pattern pattern = Pattern.compile(column_type_pattern, Pattern.CASE_INSENSITIVE);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			db_error_message = "Can't get MySQL Instance : " + e;
			System.out.println(db_error_message + "\n");
			System.exit(1);
		} // CATCH
		// System.out.println("Successfully loaded database driver\n");
		query = "select column_name COLNAME,ordinal_position ORD " +
				"from information_schema.columns where table_name = '" + table_name + "'";
		query = "select column_name COLNAME,ordinal_position ORD," +
				"is_nullable ISNULL,character_maximum_length MAXLEN," +
				"column_type,extra,column_key " +
				"from information_schema.columns where table_name = '" + table_name + "'";

		colname_maxlen = 0;
		try {
			conn = DriverManager.getConnection(url,mysql_username,mysql_user_password);
			stmt = conn.createStatement();
			results = stmt.executeQuery(query);
			num_columns = 0;
			while (results.next()) {
				num_columns += 1;
				colname = results.getString(1);
				colnames.addElement(colname);
				len = colname.length();
				if ( len > colname_maxlen ) {
					colname_maxlen = len;
				}
				column_type = results.getString(5);
				column_types.addElement(column_type);
			} // WHILE displaying SELECT results
		} catch (Exception e) {
			db_error_message = "Database error : " + e;
			System.out.println(db_error_message + "\n");
			System.exit(1);
		} // CATCH

		try {
			conn = DriverManager.getConnection(url,mysql_username,mysql_user_password);
			stmt = conn.createStatement();
			results = stmt.executeQuery("SELECT * FROM " + table_name);
			num_records = 0;
			while (results.next()) {
				num_records += 1;
				System.out.println("\nRecord " + num_records);
				for ( index = 0 ; index < colnames.size() ; ++index ) {
					colname = (String) colnames.get(index);
					column_type = (String) column_types.get(index);
					buffer = rightpad(colname,colname_maxlen);
					// buffer += " (" + column_type + ")";
					Matcher matcher = pattern.matcher(column_type);
					column_value = results.getString(index+1);
					if  ( matcher.find() ) { // check for a match
						System.out.println(buffer + " = '" + column_value + "'");
					}
					else {
						System.out.println(buffer + " = " + column_value);
					}
				}
			} // WHILE displaying SELECT results
			System.out.println("\n" + num_records + " record(s) read from table " + table_name);
		} catch (Exception e) {
			db_error_message = "Database error : " + e;
			System.out.println(db_error_message + "\n");
			System.exit(1);
		} // CATCH

	System.exit(0);
	} // end of main
public static String rightpad(String text, int length) {
    return String.format("%-" + length + "." + length + "s", text);
}
} // end of mysql_list_table
