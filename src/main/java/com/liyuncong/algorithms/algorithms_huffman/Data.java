package com.liyuncong.algorithms.algorithms_huffman;

public class Data {
	private char c = 0;
	private int frequency = 0;
	
	public char getC() {
		return c;
	}
	public void setC(char c) {
		this.c = c;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	@Override
	public String toString() {
		return "Data [c=" + c + ", frequency=" + frequency + "]";
	}
	
    
}
