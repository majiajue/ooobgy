package edu.zju.cs.ooobgy.datagen;




public class GenDataTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		//blogSize必须是4的倍数，因为clusterSize固定为4
		int nbTime = 1;
		int nbChange = 1;
		int z = 0;
		int blogSize = 128;
		int avgDegree =  16;
		int state = 10;
		
		SynDataGenerator sdg = new SynDataGenerator();
		sdg.GenData(nbTime, z, nbChange, state, blogSize, avgDegree);
		//sdg.ImportToDB();
		sdg.ImportToCsvFile();
		System.out.println("generate data over!!!");
		
		/*try
		{
			String dbURL = "jdbc:oracle:thin:@localhost:1521:JASON";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(dbURL,"scott","tiger");
			String testSql = "insert into SYNDATA (UDR_ID, TIME_RANGE, PNODE_1, PNODE_2, CALL_COUNT, CALL_TIME, UDR_WEIGHT)"+
			" values(?,?,?,?,?,?,?)";
			//String testSql = "insert into UNDIRECTED_RALATION (UDR_ID, TIME_RANGE, PNODE_1, PNODE_2, CALL_COUNT, CALL_TIME, UDR_WEIGHT) values (2, '201001', '2', '3', 0, 0, 0)";
			//String testSql = "insert into PERSON_NODE (PID) values(101)";
			//Statement pt = conn.createStatement();
			PreparedStatement pt = conn.prepareStatement(testSql);
			
			pt.setInt(1, 3); pt.setString(2, "201003"); pt.setString(3, "4");pt.setString(4, "5");
			pt.setInt(5, 1); pt.setInt(6, 0); pt.setInt(7, 9);
			pt.executeQuery();
			pt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}*/
		
	}

}
