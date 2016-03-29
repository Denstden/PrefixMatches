package com.prefixMatches;

import com.prefixMatches.trie.Trie;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class allows to create in-memory dictionary of words.
 *
 * @author Denys Storozhenko
 */
public class PrefixMatches {
    public final static int DEF_K = 3;
    private Trie trie;

    /**
     * Constructs prefix matcher with trie.
     *
     * @param trie - struct for dictionary
     */
    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    /**
     * Adds word(s) to dictionary.
     *
     * @param strings word or string or array of words/strings
     * @return count of added words
     */
    public int add(String... strings) {
        int count = 0;
        if (strings.length > 0) {
            for (String string : strings) {
                if (string.contains(" ")) {
                    String[] elements = string.split("[ ]+");
                    for (String s : elements) {
                        if (s.length() > 2) {
                            trie.add(new Trie.Tuple(s, s.length()));
                            count++;
                        }
                    }
                } else {
                    if (string.length() > 2) {
                        trie.add(new Trie.Tuple(string, string.length()));
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Determines whether there is a word in the dictionary.
     *
     * @param word to verify the presence in dictionary.
     * @return true if a word is in the dictionary, false - else.
     */
    public boolean contains(String word) {
        return trie.contains(word);
    }

    /**
     * Deletes word from the dictionary.
     *
     * @param word to delete from the dictionary.
     * @return true if a word has been removed from dictionary, false - else.
     */
    public boolean delete(String word) {
        return trie.delete(word);
    }

    /**
     * Returns the number of elements in dictionary.
     *
     * @return the number of elements in dictionary.
     */
    public int size() {
        return trie.size();
    }

    private class PrefixMatchesIterator<T> implements Iterator<T> {
        private Iterator<String> trieIterator;
        private String prefix;
        private int k;
        private String currentString = "";
        private boolean flag = false;

        public PrefixMatchesIterator(String pref, int k) {
            this.k = k;
            this.prefix = pref;
            trieIterator = trie.wordsWithPrefix(pref).iterator();
        }

        @Override
        public boolean hasNext() {
            if (flag) {
                return true;
            }
            if (trieIterator.hasNext()) {
                do {
                    String tmp = trieIterator.next();
                    if (tmp.length() < prefix.length() + k) {
                        currentString = tmp;
                        flag = true;
                        return true;
                    }
                }
                while (trieIterator.hasNext());
            }
            return false;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            flag = false;
            return (T) currentString;
        }
    }


    /**
     * Returns iterator for all words in dictionary, which starts with prefix
     * and has length from prefix length to prefix length+k
     *
     * @param pref prefix of each returned word
     * @param k    max length of word
     * @return iterator for all words, which starts with prefix
     */
    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() >= 2) {
            return () -> new PrefixMatchesIterator<>(pref, k);
        } else {
            return () -> new Iterator<String>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public String next() {
                    return null;
                }
            };
        }
    }

    /**
     * Returns iterator for all words in dictionary, which starts with prefix
     * and has length from prefix length to prefix length+3
     *
     * @param pref prefix of each returned word
     * @return iterator for all words, which starts with prefix
     */
    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, DEF_K);
    }
}

