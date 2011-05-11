package edu.zju.cs.ooobgy.algo.dynamic_na.pojo;

import java.awt.Color;
import java.util.Set;
import java.util.UUID;

import edu.zju.cs.ooobgy.app.cache.DCD_Cache;

/**
 * 带有标记的cluster，包含点集，前端颜色，id
 * @author frogcherry 周晓龙
 * @created 2011-5-4
 * @Email frogcherry@gmail.com
 */
public class IdCluster<V> {
	private String id;
	private Set<V> vertexes;
	private Color color;
	
	
	
	public IdCluster(String id, Set<V> vertexes, Color color) {
		super();
		this.id = id;
		this.vertexes = vertexes;
		this.color = color;
	}

	public IdCluster(Set<V> vertexes, Color color) {
		super();
		this.vertexes = vertexes;
		this.color = color;
		this.id = UUID.randomUUID().toString();
	}
	
	public boolean containsVertex(V v){
		return vertexes.contains(v);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<V> getVertexes() {
		return vertexes;
	}
	public void setVertexes(Set<V> vertexes) {
		this.vertexes = vertexes;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("cluster:[");
		sb.append(id).append(", ");
		sb.append(vertexes).append("]");
				
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IdCluster) {
			IdCluster test = (IdCluster)obj;
			if (test.id.equals(this.id) && test.color.equals(this.color) 
					&& test.vertexes.equals(this.vertexes)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
