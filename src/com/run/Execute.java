package com.run;

import com.util.Activation;
import com.util.NeuralNetwork;
import com.util.Node;

import java.util.ArrayList;

public class Execute {
	
	public interface activation {
		float process(float x);
	}
	
	
	public static void main(String[] args) {
		//define activation
		
//		Activation function = x -> {return x;};
		Activation function = x -> {return (float) (1/1+Math.pow(Math.E, -x));};
		NeuralNetwork net = new NeuralNetwork();
		net.buildInput(10);
		net.buildOutput(8);
		ArrayList<Float> inputs = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			inputs.add((float) (0 + Math.random() * (10 - 0)));
		}
		net.computeNetwork(inputs, function);
		
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
