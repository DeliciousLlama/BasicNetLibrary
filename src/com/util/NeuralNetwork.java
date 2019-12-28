package com.util;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class NeuralNetwork {
	private ArrayList<Node> inputLayer;
	private ArrayList<Node> outputLayer;
	public ArrayList<Edge> mainStructure;
	
	public NeuralNetwork() {
		inputLayer = new ArrayList<>();
		outputLayer = new ArrayList<>();
		mainStructure = new ArrayList<>();
	}
	
	public void reset() {
		for(Edge e : mainStructure) {
			if(e.start.inputEdges != null) {
				e.start.resetNode();
			}
			e.end.resetNode();
		}
	}
	
	private void setInput(ArrayList<Float> inputs) {
		for(int i = 0; i < inputs.size(); i++) {
			inputLayer.get(i).answer = inputs.get(i);
		}
	}
	
	@Override
	public String toString() {
	    return super.toString().split("@")[1];
	}
	
	public void buildInput(int inputNode) {
		for(int i = 0; i < inputNode; i++) {
			inputLayer.add(new Node(null, 0, true));
		}
	}
	
	public void buildOutput(int outputNode) {
		Random rand = new Random();
		ArrayList<Edge> connectionList = new ArrayList<>();
		for(int i = 0; i < outputNode; i++) {
			connectionList.removeAll(connectionList);
			Node tempOut = new Node(null, 0, false);
			for(int j = 0; j < inputLayer.size(); j++) {
				Edge tempEdg = new Edge(rand.nextFloat(), inputLayer.get(j), tempOut);
				connectionList.add(tempEdg);
				mainStructure.add(tempEdg);
			}
			//cloning
			for(int j = 0; j < connectionList.size(); j++) {
			    tempOut.inputEdges.add(connectionList.get(j));
			}
			outputLayer.add(tempOut);
		}
	}
	
	public void buildHidden(int nodes) {
	    //build node
	    
	}
	
	private boolean isOutputComputed() {
		for(Node n : outputLayer) {
			if(!n.isFinished()) {
				return false;
			}
		}
		return true;
	}
	
	public void computeNetwork(ArrayList<Float> inputs, Activation function) {
		setInput(inputs);
		while(!isOutputComputed()) {
			for(Edge e : mainStructure) {
				if(!e.end.isFinished() && e.start.isFinished()) {
					e.end.calculate(function);
				}
			}
		}
		//TODO fix
	}
	
	private float train(NeuralNetwork net) {
		return 0;
	}
	private float test(NeuralNetwork net) {
		return 0;
	}

	public ArrayList<Node> getOutputLayer() {
		return outputLayer;
	}

	public ArrayList<Node> getInputLayer() {
		return inputLayer;
	}
}
