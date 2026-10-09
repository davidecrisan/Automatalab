package com.automatalab.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DFATest {

    // DFA over {a, b} that accepts words ending in "ab"
    private DFA endsWithAb;

    @BeforeEach
    void setUp() {
        Map<String, Map<Character, String>> transitions = Map.of(
                "q0", Map.of('a', "q1", 'b', "q0"),
                "q1", Map.of('a', "q1", 'b', "q2"),
                "q2", Map.of('a', "q1", 'b', "q0")
        );

        endsWithAb = new DFA(
                Set.of("q0", "q1", "q2"),
                Set.of('a', 'b'),
                transitions,
                "q0",
                Set.of("q2")
        );
    }

    @Test
    void acceptsWordsEndingInAb() {
        assertTrue(endsWithAb.accepts("ab"));
        assertTrue(endsWithAb.accepts("aab"));
        assertTrue(endsWithAb.accepts("bbab"));
    }

    @Test
    void rejectsWordsNotEndingInAb() {
        assertFalse(endsWithAb.accepts("a"));
        assertFalse(endsWithAb.accepts("abb"));
        assertFalse(endsWithAb.accepts("ba"));
    }

    @Test
    void rejectsEmptyWord() {
        assertFalse(endsWithAb.accepts(""));
    }

    @Test
    void throwsOnSymbolNotInAlphabet() {
        assertThrows(IllegalArgumentException.class, () -> endsWithAb.accepts("abc"));
    }

    @Test
    void throwsWhenStartStateIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                new DFA(Set.of("q0"), Set.of('a'), Map.of(), "q9", Set.of()));
    }
}