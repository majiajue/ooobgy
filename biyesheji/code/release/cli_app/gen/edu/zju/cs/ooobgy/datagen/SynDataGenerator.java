package edu.zju.cs.ooobgy.datagen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Random;

import edu.zju.cs.ooobgy.algo.math.matrix.Matrix;


public class SynDataGenerator {
		
	private ArrayList<Matrix> W_Cube;
	private ArrayList<Matrix> GT_Cube;
	private Matrix GT_Matrix;
	
	public SynDataGenerator()
	{
		W_Cube = new ArrayList<Matrix>();
		GT_Cube = new ArrayList<Matrix>();
	}
	
	//  inputs:
	//       T: number of time steps, e.g., 10
	//       z: average number of edges of a node connecting to other clusters,
	//          e.g., 3, 6, 10
	//       nbChange: number of nodes (divided by 3) that switch
	//          membership at each time step, e.g., 1
	//       state: initial state for random number generator
	//       blogSize: number of nodes in each graph, e.g., 128
	//       avgDegree: average degree of each node, e.g., 16
	public void GenData(int T, int z, int nbChange, int state, int blogSize, int avgDegree)
	{
		int nbCluster = 4; // the number of clusters is fixed to 4
		int clusterSize = blogSize/nbCluster;
		
		double p_out = (double)z/((double)clusterSize* (double)(nbCluster-1));
		double p_in = ((double)(avgDegree/2) - p_out*3*clusterSize)/(double)(clusterSize -1);
		
		if ( p_in < p_out )
		{
			System.out.println("Warning: p_in < p_out! Are you sure?");
			return;
		}
		/*
		GT = zeros(blogSize,nbCluster);
		for i = 1:1:nbCluster
		    start_idx = (i-1)*clusterSize+1;
		    end_idx = i*clusterSize;
		    GT(start_idx:end_idx, i) = 1;
		end
		*/
		Matrix GT = new Matrix(blogSize, nbCluster);
		for(int i = 1; i <= nbCluster; i++)
		{
			int start_idx = (i-1)*clusterSize + 1;
			int end_idx = i*clusterSize;
			for(int j = start_idx ; j <= end_idx; j++)
				GT.setElement(j-1, i-1, 1.0);
		}
		
		/*		
		W = unifrnd(0,1,blogSize,blogSize);
		
		*/
		Random random = new Random(state);
		Matrix W = new Matrix(blogSize, blogSize);
		for(int i = 0;i < blogSize; i++)
			for(int j = 0; j < blogSize; j++)
		{
			double x = random.nextDouble();
			W.setElement(i,j,x); //0和1之间的随机数
		}
		
		/*
		W1 = W;
		W2 = W;
		WW1 = zeros(blogSize,blogSize);
		WW2 = zeros(blogSize,blogSize);
		WW1(W1 <= p_in) = 1;
		WW2(W2 <= p_out) = 1;
		W = WW2;
		*/
		Matrix W1 = new Matrix(W);
		Matrix W2 = new Matrix(W);
		Matrix WW1 = new Matrix(blogSize, blogSize);
		Matrix WW2 = new Matrix(blogSize, blogSize);
		WW1.setAllElements(0.0);
		WW2.setAllElements(0.0);
		WW1.UnEqualSet(W1, p_in, 1.0, "<=");
		WW2.UnEqualSet(W2, p_out, 1.0, "<=");		
		W.Reset(WW2);
		
		/*
		for i = 1:1:nbCluster
	    	start_idx = (i-1)*clusterSize+1;
	    	end_idx = i*clusterSize;
	    	W(start_idx:end_idx,start_idx:end_idx) = WW1(start_idx:end_idx,start_idx:end_idx);
		*/
		for(int i = 1; i <= nbCluster; i++ )
		{
			int start_idx = (i-1)*clusterSize + 1;
			int end_idx = i*clusterSize;
			for(int j = start_idx - 1; j < end_idx; j++)
				for(int k = start_idx -1 ; k < end_idx; k++)
					W.setElement(j, k, WW1.element(j, k));
		}
		
		/*
		 W = W+W';
		 W(W>0)=1;
		 for i = 1:1:blogSize
    		 W(i,i) = 0;
		 end
		*/
		W = W.MatrixAdd(W.transpose());
		W.UnEqualSet(W, 0.0, 1.0, ">");
		for(int i = 0; i < blogSize; i++)
			W.setElement(i, i, 0.0);
		
		/*
		W_Cube{1} = W;
		GT_Cube{1} = GT;
		c=zeros(clusterSize,4);
		for i = 1:1:nbCluster
    		start_idx = (i-1)*clusterSize+1;
    		end_idx = i*clusterSize;
    		cc(:,i) = start_idx:end_idx;
		end
		 */
		
		W_Cube.add(W);
		
		GT_Cube.add(GT);
		
		Matrix cc = new Matrix(clusterSize, 4);
		for(int i = 1; i <= nbCluster; i++)
		{
			int start_idx = (i-1)*clusterSize + 1;
			int end_idx = i*clusterSize;
			for(int j = 1; j <= clusterSize && start_idx <= end_idx; j++, start_idx++)
				cc.setElement(j-1, i-1, (double)start_idx);
		}
		
		
		for( int kk = 1; kk < T; kk++)
		{
			Matrix WA = new Matrix(blogSize,blogSize);
			for(int i = 0;i < blogSize; i++)
				for(int j = 0; j < blogSize; j++)
			{
				double x = random.nextDouble();
				WA.setElement(i,j,x); //0和1之间的随机数
			}
			W1 = WA;
			W2 = WA;		
			WW1.setAllElements(0.0);
			WW2.setAllElements(0.0);			
			WW1.UnEqualSet(W1, p_in, 1.0, "<=");
			WW2.UnEqualSet(W2, p_out, 1.0, "<=");			
			WA = WW2;
			for(int i = 1; i <= nbCluster; i++ )
			{
				int start_idx = (i-1)*clusterSize + 1;
				int end_idx = i*clusterSize;
				for(int j = start_idx - 1; j < end_idx; j++)
					for(int k = start_idx -1 ; k < end_idx; k++)
						WA.setElement(j, k, WW1.element(j, k));
			}		
			WA = WA.MatrixAdd(WA.transpose());
			WA.UnEqualSet(WA, 0.0, 1.0, ">");
			for(int i = 0; i < blogSize; i++)
				WA.setElement(i, i, 0.0);
			
			/*
			cc(:,1) = cc(randperm(clusterSize),1);
			cc(:,2) = cc(randperm(clusterSize),2);
			cc(:,3) = cc(randperm(clusterSize),3);
			cc(:,4) = cc(randperm(clusterSize),4);
			*/
			cc.ColumnRandperm(0);
			cc.ColumnRandperm(1);
			cc.ColumnRandperm(2);
			cc.ColumnRandperm(3);
			
			/*
			i = 1;
			cc(i:(i+nbChange-1),[2 3 4 1]') = cc(i:(i+nbChange-1),[1 2 3 4]');
			i = i+nbChange;
			cc(i:(i+nbChange-1),[3 4 1 2]') = cc(i:(i+nbChange-1),[1 2 3 4]');
			i = i+nbChange;
			cc(i:(i+nbChange-1),[4 1 2 3]') = cc(i:(i+nbChange-1),[1 2 3 4]');
			*/
			int i = 1;
			for(int k = i ; k <= i+nbChange-1; k++)
			{
				double c1 = cc.element(k-1, 0);
				double c2 = cc.element(k-1, 1);
				double c3 = cc.element(k-1, 2);
				double c4 = cc.element(k-1, 3);
				
				cc.setElement(k-1, 0, c4);
				cc.setElement(k-1, 1, c1);
				cc.setElement(k-1, 2, c2);
				cc.setElement(k-1, 3, c3);
			}
			
			i += nbChange;
			for(int k = i ; k <= i+nbChange-1; k++)
			{
				double c1 = cc.element(k-1, 0);
				double c2 = cc.element(k-1, 1);
				double c3 = cc.element(k-1, 2);
				double c4 = cc.element(k-1, 3);
				cc.setElement(k-1, 0, c3);
				cc.setElement(k-1, 1, c4);
				cc.setElement(k-1, 2, c1);
				cc.setElement(k-1, 3, c2);
			}
			i += nbChange;
			for(int k = i ; k <= i+nbChange-1; k++)
			{
				double c1 = cc.element(k-1, 0);
				double c2 = cc.element(k-1, 1);
				double c3 = cc.element(k-1, 2);
				double c4 = cc.element(k-1, 3);
				cc.setElement(k-1, 0, c2);
				cc.setElement(k-1, 1, c3);
				cc.setElement(k-1, 2, c4);
				cc.setElement(k-1, 3, c1);
			}
			
			Matrix GTA = new Matrix(blogSize, nbCluster);
			GTA.setAllElements(0.0);
			for(int k = 0; k < cc.getRowCount(); k++)
			{
				double row = cc.element(k, 0);
				GTA.setElement((int)row - 1, 0, 1.0);
			}
			for(int k = 0; k < cc.getRowCount(); k++)
			{
				double row = cc.element(k, 1);
				GTA.setElement((int)row - 1, 1, 1.0);
			}
			for(int k = 0; k < cc.getRowCount(); k++)
			{
				double row = cc.element(k, 2);
				GTA.setElement((int)row - 1, 2, 1.0);
			}
			for(int k = 0; k < cc.getRowCount(); k++)
			{
				double row = cc.element(k, 3);
				GTA.setElement((int)row - 1, 3, 1.0);
			}
			//W([cc(:,1); cc(:,2); cc(:,3); cc(:,4)],:) = W;
			//W(:,[cc(:,1); cc(:,2); cc(:,3); cc(:,4)]) = W;
			/*
			 * to be added
			 */
			Matrix ccCol = new Matrix(cc.getRowCount()*cc.getColumnCount(),1);
			
			int j = 0;
			for(int m = 0; m < cc.getColumnCount(); m++)
				for(int n = 0; n < cc.getRowCount(); n++)
				{
					double element = cc.element(n, m);
					ccCol.setElement(j++, 0, element);
				}
			Matrix tmpMatrix = new Matrix(W);
			for(int k = 0; k < tmpMatrix.getColumnCount(); k++)
			{
				double[] d = tmpMatrix.getColumn(k);
				double index = ccCol.element(k, 0);
				WA.setRow((int)index - 1, d);
			}
			
			tmpMatrix = WA;
			for(int k = 0; k < tmpMatrix.getRowCount(); k++)
			{
				double[] d = tmpMatrix.getRow(k);
				double index = ccCol.element(k, 0);
				WA.setColumn((int)index - 1, d);
			}
			//W_Cube{kk} = W;
			//GT_Cube{kk} = GT;
            W_Cube.add(WA);
            GT_Cube.add(GTA);
		}
		//GT_Matrix = zeros(blogSize, T);
		//for i=1:1:T
		//    GT_Matrix(:,i) = GT_Cube{i}*[1 2 3 4]';
		GT_Matrix = new Matrix(blogSize, T);
		Matrix bMatrix = new Matrix(4,1);
		bMatrix.setElement(0, 0, 1.0);
		bMatrix.setElement(1, 0, 2.0);
		bMatrix.setElement(2, 0, 3.0);
		bMatrix.setElement(3, 0, 4.0);
		for(int i = 0; i < T; i++)
		{
			Matrix GT_sub = (Matrix)GT_Cube.get(i);
			Matrix rMatrix = GT_sub.MatrixMultiply(bMatrix);
			for(int j = 0; j < rMatrix.getRowCount(); j++)
				GT_Matrix.setElement(j, i, rMatrix.element(j, 0));
		}
	}

