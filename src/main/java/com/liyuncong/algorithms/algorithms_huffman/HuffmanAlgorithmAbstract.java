package com.liyuncong.algorithms.algorithms_huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class HuffmanAlgorithmAbstract implements HuffmanAlgorithm {

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
