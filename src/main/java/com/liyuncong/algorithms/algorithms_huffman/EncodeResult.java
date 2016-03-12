package com.liyuncong.algorithms.algorithms_huffman;

import java.util.Map;

public class EncodeResult {
	// 字符串编码后的结果
	private String encode;
	// 字符编码对
	private Map<Character, String> letterCode;
	public EncodeResult(String encode, Map<Character, String> letterCode) {
		super();
		this.encode = encode;
		this.letterCode = letterCode;
	}
	public String getEncode() {
		return encode;
	}
	public Map<Character, String> getLetterCode() {
		return letterCode;
	}
}
