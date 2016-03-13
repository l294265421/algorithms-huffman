package com.liyuncong.algorithms.algorithms_huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HuffmanAlgorithmImpl1 extends HuffmanAlgorithmAbstract {
	public EncodeResult encode(String str) {
		ArrayList<Node> letterList = init(str);
		Node rootNode = createTree(letterList);
		Map<Character, String> letterCode = getLetterCode(rootNode);
		EncodeResult result = encode(letterCode, str);
		return result;
	}

	/**
	 * 得到字符串最终编码
	 */
	private EncodeResult encode(Map<Character, String> letterCode, String letters) {
		StringBuilder encode = new StringBuilder();
		for (int i = 0, length = letters.length(); i < length; i++) {
			Character character = letters.charAt(i);
			encode.append(letterCode.get(character));
		}
		EncodeResult result = new EncodeResult(encode.toString(), letterCode);
		return result;
	}

	/**
	 * 初始化 对输入的字符串建立哈夫曼树节点列表，并且，列表中节点是有序的
	 */
	private ArrayList<Node> init(String letters) {
		ArrayList<Node> letterList = new ArrayList<Node>();
		Map<Character, Integer> ci = new HashMap<Character, Integer>();
		for (int i = 0; i < letters.length(); i++) {
			Character character = letters.charAt(i);
			if (!ci.keySet().contains(character)) {
				ci.put(character, 1);
			} else {
				Integer oldValue = ci.get(character);
				ci.put(character, oldValue + 1);
			}
		}
		Set<Character> keys = ci.keySet();
		for (Character key : keys) {
			Node node = new Node();
			Data data = new Data();
			data.setC(key);
			data.setFrequency(ci.get(key));
			node.setData(data);
			letterList.add(node);
		}
		sort(letterList);
		return letterList;
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

	/*
	 * 创建哈夫曼树； 丢失了letterList中的数据，深拷贝letterList是需要完善的地方
	 */
	private Node createTree(ArrayList<Node> letterList) {
		while (letterList.size() != 1) {
			int size = letterList.size();
			// 小的节点放在右边（眼睛看到的左边）
			Node nodeRight = letterList.get(size - 1);
			Node nodeLeft = letterList.get(size - 2);
			Node nodeParent = new Node();
			nodeParent.setRightChild(nodeRight);
			nodeParent.setLeftChild(nodeLeft);
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
	 * 获得所有字符编码对
	 * 
	 * @param rootNode哈夫曼树的根节点
	 * @return 所有字符编码对
	 */
	private Map<Character, String> getLetterCode(Node rootNode) {
		Map<Character, String> letterCode = new HashMap<Character, String>();
		// 处理只有一个节点的情况
		if (rootNode.getLeftChild() == null && rootNode.getRightChild() == null) {
			letterCode.put(rootNode.getData().getC(), "1");
			return letterCode;

		}
		getLetterCode(rootNode, "", letterCode);
		return letterCode;
	}

	/**
	 * 先序遍历哈夫曼树,获得所有字符编码对
	 * 
	 * @param rooNode
	 * @param suffix
	 * @param letterCode
	 */
	private void getLetterCode(Node rooNode, String suffix,
			Map<Character, String> letterCode) {
		if (rooNode != null) {
			if (rooNode.getLeftChild() == null
					&& rooNode.getRightChild() == null) {
				Character character = rooNode.getData().getC();
				letterCode.put(character, suffix);

			}
			getLetterCode(rooNode.getLeftChild(), suffix + "0", letterCode);
			getLetterCode(rooNode.getRightChild(), suffix + "1", letterCode);

		}
	}
}
