package edu.zju.cs.ooobgy.algo.util;

import org.apache.commons.collections15.KeyValue;

/**
 * 简单的KV对实现
 * @author frogcherry 周晓龙
 * @created 2010-12-14
 * @Email frogcherry@gmail.com
 */
public class SimpleKeyValue<K, V> implements KeyValue<K, V>{
	private K key;
	private V value;

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	public SimpleKeyValue(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	public void update(K key, V value){
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "<" + key + ", " + value + ">";
	}
}
