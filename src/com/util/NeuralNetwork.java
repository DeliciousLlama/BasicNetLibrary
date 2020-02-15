package com.util;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class NeuralNetwork{
	private ArrayList<Node> inputLayer;
	private ArrayList<Node> outputLayer;
	public ArrayList<Edge> mainEdgeStructure;
	public ArrayList <ArrayList<Node>> mainNodeStructure;
	
	public NeuralNetwork() { //constructor
		inputLayer = new ArrayList<>();
		outputLayer = new ArrayList<>();
		mainEdgeStructure = new ArrayList<>();
		mainNodeStructure = new ArrayList<>();
	}
	
	public void reset() { //used in the beginning of running, and reset all the output to 0 and finished to false
		for(Edge e : mainEdgeStructure) {
			if(e.start.inputEdges != null) {
				e.start.resetNode();
			}
			e.end.resetNode();
		}
	}

	private void setInput(ArrayList<Float> inputs) { //
		for(int i = 0; i < inputs.size(); i++) {
			mainNodeStructure.get(0).get(i).answer = inputs.get(i);
		}
	}
	
	public void buildInput(int inputNode) {
	    for(int i = 0; i < inputNode; i++) {
    		Node tempNode = new Node(null, 0, Activation.identity, true, false);
    		inputLayer.add(tempNode); //repeat for given times add input nodes
	    }
	    mainNodeStructure.add(inputLayer);
	}
	
	public void buildOutput(int outputNode, Activation.Function activation) {
	    for(int i = 0; i < outputNode; i++) {
	    	Node tempNode = new Node(null, 0, activation, false, true);
	    	outputLayer.add(tempNode); //repeat for given times add output nodes
	    }
	    mainNodeStructure.add(outputLayer);
	}
	
	public void addHidden(int depth, Activation.Function activation) {
		ArrayList<Node> hLayer = new ArrayList<>();
		for(int i = 0; i < depth; i++) {
	    	Node tempNode = new Node(null, 0, activation, false, false);
	    	hLayer.add(tempNode); //repeat for given times add output nodes
	    }
	    mainNodeStructure.add(hLayer);
	}
	
	public void compileNetwork() throws InvalidStructureException{
		/*
		 * pseudo code:
		 * ---
		 * does the network have an input and output node?
		 * 		if not, throw an illegalOperationException
		 * #since layers are added in order, we compile from start node to end node
		 * for all node arrays(excluding first) in main node list:
		 * 		for everyNode in the current array:
		 * 			create an empty arraylist
		 * 			for everPreviousNode in the previous array:
		 * 				make a new edge in which the connection is the previous node and the end node the current node
		 * 				add that edge to the empty arraylist
		 *			set the arraylist as the node's input edges
		 * ---
		 */
		
		//check if the network have input and output node and their order
		if(!(mainNodeStructure.get(0).get(0).isInput
				&& mainNodeStructure.get(mainNodeStructure.size()-1).get(0).isOutput)) {
			//if it is not, the network therefore must be improper
			throw new InvalidStructureException("Missing or illegal IO layer position.");
		}
		
		for(int i = 1; i < mainNodeStructure.size(); i++) {
			for(Node n : mainNodeStructure.get(i)) {
				ArrayList<Edge> tempEdges = new ArrayList<>();
				for(Node ni : mainNodeStructure.get(i-1)) {
					Edge e = new Edge(1, ni, n);
					tempEdges.add(e);
				}
				n.inputEdges = tempEdges;
			}
		}
	}
	
	public void computeNetwork(ArrayList<Float> inputs) {
		setInput(inputs);
		for(int i = 1; i < mainNodeStructure.size(); i++) {
			for(Node n : mainNodeStructure.get(i)) {
				n.calculate();
			}
		}
	}
	
	private float train(int epochs) {
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
	
	@Override
	public String toString() {
	    return super.toString().split("@")[1];
	}
}
