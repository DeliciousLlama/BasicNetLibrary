package com.util;

import java.util.ArrayList;

public class Node {
    	public ArrayList<Edge> inputEdges;
	public float bias, answer;
	public boolean isInput, isOutput;
	
	private boolean finished = false;
	private Activation.Function activation;
	
	public Node(ArrayList<Edge> inputEdges, float bias, Activation.Function activation, boolean isInputNode, boolean isOutputNode) {
	    //constructor: set the input edges of a node, set default bias, set input node or output node mode
	    inputEdges = new ArrayList<>();
	    this.inputEdges = inputEdges;
	    this.bias = bias;
	    this.activation = activation;
	    finished = isInputNode; //apply input characteristic
	    this.isInput = isInputNode;
	    isOutput = isInputNode?false:isOutputNode; //false proving the output characteristic
	}
	
	public void resetNode() { //set output to 0, and finished (latch) to false
		answer = 0;
		finished = false;
	}
	
	public void updateBias(float bias) { //update the bias for training
		this.bias = bias;
	}
	
	public void calculate() { //calculate the ouptut based on the input
		float ans = bias;
		for(Edge e : inputEdges) {
			ans += e.weight * e.start.answer;
		}
		
		//calculate activation
		answer = activation.activate(ans);
		finished = true;
	}
	
	public boolean isFinished() { //update the isfinished mode (latch)
		return finished;
	}
}
