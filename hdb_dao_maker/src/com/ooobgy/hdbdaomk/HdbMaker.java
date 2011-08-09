package com.ooobgy.hdbdaomk;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ooobgy.util.IniConfReader;

public class HdbMaker {
	private Map<String, String> conf;
	private List<String> entities = new LinkedList<String>();
	
	public void makeHdb(){
		readConf();
		readEntities();
		makeTDataDao();
		makeTDataDaoImpl();
		for (String entity : entities) {
			makeDao(entity);
			makeDaoImpl(entity);
		}
	}

	private void makeDaoImpl(String entity) {
		// TODO Auto-generated method stub
		
	}

	private void makeDao(String entity) {
		// TODO Auto-generated method stub
		
	}

	private void makeTDataDaoImpl() {
		// TODO Auto-generated method stub
		
	}

	private void makeTDataDao() {
		// TODO Auto-generated method stub
		
	}

	private void readEntities() {
		File dir = new File(conf.get("ENTITY_DIR"));
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
			conf = iniReader.readIni(conf.get("CONF_FILE"));
		} catch (IOException e) {
			System.err.println("Can't read conf file!!");
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HdbMaker maker = new HdbMaker();
		maker.makeHdb();
	}

}
