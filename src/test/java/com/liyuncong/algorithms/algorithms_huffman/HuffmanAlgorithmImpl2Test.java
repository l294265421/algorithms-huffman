package com.liyuncong.algorithms.algorithms_huffman;

import static org.junit.Assert.*;

import org.junit.Test;

public class HuffmanAlgorithmImpl2Test {

	@Test
	public void testEncodeString() {
		HuffmanAlgorithmImpl2 huffmanImpl2 = new HuffmanAlgorithmImpl2();
		EncodeResult result = huffmanImpl2.encode("李云聪李艳玲");
		System.out.println(result.getEncode());
		// 01001100111101
		// 01011100111100
	}

	@Test
	public void testDecode() {
		HuffmanAlgorithmImpl1 huffmanImpl2 = new HuffmanAlgorithmImpl1();
		EncodeResult result = huffmanImpl2.encode("李云聪李艳玲");
		String decode = huffmanImpl2.decode(result);
		System.out.println(decode);
	}


}
