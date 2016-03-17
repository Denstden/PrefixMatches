package com.prefixMatches;

import com.prefixMatches.trie.RWayTrie;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        PrefixMatches prefixMatches = new PrefixMatches(new RWayTrie());
        prefixMatches.add(new Parser("words-333333.txt").parse());
        for (String str:prefixMatches.wordsWithPrefix("mock",18)){
            System.out.println(str);
        }
    }
}
