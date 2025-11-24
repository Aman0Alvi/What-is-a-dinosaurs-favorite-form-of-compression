package org.example;

public class App {

    public static void main(String[] args) {
        String text = "Hello World";

        HuffmanCoding.CompressionResult result = HuffmanCoding.compress(text);

        System.out.println("Original text: " + text);
        System.out.println("Encoded bits : " + result.encodedText);
        System.out.println("\nSymbol\tFrequency\tCode");
        for (var entry : result.freqTable.entrySet()) {
            char ch = entry.getKey();
            String symbol = (ch == ' ') ? "space" : Character.toString(ch);
            System.out.println(symbol + "\t" + entry.getValue() + "\t\t" + result.codeTable.get(ch));
        }

        String decoded = HuffmanCoding.decompress(result.encodedText, result.codeTable);
        System.out.println("\nDecoded text: " + decoded);
    }
}
