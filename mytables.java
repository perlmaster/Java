//****************************************************************************
//
//   Filename: mytables.java
//
//   Purpose : List the tables in a MYSQL database
//
// Copyright : Barry Kimelman (All rights reserved.)
//
//    Author : Barry Kimelman
//
//   Written : May 8, 2019
//
//****************************************************************************

import java.util.*;
import java.io.*;
import java.sql.*;

//*************************************************************************
//
//     Class   : mytables
//
//   Purpose : List the tables in a MYSQL database
//
//       Notes : (none)
//
//*************************************************************************

public class mytables
{
	public static void main (String args[])
	{
		String[] headers = {"Table Type", "Table Name", "Num Rows", "Row Format" , "Create Time" };
		Connection conn;
		Statement stmt;
		ResultSet results;
		int	num_tables;
		int len;
		int index;
		String buffer = new String();
		String line = new String();
		String query = new String();
		String schema = new String();
		String table_name = new String(); Vector table_names = new Vector(); int table_name_maxlen;
		String table_type = new String(); Vector table_types = new Vector(); int table_type_maxlen;
		String row_format = new String(); Vector row_formats = new Vector();
		String num_tab_rows = new String(); Vector num_tab_rowss = new Vector(); int num_tab_rows_maxlen;
		String create_time = new String(); Vector create_times = new Vector(); int create_time_maxlen;

		String mysql_username = "my_user";
		String mysql_user_password = "my_pass";
		String url = "jdbc:mysql://localhost/my_database?autoReconnect=true&useSSL=false";
		String db_error_message = new String();
		if (args.length == 0) {
			schema = "qwlc";
		}
		else {
			schema = args[0];
		}

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			db_error_message = "Can't get MySQL Instance : " + e;
			System.out.println(db_error_message + "\n");
			System.exit(1);
		} // CATCH

		try {
			conn = DriverManager.getConnection(url,mysql_username,mysql_user_password);
			stmt = conn.createStatement();
			query = "select table_schema,table_name,table_type,row_format,table_rows," +
					"create_time, " +
					"ifnull(update_time,'++') U_Time, ifnull(check_time,'++') C_Time " +
					"from information_schema.tables where table_schema = '" +
					schema + "'";
			// Note : The update_time and check_time fields always seem to be null
			System.out.println("query is\n" + query + "\n");
			results = stmt.executeQuery(query);
			num_tables = 0;
			table_type_maxlen = headers[0].length();
			table_name_maxlen = headers[1].length();
			num_tab_rows_maxlen = headers[2].length();
			create_time_maxlen = headers[3].length();
			create_time_maxlen = headers[4].length();
			while (results.next()) {
				num_tables += 1;
				table_name = results.getString(2);
				table_names.addElement(table_name);
				len = table_name.length();
				if ( len > table_name_maxlen ) {
					table_name_maxlen = len;
				}

				table_type = results.getString(3);
				table_types.addElement(table_type);
				len = table_type.length();
				if ( len > table_type_maxlen ) {
					table_type_maxlen = len;
				}

				row_format = results.getString(4);
				row_formats.addElement(row_format);

				num_tab_rows = results.getString(5);
				num_tab_rowss.addElement(num_tab_rows);
				len = num_tab_rows.length();
				if ( len > num_tab_rows_maxlen ) {
					num_tab_rows_maxlen = len;
				}

				create_time = results.getString(6);
				create_times.addElement(create_time);
				len = create_time.length();
				if ( len > create_time_maxlen ) {
					create_time_maxlen = len;
				}

			} // WHILE displaying SELECT results

			line = rightpad("Table Type",table_type_maxlen);
			line += " " + rightpad("Table Name",table_name_maxlen);
			line += " " + rightpad("Num Rows",num_tab_rows_maxlen);
			line += " " + "Row Format";
			line += " " + rightpad("Create Time",create_time_maxlen);
			System.out.println(line);

			line = rightpad("==========",table_type_maxlen);
			line += " " + rightpad("===========",table_name_maxlen);
			line += " " + rightpad("=========",num_tab_rows_maxlen);
			line += " " + "==========";
			line += " " + rightpad("===========",create_time_maxlen);
			System.out.println(line);

			for ( index = 0 ; index < num_tables ; ++index ) {
				table_type = (String) table_types.get(index);
				table_name = (String) table_names.get(index);
				num_tab_rows = (String) num_tab_rowss.get(index);
				row_format = (String) row_formats.get(index);
				// maxlen = (String) maxlens.get(index);
				create_time = (String) create_times.get(index);

				line = rightpad(table_type,table_type_maxlen);
				line += " " + rightpad(table_name,table_name_maxlen);
				line += " " + rightpad(num_tab_rows,num_tab_rows_maxlen);
				line += " " + rightpad(row_format,10);
				line += " " + rightpad(create_time,create_time_maxlen);
				System.out.println(line);
			}
		} catch (Exception e) {
			db_error_message = "Database/processing error : " + e;
			System.out.println(db_error_message + "\n");
			System.exit(1);
		} // CATCH

	System.exit(0);
	} // end of main

public static String rightpad(String text, int length) {
    return String.format("%-" + length + "." + length + "s", text);
}

} // end of mytables
