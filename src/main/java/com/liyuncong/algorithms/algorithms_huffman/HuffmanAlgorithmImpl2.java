package com.liyuncong.algorithms.algorithms_huffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.liyuncong.datastructure.datastructure_common.MinPriorityQueue;

/**
 * 算法实现参考《算法导论》中赫夫曼编码一节
 * @author yuncong
 *
 */
public class HuffmanAlgorithmImpl2 extends HuffmanAlgorithmAbstract {

	@Override
	protected Node createTree(ArrayList<Node> letterList) {
		int n = letterList.size();
		Node[] a = new Node[n];
		letterList.toArray(a);
		MinPriorityQueue<Node> helper = new MinPriorityQueue<Node>(a, n);
		// 需要n-1步
		for(int i = 1; i <= n - 1; i++) {
			Node left = helper.heapExtractMin();
			Node right = helper.heapExtractMin();
			Data data = new Data();
			data.setFrequency(left.getData().getFrequency() + 
					right.getData().getFrequency());
			Node parent = new Node();
			parent.setData(data);
			parent.setLeftChild(left);
			parent.setRightChild(right);
			helper.minHeapInsert(parent);
		}
		return helper.heapExtractMin();
	}

}
