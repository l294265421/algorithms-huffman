package com.liyuncong.algorithms.algorithms_huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.liyuncong.application.commontools.FileTools;

public abstract class HuffmanAlgorithmAbstract implements HuffmanAlgorithm {
	@Override
	public EncodeResult encode(String str) {
		ArrayList<Node> letterList = toList(str);
		Node rootNode = createTree(letterList);
		Map<Character, String> letterCode = getLetterCode(rootNode);
		EncodeResult result = encode(letterCode, str);
		return result;
	}
	
	/**
	 * 把一个字符串转化为节点列表
	 * @param letters
	 * @return
	 */
	private ArrayList<Node> toList(String letters) {
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
		return letterList;
	}
	
	protected abstract Node createTree(ArrayList<Node> letterList);
	
	/**
	 * 编码字符串。
	 * @param letterCode 字符/编码对集合。
	 * @param letters 指定的需要编码的字符串。
	 * @return 编码结果
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
	 * 先序遍历哈夫曼树,获得所有字符编码对。
	 * 
	 * @param rooNode 哈夫曼树根结点
	 * @param suffix 编码前缀，也就是编码这个字符时，之前路径上的所有编码
	 * @param letterCode 用于保存字符编码结果
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
	
	public String decode(EncodeResult decodeResult) {
		// 解码得到的字符串
		StringBuffer decodeStr = new StringBuffer();
		// 获得解码器
		Map<String, Character> decodeMap = getDecoder(decodeResult
				.getLetterCode());
		// 解码器键集合
		Set<String> keys = decodeMap.keySet();
		// 待解码的（被编码的）字符串
		String encode = decodeResult.getEncode();
		// 从最短的开始匹配之所以能够成功，是因为哈夫曼编码的唯一前缀性质
		// 临时的可能的键值
		String temp = "";
		// 改变temp值大小的游标
		int i = 1;
		while (encode.length() > 0) {
			temp = encode.substring(0, i);
			if (keys.contains(temp)) {
				Character character = decodeMap.get(temp);
				decodeStr.append(character);
				encode = encode.substring(i);
				i = 1;
			} else {
				i++;
			}
		}
		return decodeStr.toString();
	}

	/**
	 * 获得解码器，也就是通过字母/编码对得到编码/字符对。
	 * 
	 * @param letterCode
	 * @return
	 */
	private Map<String, Character> getDecoder(Map<Character, String> letterCode) {
		Map<String, Character> decodeMap = new HashMap<String, Character>();
		Set<Character> keys = letterCode.keySet();
		for (Character key : keys) {
			String value = letterCode.get(key);
			decodeMap.put(value, key);
		}
		return decodeMap;
	}
}
