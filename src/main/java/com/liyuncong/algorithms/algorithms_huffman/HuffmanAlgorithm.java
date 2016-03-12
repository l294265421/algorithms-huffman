package com.liyuncong.algorithms.algorithms_huffman;

public interface HuffmanAlgorithm {
	public EncodeResult encode(String str);
	public String decode(EncodeResult decodeResult);
}
