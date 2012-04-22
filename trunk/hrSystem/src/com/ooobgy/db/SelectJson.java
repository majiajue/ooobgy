package com.ooobgy.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectJson {
	public static String select(String sql) {
		StringBuilder sb = new StringBuilder();
		Connection conn = MysqlConnection.getConnection();
		Statement statement;
		try {
			sb.append("[");
			statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			ResultSetMetaData meta = result.getMetaData();
			int colCnt = meta.getColumnCount();
			while (result.next()) {
				sb.append("{");
				for (int i = 1; i <= colCnt; i++) {
					sb.append(meta.getColumnLabel(i)).append(":'");
					sb.append(result.getString(i)).append("',");
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append("},");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (sb.length() > 2) {
			sb.deleteCharAt(sb.length()-1);
		}
		
		sb.append("]");
		return sb.toString();
	}
}
