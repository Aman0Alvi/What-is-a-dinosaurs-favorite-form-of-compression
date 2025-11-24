package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void compressThenDecompressReturnsOriginal() {
        String original = "Hello World";
        HuffmanCoding.CompressionResult result = HuffmanCoding.compress(original);
        String decoded = HuffmanCoding.decompress(result.encodedText, result.codeTable);

        assertEquals(original, decoded, "Decompressed text should match original");
    }
}
