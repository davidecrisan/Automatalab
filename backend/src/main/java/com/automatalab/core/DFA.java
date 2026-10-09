package com.automatalab.core;

import java.util.Map;
import java.util.Set;

/**
 * A Deterministic Finite Automaton (DFA).
 */
public class DFA {

    private final Set<String> states;
    private final Set<Character> alphabet;
    private final Map<String, Map<Character, String>> transitions;
    private final String startState;
    private final Set<String> finalStates;

    public DFA(Set<String> states,
               Set<Character> alphabet,
               Map<String, Map<Character, String>> transitions,
               String startState,
               Set<String> finalStates) {

        if (!states.contains(startState)) {
            throw new IllegalArgumentException("The start state must be one of the states");
        }
        if (!states.containsAll(finalStates)) {
            throw new IllegalArgumentException("All final states must be one of the states");
        }

        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.startState = startState;
        this.finalStates = finalStates;
    }

    /**
     * Checks if the automaton accepts the given word.
     */
    public boolean accepts(String word) {
        String current = startState;

        for (char symbol : word.toCharArray()) {
            if (!alphabet.contains(symbol)) {
                throw new IllegalArgumentException("Symbol not in alphabet: " + symbol);
            }

            Map<Character, String> row = transitions.get(current);
            if (row == null || !row.containsKey(symbol)) {
                return false; // no transition, so the word is rejected
            }
            current = row.get(symbol);
        }

        return finalStates.contains(current);
    }
}