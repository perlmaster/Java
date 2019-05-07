//****************************************************************************
//
//   Filename: mydesc2.java
//
//   Purpose : Describe the structure of a MYSQL database table
//
// Copyright : Barry Kimelman (All rights reserved.)
//
//    Author : Barry Kimelman
//
//   Written : May 6, 2019
//
//****************************************************************************

import java.util.*;
import java.io.*;
import java.sql.*;

//*************************************************************************
//
//     Class   : mydesc2
//
//     Purpose : MYSQL test program
//
//       Notes : (none)
//
//*************************************************************************

public class mydesc2
{
	public static void main (String args[])
	{
		String[] headers = {"Ordinal", "Column Name", "Data Type", "Maxlen" , "Nullable ?" , "Key ?" , "Extra" , "Comment" };
		Connection conn;
		Statement stmt;
		ResultSet results;
		int	num_columns;
		int len;
		int index;
		String buffer = new String();
		String line = new String();
		String query = new String();
		String table = new String();
		String colname = new String(); Vector colnames = new Vector(); int colname_maxlen;
		String ord = new String(); Vector ordinals = new Vector();
		String isnull = new String(); Vector isnulls = new Vector();
		String maxlen = new String(); Vector maxlens = new Vector();
		String column_type = new String(); Vector column_types = new Vector(); int column_type_maxlen;
		String extra = new String(); Vector extras = new Vector(); int extra_maxlen;
		String column_key = new String(); Vector column_keys = new Vector(); int key_maxlen;

		String mysql_username = "my_name";
		String mysql_user_password = "my_pass";
		String url = "jdbc:mysql://localhost/my_database?autoReconnect=true&useSSL=false";
		String db_error_message = new String();
		if (args.length == 0) {
			table = "hockey_divisions";
		}
		else {
			table = args[0];
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
			query = "select column_name COLNAME,ordinal_position ORD," +
					"is_nullable ISNULL,character_maximum_length MAXLEN," +
					"column_type,extra,column_key " +
					"from information_schema.columns where table_name = '" + table + "'";
			results = stmt.executeQuery(query);
			num_columns = 0;
			colname_maxlen = headers[1].length();
			column_type_maxlen = headers[2].length();
			extra_maxlen = headers[3].length();
			key_maxlen = headers[5].length();
			extra_maxlen = headers[6].length();
			while (results.next()) {
				num_columns += 1;
				colname = results.getString(1);
				colnames.addElement(colname);
				len = colname.length();
				if ( len > colname_maxlen ) {
					colname_maxlen = len;
				}

				ord = results.getString(2);
				ordinals.addElement(ord);
				isnull = results.getString(3);
				isnulls.addElement(isnull);
				maxlen = results.getString(4);
				maxlens.addElement(maxlen);

				column_type = results.getString(5);
				column_types.addElement(column_type);
				len = column_type.length();
				if ( len > column_type_maxlen ) {
					column_type_maxlen = len;
				}

				extra = results.getString(6);
				extras.addElement(extra);
				len = extra.length();
				if ( len > extra_maxlen ) {
					extra_maxlen = len;
				}

				column_key = results.getString(7);
				column_keys.addElement(column_key);
				len = column_key.length();
				if ( len > key_maxlen ) {
					key_maxlen = len;
				}

			} // WHILE displaying SELECT results

			line = rightpad("Ordinal",colname_maxlen);
			line += " " + rightpad("Column Name",colname_maxlen);
			line += " " + rightpad("Data Type",column_type_maxlen);
			line += " " + "Nullable ?";
			line += " " + rightpad("Key ?",key_maxlen);
			line += " " + rightpad("Extra",extra_maxlen);
			System.out.println(line);

			line = rightpad("=======",colname_maxlen);
			line += " " + rightpad("===========",colname_maxlen);
			line += " " + rightpad("=========",column_type_maxlen);
			line += " " + "==========";
			line += " " + rightpad("=====",key_maxlen);
			line += " " + rightpad("=====",extra_maxlen);
			System.out.println(line);

			for ( index = 0 ; index < num_columns ; ++index ) {
				ord = (String) ordinals.get(index);
				colname = (String) colnames.get(index);
				column_type = (String) column_types.get(index);
				isnull = (String) isnulls.get(index);
				// maxlen = (String) maxlens.get(index);
				extra = (String) extras.get(index);
				column_key = (String) column_keys.get(index);

				line = rightpad(ord,colname_maxlen);
				line += " " + rightpad(colname,colname_maxlen);
				line += " " + rightpad(column_type,column_type_maxlen);
				line += " " + rightpad(isnull,10);
				line += " " + rightpad(column_key,key_maxlen);
				line += " " + rightpad(extra,extra_maxlen);
				System.out.println(line);
			}
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

} // end of mydesc2
