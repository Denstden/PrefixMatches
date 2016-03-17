package com.prefixMatches.trie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RWayTrieTest {
    private Trie trie;

    @Before
    public void setUp(){
        trie = new RWayTrie();
    }

    @Test(expected = NullPointerException.class)
    public void addNullTupleToTrieShouldRiseException(){
        trie.add(null);
    }

    @Test
    public void addDiffTuplesToTrie(){
        trie = new RWayTrie();
        trie.add(new Trie.Tuple("tupleone", 8));
        trie.add(new Trie.Tuple("tupletwo", 8));
        int expected = 2;
        assertEquals(expected, trie.size());
    }

    @Test
    public void addSameTuplesToTrie(){
        trie.add(new Trie.Tuple("tupleone", 8));
        trie.add(new Trie.Tuple("tupleone", 8));
        int expected = 1;
        assertEquals(expected,trie.size());
    }


    @Test(expected = NullPointerException.class)
    public void containsNullInTrieShouldRiseException(){
        trie.contains(null);
    }

    @Test
    public void containsTupleInTrieShouldBeTrue(){
        trie.add(new Trie.Tuple("tupleone", 8));
        boolean expected = true;
        assertEquals(expected, trie.contains("tupleone"));
    }

    @Test
    public void containsInTrieShouldBeFalseReasonWeight(){
        trie.add(new Trie.Tuple("tupleone", 7));
        boolean expected = false;
        assertEquals(expected, trie.contains("tupleone"));
    }

    @Test
    public void containsInTrieShouldBeFalseReasonTerm(){
        trie.add(new Trie.Tuple("tupleono", 8));
        boolean expected = false;
        assertEquals(expected, trie.contains("tupleone"));
    }


    @Test(expected = NullPointerException.class)
    public void deleteNullFromTrieShouldRiseException(){
        trie.delete(null);
    }

    @Test
    public void deleteFromTrieShouldBeTrue(){
        trie.add(new Trie.Tuple("tuple", 5));
        boolean expected = true;
        assertEquals(expected, trie.delete("tuple"));
    }

    @Test
    public void deleteFromTrieShouldBeFalse(){
        trie.add(new Trie.Tuple("tuple", 5));
        boolean expected = false;
        assertEquals(expected, trie.delete("adfsa"));
    }

    @Test
    public void deleteFromTrieSubstringShouldBeFalse(){
        trie.add(new Trie.Tuple("tuple", 5));
        boolean expected = false;
        assertEquals(expected, trie.delete("tupl"));
    }

    @Test(expected = NullPointerException.class)
    public void wordsWithPrefixNullShouldRiseException(){
        trie.wordsWithPrefix(null);
    }

    @Test
    public void wordsDifferent(){
        trie.add(new Trie.Tuple("tupleone",8));
        trie.add(new Trie.Tuple("tupletwo",8));
        trie.add(new Trie.Tuple("tuplethree",10));
        int expected = 3;
        int actual = 0;
        for (String s:trie.words()){
            actual++;
        }
        assertEquals(expected, actual);
    }

    @Test
    public void wordsSame(){
        trie.add(new Trie.Tuple("tupleone",8));
        trie.add(new Trie.Tuple("tupleone",8));
        trie.add(new Trie.Tuple("tupletwo",8));
        int expected = 2;
        int actual = 0;
        for (String s:trie.words()){
            actual++;
        }
        assertEquals(expected,actual);
    }

    @Test
    public void wordsWithPrefixDifferent(){
        trie.add(new Trie.Tuple("tupleone",8));
        trie.add(new Trie.Tuple("tupletwo",8));
        trie.add(new Trie.Tuple("tuplethree",10));
        int expected = 3;
        int actual = 0;
        for (String s:trie.wordsWithPrefix("tuple")){
            actual++;
        }
        assertEquals(expected,actual);
    }

    @Test
    public void wordsWithPrefixSame(){
        trie.add(new Trie.Tuple("tupleone",8));
        trie.add(new Trie.Tuple("tupleone",8));
        trie.add(new Trie.Tuple("tupletwo",8));
        int expected = 2;
        int actual = 0;
        for (String s:trie.wordsWithPrefix("tuple")){
            actual++;
        }
        assertEquals(expected,actual);
    }

    @Test
    public void wordsWithPrefixShouldBeEmpty(){
        trie.add(new Trie.Tuple("tupleone",8));
        trie.add(new Trie.Tuple("tupletwo",8));
        boolean expected = false;
        assertEquals(expected,trie.wordsWithPrefix("abc").iterator().hasNext());
    }
}
