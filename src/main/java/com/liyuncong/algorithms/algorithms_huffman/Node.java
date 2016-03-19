package com.liyuncong.algorithms.algorithms_huffman;

public class Node implements Comparable<Node>{
	private Node leftChild = null;
	private Data data = null;
	private Node rightChild = null;
	
	public Node getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Node getRightChild() {
		return rightChild;
	}
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}
	@Override
	public String toString() {
		return "Node [leftChild=" + leftChild + ", data=" + data
				+ ", rightChild=" + rightChild + "]";
	}
	@Override
	public int compareTo(Node o) {
		return this.data.compareTo(o.getData());
	}
	
}
