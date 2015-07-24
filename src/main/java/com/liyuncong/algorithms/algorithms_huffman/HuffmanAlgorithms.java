package com.liyuncong.algorithms.algorithms_huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * 该程序处理至少有一个输入的情况，这要求在主函数中控制用户的输入，使之必须有输入；
 * 当用户只输入了一个字母或者全部字母一样时，该字母的编码为1
 */
public class HuffmanAlgorithms {
	// 待处理的字符串
	private StringBuffer letters;
	// 字符节点数组
	private ArrayList<Node> letterList;
	// 哈夫曼树根节点
	private Node rootNode = null;
	// 字符编码对
	private Map<Character, String> letterCode;
	// 字符串编码后的结果
	private StringBuffer lettersCode;

	public HuffmanAlgorithms(StringBuffer letters) {
		this(letters, new ArrayList<Node>(), new Node(),
				new HashMap<Character, String>(), new StringBuffer());
	}

	private HuffmanAlgorithms(StringBuffer letters, ArrayList<Node> letterList,
			Node rootNode, Map<Character, String> letterCode,
			StringBuffer lettersCode) {
		super();
		this.letters = letters;
		this.letterList = letterList;
		this.rootNode = rootNode;
		this.letterCode = letterCode;
		this.lettersCode = lettersCode;
	}

	// 编码算法
	public void algorithm() {
		init();
		createTree();
		code();
		printOut();
	}

	/**
	 * 初始化 对输入的字符串建立哈夫曼树节点列表，并且，列表中节点是有序的
	 */
	private void init() {
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
			this.letterList.add(node);
		}
		sort();
	}

	/**
	 * 冒泡排序，把小的放在最后
	 */
	private void sort() {
		// 处理只有一个元素的情况，也就是说，不需要排序
		int size = letterList.size();
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
	 * 创建哈夫曼树； 丢失了letterList中的数据
	 */
	private void createTree() {
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
			sort();

		}
		rootNode = letterList.get(0);
		resetLetterList();

	}

	// 获得所有字符编码对
	private void code() {
		// 处理只有一个节点的情况
		if (rootNode.getLeftChild() == null && rootNode.getRightChild() == null) {
			letterCode.put(rootNode.getData().getC(), "1");
			return;

		}
		preorder(this.rootNode, "");
	}

	/**
	 * 先序遍历哈夫曼树
	 * 
	 * @param rooNode
	 * @param suffix
	 */
	private void preorder(Node rooNode, String suffix) {
		if (rooNode != null) {
			if (rooNode.getLeftChild() == null
					&& rooNode.getRightChild() == null) {
				Character character = rooNode.getData().getC();
				letterCode.put(character, suffix);

			}
			preorder(rooNode.getLeftChild(), suffix + "0");
			preorder(rooNode.getRightChild(), suffix + "1");

		}
	}

	/**
	 * 得到字符串最终编码
	 */
	private void printOut() {
		for (int i = 0, length = letters.length(); i < length; i++) {
			Character character = letters.charAt(i);
			lettersCode.append(letterCode.get(character));
		}
	}

	/**
	 * 解码
	 */
	public String deCode() {
		// 解码得到的字符串
		StringBuffer deCodeStr = new StringBuffer();
		// 获得解码器
		Map<String, Character> deCodeMap = deCoder();
		// 解码器键集合
		Set<String> keys = deCodeMap.keySet();
		// 待解码的（被编码的）字符串
		String lc = lettersCode.toString();
		// 从最短的开始匹配之所以能够成功，是因为哈夫曼编码的唯一前缀性质
		// 临时的可能的键值
		String temp = "";
		// 改变temp值大小的游标
		int i = 1;
		while (lc.length() > 0) {
			temp = lc.substring(0, i);
			if (keys.contains(temp)) {
				Character character = deCodeMap.get(temp);
				deCodeStr.append(character);
				lc = lc.substring(i);
				i = 1;
			} else {
				i++;
			}
		}
		return deCodeStr.toString();
	}

	/**
	 * 获得解码器，也就是通过字母/编码对得到编码/字符对。
	 * 
	 * @return
	 */
	private Map<String, Character> deCoder() {
		Map<String, Character> deCodeMap = new HashMap<String, Character>();
		Set<Character> keys = letterCode.keySet();
		for (Character key : keys) {
			String value = letterCode.get(key);
			deCodeMap.put(value, key);
		}
		return deCodeMap;
	}

	// 重置letterList
	private void resetLetterList() {
		// 只有在编码之后，这个操作才是需要的
		getLetterList().clear();
		preOrder(getLetterList(), getRootNode());
	}

	/**
	 * 遍历哈夫曼树，把里面的叶子节点添加进letterList中， 这是对父类中创建哈夫曼树时直接操作letterList的后果
	 * 
	 * @param nodes
	 * @param rootNode
	 */
	private static void preOrder(ArrayList<Node> nodes, Node rootNode) {
		// 一个节点要么是null，要么非null
		if (rootNode != null) {
			// 一个非null节点要么是叶子节点，要么是根节点
			// 我们只需要叶子节点
			if (rootNode.getLeftChild() == null
					&& rootNode.getRightChild() == null) {
				nodes.add(rootNode);
			} else {
				preOrder(nodes, rootNode.getLeftChild());
				preOrder(nodes, rootNode.getRightChild());
			}
		}
	}

	public void printLetterTimesFre() {
		if (getRootNode() == null) {
			System.out.println("还未进行编码");
			return;
		} else {
			resetLetterList();
		}
		int letterNum = getLetters().length();
		for (Node node : getLetterList()) {
			Data data = node.getData();
			Character character = data.getC();
			System.out.println(character);
			int num = data.getFrequency();
			System.out.println(num);
			double frequency = (double) num / letterNum;
			System.out.println(frequency);
		}

	}

	public StringBuffer getLetters() {
		return letters;
	}

	public void setLetters(StringBuffer letters) {
		this.letters = letters;
	}

	public ArrayList<Node> getLetterList() {
		return letterList;
	}

	public void setLetterList(ArrayList<Node> letterList) {
		this.letterList = letterList;
	}

	public Node getRootNode() {
		return rootNode;
	}

	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
	}

	public Map<Character, String> getLetterCode() {
		return letterCode;
	}

	public void setLetterCode(Map<Character, String> letterCode) {
		this.letterCode = letterCode;
	}

	public StringBuffer getLettersCode() {
		return lettersCode;
	}

	public void setLettersCode(StringBuffer lettersCode) {
		this.lettersCode = lettersCode;
	}

}
