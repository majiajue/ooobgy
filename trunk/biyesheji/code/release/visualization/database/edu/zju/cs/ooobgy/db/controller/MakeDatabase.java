/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package edu.zju.cs.ooobgy.db.controller;

public class MakeDatabase {
	public static void main(String[] args) {
		InitDatabase initDatabase = new InitDatabase();
		initDatabase.createSchema();
	}
}
