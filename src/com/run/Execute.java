package com.run;

import com.util.Activation;
import com.util.InvalidStructureException;
import com.util.NeuralNetwork;
import com.util.Node;

import java.util.ArrayList;

public class Execute {
	
	public interface activation {
		float process(float x);
	}
	
	
	public static void main(String[] args) {
		//define activation
		
		ArrayList<ArrayList<Float>> input;
		ArrayList<ArrayList<Float>> output;
		int[][] inputs = {
			{108,90,61},
			{156,62,54},
			{198,95,118},
			{63,247,13},
			{56,37,233}};
		input = new ArrayList<ArrayList<Float>>();
		for(int i = 0; i < inputs.length; i++) {			
			ArrayList<Float> tempInput = new ArrayList<Float>();
			for(int j = 0; j < inputs[i].length; j++) {
				tempInput.add((float) inputs[i][j]);
			}
			input.add(tempInput);
		}
		
		int[][] outputs = {
			{1},
			{1},
			{1},
			{0},
			{1}};
		output = new ArrayList<ArrayList<Float>>();
		for(int i = 0; i < outputs.length; i++) {
			ArrayList<Float> tempOutput = new ArrayList<Float>();
			for(int j = 0; j < outputs[i].length; j++) {
				tempOutput.add((float) outputs[i][j]);
			}
			output.add(tempOutput);
		}
		
		NeuralNetwork net = new NeuralNetwork(input, output);
		net.buildInput(3);
		net.addHidden(60, Activation.identity);
		net.addHidden(100, Activation.identity);
		net.addHidden(70, Activation.identity);
		net.buildOutput(2, Activation.identity);
		
		ArrayList<Float> testInputs = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			testInputs.add((float) (0 + Math.random() * (10 - 0)));
		}
		
		try {
			net.compileNetwork();
		} catch (InvalidStructureException e) {
			e.printStackTrace();
		}
		net.computeNetwork(testInputs);
		
		System.out.println("Mode: Testing / NeuralNet ID: ["+net.toString()+"]\n----------");
		System.out.println("Output nodes:\nbias\t|\toutput(dec)\n=====");
		for(Node n : net.getOutputLayer()) {
			System.out.println(n.bias+"\t|\t"+n.answer);
		}
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("Input nodes:\nbias\t|\tvalue(dec)\n=====");
		for(Node n : net.getInputLayer()) {
			System.out.println(n.bias+"\t|\t"+n.answer);
		}
	}
}
