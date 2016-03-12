package com.liyuncong.algorithms.algorithms_huffman;

import static org.junit.Assert.*;

import org.junit.Test;

public class HuffmanAlgorithmImpl1Test {

	@Test
	public void testEncodeString() {
		HuffmanAlgorithmImpl1 huffmanImpl1 = new HuffmanAlgorithmImpl1();
		EncodeResult result = huffmanImpl1.encode("李云聪李艳玲");
		System.out.println(result.getEncode());
	}

	@Test
	public void testDecode() {
		HuffmanAlgorithmImpl1 huffmanImpl1 = new HuffmanAlgorithmImpl1();
		EncodeResult result = huffmanImpl1.encode("李云聪李艳玲");
		String decode = huffmanImpl1.decode(result);
		System.out.println(decode);
	}


}
