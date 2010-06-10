/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package init.database;

public class MakeDatabase {
	public static void main(String[] args) {
		InitDatabase initDatabase = new InitDatabase();
		initDatabase.createSchema();
	}
}
