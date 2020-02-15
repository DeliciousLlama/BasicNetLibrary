package com.util;

public class Activation{
    public static interface Function{ //this is the inner lambda interface
    	float activate(float x);
    }
    
    //activation functions:
    public static Activation.Function identity = x -> {return x;};
    public static Activation.Function sigmoid = x -> {return (float) (1/1+Math.pow(Math.E, -x));};
    public static Activation.Function tanh = x -> {return (float)(Math.tanh(x));};
    public static Activation.Function relu = x -> {return (x<=0)? 0:x;};
    public static Activation.Function leakyRelu = x -> {return (float) ((x<=0)? 0.01*x:x);};
    public static Activation.Function binStep = x -> {return (x<0)?0:1;};
}
