package com.ooobgy.hdbdaomk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ooobgy.util.IniConfReader;
import com.ooobgy.util.StringEnvReplacer;

public class HdbMaker {
	private Map<String, String> envs;
	private List<String> entities = new LinkedList<String>();
	private StringEnvReplacer envReplacer;
	private String confFile;
	
	public HdbMaker(String confFile) {
		super();
		this.confFile = confFile;
	}

	public void makeHdb() {
		init();
		makeTDataDao();
		makeTDataDaoImpl();
		for (String entity : entities) {
			makeDao(entity);
			makeDaoImpl(entity);
		}
	}

	private void init() {
		readConf();
		readEntities();
		envReplacer = new StringEnvReplacer(envs);
	}

	private void makeDaoImpl(String entity) {
		envs.put("entity", entity);
		makeTarget(envs.get("IMPL_TPL"), envs.get("IMPL_DIR") + "/" + entity + "DaoImpl.java");
	}

	private void makeDao(String entity) {
		envs.put("entity", entity);
		makeTarget(envs.get("DAO_TPL"), envs.get("DAO_DIR") + "/" + entity + "Dao.java");
	}

	private void makeTDataDaoImpl() {
		makeTarget(envs.get("TDataDao_TPL"), envs.get("DAO_DIR") + "/TDataDao.java");
	}

	private void makeTarget(String tpl, String target) {
		try {
			BufferedReader reader = openFileReader(tpl);
			PrintStream output = openFileWriter(target);
			String line = "";
			while ((line = reader.readLine())!=null) {
				output.println(envReplacer.replace(line));
			}
			
			reader.close();
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("file NOT found!!");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("io error!!");
			e.printStackTrace();
		}
	}

	private void makeTDataDao() {
		makeTarget(envs.get("TDataDaoImpl_TPL"), envs.get("IMPL_DIR") + "/TDataDaoImpl.java");
	}

	private PrintStream openFileWriter(String fileName) throws FileNotFoundException {
		PrintStream output = new PrintStream(new File(fileName));
		return output;
	}
	
	private BufferedReader openFileReader(String fileName)
			throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
		return reader;
	}

	private void readEntities() {
		File dir = new File(envs.get("ENTITY_DIR"));
		String[] files = dir.list();
		for (String file : files) {
			if (file.endsWith(".java")) {
				entities.add(file.split("\\.")[0]);
			}
		}
	}

	private void readConf() {
		IniConfReader iniReader = new IniConfReader();
		try {
			envs = iniReader.readIni(confFile);
		} catch (IOException e) {
			System.err.println("Can't read conf file!!");
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HdbMaker maker = new HdbMaker("data/config.ini");
		maker.makeHdb();
	}

}
