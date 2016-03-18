package com.prefixMatches.trie;

import java.util.*;

/**
 *  In-memory dictionary implementation of the (@code Trie).
 *
 *  @author Denys Storozhenko
 *  @see Trie
 */
public class RWayTrie implements Trie {
    private final static int R = 26;
    private Node root;
    private int size;

    private static class Node {
        private char value;
        private int weight;
        private Node[] next;

        public Node() {
            weight = -1;
            next = new Node[R];
        }
    }

    /**
     * Constructs an empty rWayTrie.
     */
    public RWayTrie() {
        root = new Node();
        size = 0;
    }

    /**
     * Adds to rWayTrie a tuple.
     *
     * @param tuple consists from two elements - word(term) and his length(weight)
     */
    public void add(Tuple tuple) {
        Node temp = root;
        boolean flag = false;
        for (int i = 0; i < tuple.getWeight(); i++) {
            char c = tuple.getTerm().charAt(i);
            if (temp.next[c - 'a'] != null) {//if there is son with value 'c' go down
                temp = temp.next[c - 'a'];
                continue;
            }
            flag = true;
            temp.next[c - 'a'] = new Node();
            temp.next[c - 'a'].value = c;
            temp = temp.next[c - 'a'];
        }
        if (flag || temp.weight == -1)//increase the size, if no such word as term in dictionary
            size++;
        temp.weight = tuple.getWeight();
    }

    /**
     * Determines whether there is a word in the rWayTrie.
     *
     * @param word to verify the presence in trie.
     * @return true if a word is in the trie, false - else.
     */
    public boolean contains(String word) {
        Node temp = root;
        for (int i = 0; i < word.length(); i++) {
            if (temp.next[word.charAt(i) - 'a'] == null) {//if at least one character is not the same
                return false;
            }
            temp = temp.next[word.charAt(i) - 'a'];
        }
        return temp.weight != -1;
    }

    /**
     * Deletes word from the rWayTrie.
     *
     * @param word to delete from the trie.
     * @return true if a word has been removed from trie, false - else.
     */
    public boolean delete(String word) {
        if (!contains(word))
            return false;
        Node temp = root;
        LinkedList<Node> stack = new LinkedList<>();
        for (int i = 0; i < word.length(); i++) {//add to stack all nodes for which need to pass to get the word out
            stack.addFirst(temp.next[word.charAt(i) - 'a']);
            temp = temp.next[word.charAt(i) - 'a'];
        }

        if (!stack.isEmpty()) stack.peek().weight = -1;
        while (!stack.isEmpty()) {
            temp = stack.pop();
            for (int i = 0; i < temp.next.length; i++) {
                if (temp.next[i] != null) {//if there is at least one son, node can not be removed
                    return true;
                }
            }
            if ((temp.weight == -1 || temp.weight >= word.length()) && !stack.isEmpty()) {
                stack.peek().next[temp.value - 'a'] = null;
            }
        }
        return true;
    }

    /**
     * Iterator for all words, breadth-first search.
     *
     * @return iterable of strings.
     */
    public Iterable<String> words() {
        return RWayTrieIterator::new;
    }

    /**
     * Iterator for all words, which starts with pref breadth-first search.
     *
     * @return iterable of strings.
     */
    public Iterable<String> wordsWithPrefix(String pref) {
        return () -> new RWayTrieIterator<>(pref);
    }

    private class RWayTrieIterator<T> implements Iterator<T> {
        private Queue<Node> nodes = new LinkedList<>();
        private Queue<String> strings = new LinkedList<>();
        private String currentString;

        public RWayTrieIterator() {
            for (int i = 0; i < R; i++) {
                if (root.next[i] != null) {
                    nodes.add(root.next[i]);
                    strings.add(root.next[i].value + "");
                }
            }
        }

        public RWayTrieIterator(String pref) {
            boolean flag = false;
            strings.add(pref);
            Node tempNode = root;
            for (int i = 0; i < pref.length(); i++) {
                if (tempNode.next[pref.charAt(i) - 'a'] == null) {
                    flag = true;
                    break;
                }
                tempNode = tempNode.next[pref.charAt(i) - 'a'];
            }
            if (!flag)
                nodes.add(tempNode);
        }

        @Override
        public boolean hasNext() {
            if (!nodes.isEmpty()) {
                currentString = getNextWord();
                return currentString != null;
            }
            return false;
        }

        @Override
        public T next() {
            return (T) currentString;
        }

        private String getNextWord() {
            String tempString;
            Node tempNode;
            while (!nodes.isEmpty()) {
                tempNode = nodes.remove();
                tempString = strings.remove();
                for (int i = 0; i < R; i++) {
                    if (tempNode.next[i] != null) {
                        nodes.add(tempNode.next[i]);
                        strings.add(tempString + tempNode.next[i].value);
                    }
                }
                if (tempNode.weight != -1) {
                    return tempString;
                }
            }
            return null;
        }
    }

    /**
     * Returns the number of elements in trie.
     *
     * @return the number of elements in trie.
     */
    public int size() {
        return size;
    }
}
