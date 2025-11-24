package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;

public class HuffmanCoding {
    private static class Node {
        char ch;         
        int freq;        
        Node left, right; 

        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        Node(int freq, Node left, Node right) {
            this.ch = '\0';
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }
    }

    public static class CompressionResult {
        public final String encodedText;               
        public final Map<Character, Integer> freqTable; 
        public final Map<Character, String> codeTable;  

        public CompressionResult(String encodedText, Map<Character, Integer> freqTable, Map<Character, String> codeTable) {
            this.encodedText = encodedText;
            this.freqTable = freqTable;
            this.codeTable = codeTable;
        }
    }

    public static CompressionResult compress(String text) {
        if (text == null || text.isEmpty()) {
            return new CompressionResult("", new HashMap<>(), new HashMap<>());
        }

        Map<Character, Integer> freqTable = buildFrequencyTable(text);

        Node root = buildHuffmanTree(freqTable);

        Map<Character, String> codeTable = new HashMap<>();
        buildCodeTable(root, new StringBuilder(), codeTable);

        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            encoded.append(codeTable.get(c));
        }

        return new CompressionResult(encoded.toString(), freqTable, codeTable);
    }

    public static String decompress(String encodedText, Map<Character, String> codeTable) {
        if (encodedText == null || encodedText.isEmpty()) {
            return "";
        }
        if (codeTable == null || codeTable.isEmpty()) {
            throw new IllegalArgumentException("Code table cannot be null or empty");
        }

        Map<String, Character> reverseTable = new HashMap<>();
        for (Map.Entry<Character, String> entry : codeTable.entrySet()) {
            reverseTable.put(entry.getValue(), entry.getKey());
        }

        StringBuilder decoded = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (int i = 0; i < encodedText.length(); i++) {
            currentCode.append(encodedText.charAt(i));
            String codeStr = currentCode.toString();
            if (reverseTable.containsKey(codeStr)) {
                decoded.append(reverseTable.get(codeStr));
                currentCode.setLength(0); 
            }
        }

        return decoded.toString();
    }

    private static Map<Character, Integer> buildFrequencyTable(String text) {
        Map<Character, Integer> freqTable = new HashMap<>();
        for (char c : text.toCharArray()) {
            freqTable.put(c, freqTable.getOrDefault(c, 0) + 1);
        }
        return freqTable;
    }

    private static Node buildHuffmanTree(Map<Character, Integer> freqTable) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.freq));

        for (Map.Entry<Character, Integer> entry : freqTable.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        if (pq.size() == 1) {
            Node only = pq.poll();
            return new Node(only.freq, only, null);
        }

        while (pq.size() > 1) {
            Node n1 = pq.poll();
            Node n2 = pq.poll();
            Node parent = new Node(n1.freq + n2.freq, n1, n2);
            pq.add(parent);
        }

        return pq.poll(); 
    }

    private static void buildCodeTable(Node node, StringBuilder prefix,  Map<Character, String> codeTable) {
        if (node == null) return;

        if (node.isLeaf()) {
            String code = prefix.length() > 0 ? prefix.toString() : "0";
            codeTable.put(node.ch, code);
            return;
        }

        prefix.append('0');
        buildCodeTable(node.left, prefix, codeTable);
        prefix.deleteCharAt(prefix.length() - 1);

        prefix.append('1');
        buildCodeTable(node.right, prefix, codeTable);
        prefix.deleteCharAt(prefix.length() - 1);
    }
}
