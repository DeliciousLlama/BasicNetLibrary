package com.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NeuralNetwork{
	private ArrayList<Node> inputLayer;
	private ArrayList<Node> outputLayer;
	public ArrayList<Edge> mainEdgeStructure;
	public ArrayList <ArrayList<Node>> mainNodeStructure;
	public Map<ArrayList<Float>, ArrayList<Float>> dataSet;
	
	public NeuralNetwork(ArrayList<ArrayList<Float>> inputs, ArrayList<ArrayList<Float>> outputs) { //constructor
		inputLayer = new ArrayList<>();
		outputLayer = new ArrayList<>();
		mainEdgeStructure = new ArrayList<>();
		mainNodeStructure = new ArrayList<>();
		//check length security
		if (inputs.size() != outputs.size()){
			System.err.println("WARNING: Given INPUT size contradicts OUTPUT size. Output will be trimmed to Input size!");
		}
		System.out.println("Data initialization will start in 5 seconds...");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		dataSet = new HashMap<>();
		
		//start initialization of data
		for(int i = 0; i < inputs.size(); i++) {
			dataSet.put(inputs.get(i), outputs.get(i));
		}
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
	
	public ArrayList<Float> computeNetwork(ArrayList<Float> inputs) {
		setInput(inputs);
		for(int i = 1; i < mainNodeStructure.size(); i++) {
			for(Node n : mainNodeStructure.get(i)) {
				n.calculate();
			}
		}
		ArrayList<Float> ans = new ArrayList<>();
		for(Node n : mainNodeStructure.get(mainNodeStructure.size()-1)) {
			ans.add(n.answer);
		}
		return ans;
	}
	
	private float getError(ArrayList<Float> answerData, ArrayList<Float> outputData) {
		//compute all the answer data
		float error = 0;
		int i = 0;
		//sqrt(A1^2 - O1^2) + sqrt(A2^2 - O2^2)
		for(float answer : answerData) {
			error += (float)(Math.pow(answer, 2) - Math.pow(outputData.get(i), 2));
			i++;
		}
		return (float) Math.sqrt(error);
	}
	
	public float train(int epochs) {
		//run all the inputs and compute the error
		for(int a = 0; a < epochs; a++) {
			//0. scramble data											(done)
			//1. compute												(done)
			//2. get error												(done)
			//3. modify(stochastic or batch gradient decent)			(In Progress)
			
			//scramble
			ArrayList<ArrayList<Float>> inputSets = new ArrayList<>();
			inputSets.addAll(dataSet.keySet());
			Collections.shuffle(inputSets);
			
			//compute + get error
			ArrayList<ArrayList<Float>> computedAnswers = new ArrayList<ArrayList<Float>>();
			ArrayList<Float> errors = new ArrayList<Float>();
			
			for(ArrayList<Float> input : inputSets) {
				ArrayList<Float> outputData = computeNetwork(input);
				computedAnswers.add(outputData);
				errors.add(getError(input, outputData));
			}
			
			
		}
		return 0;
	}
	
	@SuppressWarnings("unused")
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
