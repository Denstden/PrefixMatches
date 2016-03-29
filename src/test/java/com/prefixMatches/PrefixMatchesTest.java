package com.prefixMatches;

import com.prefixMatches.PrefixMatches;
import com.prefixMatches.trie.RWayTrie;
import com.prefixMatches.trie.Trie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {
    @Spy
    private RWayTrie spy;

    @Mock
    private Trie trie;

    @InjectMocks
    private PrefixMatches prefixMatches;

    @Before
    public void setUp(){
        trie = new RWayTrie();
        prefixMatches = new PrefixMatches(trie);
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void addNullShouldRiseException() {
        when(prefixMatches.add(null)).thenThrow(NullPointerException.class);
    }

    @Test
    public void addOneString() {
        int expected = 1;
        assertEquals(expected, prefixMatches.add("onetuple"));
    }

    @Test
    public void addFewString() {
        int expected = 3;
        assertEquals(expected, prefixMatches.add("onetuple two three"));
    }

    @Test
    public void addArrayOfStringsOfOneTuple() {
        int expected = 3;
        assertEquals(expected, prefixMatches.add("asb", "qwe", "xzxc"));
    }

    @Test
    public void addSmallString() {
        prefixMatches.add("as");
        verify(trie, never()).add(any(Trie.Tuple.class));
    }

    @Test
    public void addEmptyArray() {
        prefixMatches.add(new String[]{});
        verify(trie, never()).add(any(Trie.Tuple.class));
    }

    @Test
    public void addArrayWithSmallString() {
        int expected = 2;
        int actual = prefixMatches.add(new String[]{"abs","qw","wqeqeqe"});
        verify(trie, times(2)).add(any(Trie.Tuple.class));
        assertEquals(expected, actual);
    }

    @Test
    public void addArrayWithoutSmallString() {
        prefixMatches.add(new String[]{"abs", "qwewq", "wqeqeqe"});
        verify(trie, times(3)).add(any(Trie.Tuple.class));
    }

    @Test
    public void addOneTuple() {
        prefixMatches.add("abcd");
        verify(trie, times(1)).add(any(Trie.Tuple.class));
    }

    @Test
    public void addThreeTuples() {
        prefixMatches.add("abcd dsdsq qwx");
        verify(trie, times(3)).add(any(Trie.Tuple.class));
    }

    @Test
    public void contains() {
        String s = "abcd";
        prefixMatches.add(s);
        prefixMatches.contains(s);
        verify(trie, times(1)).contains(s);
    }

    @Test
    public void delete() {
        String s = "abcd";
        prefixMatches.add(s);
        prefixMatches.delete(s);
        verify(trie, times(1)).delete(s);
    }

    @Test
    public void size() {
        String s = "ab";
        prefixMatches.add(s);
        prefixMatches.size();
        verify(trie, times(1)).size();
    }

    @Test
    public void wordWithPrefix() {
        Iterable<String> iterable = new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new Iterator<String>() {
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
        };
        when(trie.wordsWithPrefix(anyString())).thenReturn(iterable);
    }

    @Test
    public void wordWithPrefixLessThenTwo() {
        prefixMatches.add("abcd weqis weqosx skqm weqiox");
        assertEquals(false, prefixMatches.wordsWithPrefix("w", 4).iterator().hasNext());
    }
}
