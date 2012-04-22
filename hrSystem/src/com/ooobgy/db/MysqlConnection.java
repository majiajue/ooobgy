package com.ooobgy.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConnection {
	static public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/hrsys";        
	        String username = "root";
	        String password = "ooo";
	        conn = DriverManager.getConnection(url, username, password);   
		} catch (Exception e) {
			if (conn != null)  
            {  
                try  
                {  
                    conn.close ();  
                    System.out.println ("Database connection terminated");  
                }  
                catch (Exception e1) { /* ignore close errors */ }  
            } 
			e.printStackTrace(); 
		}
		return conn;
	}
}
