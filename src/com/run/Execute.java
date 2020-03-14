package com.run;

import com.util.Activation;
import com.util.InvalidStructureException;
import com.util.NeuralNetwork;
import com.util.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Execute {
	
	public interface activation {
		float process(float x);
	}
	
	
	public static void main(String[] args) {
		//define activation
		
//		Activation function = x -> {return x;};
		int[][] inputs = {
			{108,90,61},
			{156,62,54},
			{198,95,118},
			{63,247,13},
			{56,37,233}};
		ArrayList<ArrayList<Integer>> input = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < inputs.length; i++) {
			for(int j = 0; i < inputs[i].length; i++) {
				
			}
		}
		
		int[][] outputs = {
			{1},
			{1},
			{1},
			{0},
			{1}};
		
		NeuralNetwork net = new NeuralNetwork();
		net.buildInput(3);
		net.addHidden(60, Activation.identity);
		net.addHidden(100, Activation.identity);
		net.addHidden(70, Activation.identity);
		net.buildOutput(2, Activation.identity);
		
		ArrayList<Float> inputs = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			inputs.add((float) (0 + Math.random() * (10 - 0)));
		}
		
		try {
			net.compileNetwork();
		} catch (InvalidStructureException e) {
			e.printStackTrace();
		}
		net.computeNetwork(inputs);
		
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
