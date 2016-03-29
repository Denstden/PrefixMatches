package com.prefixMatches.trie;

/**
 * Interface contains main methods for working with trie.
 *
 * @author Denys Storozhenko
 * @see RWayTrie
 */
public interface Trie {

    /**
     * Object of this class allows to store a couple.
     */
    class Tuple {
        private String term;
        private int weight;

        /**
         * Constructs a tuple from two parameters - term and weight.
         *
         * @param term   - word in tuple.
         * @param weight - words length.
         */
        public Tuple(String term, int weight) {
            this.term = term;
            this.weight = weight;
        }

        public String getTerm() {
            return term;
        }

        public int getWeight() {
            return weight;
        }
    }

    /**
     * Adds to trie a tuple.
     *
     * @param tuple consists from two elements - word(term), his length(weight)
     */
    void add(Tuple tuple);

    /**
     * Determines whether there is a word in the trie.
     *
     * @param word to verify the presence in trie.
     * @return true if a word is in the trie, false - else.
     */
    boolean contains(String word);

    /**
     * Deletes word from the trie.
     *
     * @param word to delete from the trie.
     * @return true if a word has been removed from trie, false - else.
     */
    boolean delete(String word);

    /**
     * Iterator for all words, breadth-first search.
     *
     * @return iterable of strings.
     */
    Iterable<String> words();

    /**
     * Iterator for all words, which starts with pref breadth-first search.
     *
     * @return iterable of strings.
     */
    Iterable<String> wordsWithPrefix(String pref);

    /**
     * Returns the number of elements in trie.
     *
     * @return the number of elements in trie.
     */
    int size();
}