	public void ImportToCsvFile() throws Exception
	{
		
		int T = W_Cube.size();
		//写图数据到文件
		for(int i = 0; i < T; i++)
		{
			Matrix W = W_Cube.get(i);
			String strTime = Integer.toString(i+1);
			if(i+1<10)
				strTime = "0"+strTime;
			String SliceTimeID = "00"+strTime;
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(
							new File("data/case"+SliceTimeID+".csv")),"UTF-8"));
			for(int j = 0; j < W.getRowCount(); j++)
				for(int k = 0; k < W.getColumnCount(); k++)
				{
					if(W.element(j, k).equals(1.0))
					{
						writer.write(Integer.toString(j+1)+",");						
						writer.write(Integer.toString(k+1)+",");
						writer.write("1");
						writer.newLine();
					}
				}
			writer.close();
		}
		//写underground truth到文件
		for(int i = 0; i < T; i++)
		{
			Matrix GT = GT_Cube.get(i);
			String strTime = Integer.toString(i+1);
			if(i+1<10)
				strTime = "0"+strTime;
			String SliceTimeID = "00"+strTime;
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(
							new File("data/real"+SliceTimeID+".csv")),"UTF-8"));
			
			for(int j = 0; j < GT.getColumnCount(); j++)
				for(int k = 0; k < GT.getRowCount(); k++)
				{					
					if(GT.element(k, j).equals(1.0))
					{
						writer.write(Integer.toString(j+1)+",");
						writer.write(Integer.toString(k+1));
						writer.newLine();
					}
				}	
			writer.close();
		}
	}
	public void ImportToDB() throws Exception
	{
		try
		{
			String dbURL = "jdbc:oracle:thin:@localhost:1521:JASON";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(dbURL,"scott","tiger");
			
			
			if(!conn.isClosed())
				System.out.println("db open!!!");
		
			/*String testSql = "insert into UNDIRECTED_RALATION (PNODE_1, PNODE_2, UDR_ID)"+
			" values(?,?,?)";
			PreparedStatement pt = conn.prepareStatement(testSql);
			pt.setString(1, "1"); pt.setString(2, "2"); pt.setInt(3, 1);
			pt.executeUpdate();
			pt.close();*/
			int T = W_Cube.size();
			int count = 1;
			for(int i = 0; i < T; i++)
			{
				String sql = "insert into SYNDATA "+
				"(TIME_RANGE, PNODE_1, PNODE_2, CALL_COUNT, CALL_TIME, UDR_WEIGHT, UDR_ID) "+
				"values (?, ?, ?, ?, ?, ?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				String s_time = Integer.toString(i+1);
				if(i+1<10)
					s_time ="0"+s_time;	
				s_time = "2010"+s_time;
				Matrix W = W_Cube.get(i);
				for(int j = 0; j < W.getRowCount(); j++)
					for(int k = 0; k < W.getColumnCount(); k++)
					{
						if(W.element(j, k).equals(1.0))
						{						
							pstmt.setString(1, s_time);	pstmt.setString(2, Integer.toString(j+1));
							pstmt.setString(3, Integer.toString(k+1));pstmt.setInt(4, 0);
							pstmt.setInt(5, 0);	pstmt.setInt(6, 1);pstmt.setInt(7, count);
							pstmt.executeUpdate();
							
							System.out.println("inserted!"+Integer.toString(count)+" record!");
							count++;							
						}
					}
				pstmt.close();
			}	
			conn.commit();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
