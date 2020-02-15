package com.util;

public class Edge {
	public float weight;
	public Node start, end;
	
	public Edge(float weight, Node start, Node end){
		this.weight = weight;
		this.start = start;
		this.end = end;
	}
	
	public void updateWeight(float weight) {
		this.weight = weight;
	}	
}
