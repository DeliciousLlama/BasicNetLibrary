package com.util;

import java.util.ArrayList;
import java.util.function.Function;

import com.run.Execute;

public class Node {
	public ArrayList<Edge> inputEdges;
	public float bias, answer;
	
	private boolean finished = false;
	
	public Node(ArrayList<Edge> inputEdges, float bias, boolean inputLayer) {
		inputEdges = new ArrayList<>();
		this.inputEdges = inputEdges;
		this.bias = bias;
		finished = inputLayer;
	}
	
	public void resetNode() {
		answer = 0;
		finished = false;
	}
	
	public void updateBias(float bias) {
		this.bias = bias;
	}
	
	public void calculate(Activation function) {
		float ans = bias;
		for(Edge e : inputEdges) {
			ans += e.weight * e.start.answer;
		}
//		answer = activation.apply(ans);
//		answer = Execute.test.process(ans);
		answer = function.activate(ans);
		
		finished = true;
	}

	
	
	public boolean isFinished() {
		return finished;
	}
}
