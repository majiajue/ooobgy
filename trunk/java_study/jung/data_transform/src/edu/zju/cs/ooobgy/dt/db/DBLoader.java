package edu.zju.cs.ooobgy.dt.db;

public interface DBLoader<T> {
	public T load(String time_range); 
}
