package com.liyuncong.algorithms.algorithms_huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 算法实现参考《多媒体技术教程》
 * @author yuncong
 *
 */
public class HuffmanAlgorithmImpl1 extends HuffmanAlgorithmAbstract {
	
	/*
	 * 创建哈夫曼树； 丢失了letterList中的数据，深拷贝letterList是需要完善的地方
	 */
	@Override
	protected Node createTree(ArrayList<Node> letterList) {
		init(letterList);
		while (letterList.size() != 1) {
			int size = letterList.size();
			// 小的节点放在右边（眼睛看到的左边）
			Node nodeLeft = letterList.get(size - 1);
			Node nodeRight = letterList.get(size - 2);
			Node nodeParent = new Node();
			nodeParent.setLeftChild(nodeLeft);
			nodeParent.setRightChild(nodeRight);
			Data data = new Data();
			data.setFrequency(nodeRight.getData().getFrequency()
					+ nodeLeft.getData().getFrequency());
			nodeParent.setData(data);
			letterList.set(size - 2, nodeParent);
			letterList.remove(size - 1);
			sort(letterList);

		}
		Node rootNode = letterList.get(0);
		return rootNode;
	}
	
	/**
	 * 初始化 让节点列表有序
	 */
	private void init(ArrayList<Node> letterList) {
		sort(letterList);
	}

	/**
	 * 冒泡排序，把小的放在最后
	 */
	private void sort(ArrayList<Node> letterList) {
		int size = letterList.size();
		// 处理只有一个元素的情况，也就是说，不需要排序
		if (size == 1) {
			return;
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - 1 - i; j++) {
				if (letterList.get(j).getData().getFrequency() < letterList
						.get(j + 1).getData().getFrequency()) {
					Node tempNode = letterList.get(j);
					letterList.set(j, letterList.get(j + 1));
					letterList.set(j + 1, tempNode);

				}
			}
		}
	}

}
