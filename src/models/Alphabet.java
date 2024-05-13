package models;

import java.util.Map;

public class Alphabet {
    private final static int MAX_ADDITIONAL_SYMBOLS = 26;

    private final Map<String, Character> alphabet;
    private int initialSymbolCount;

    public Alphabet(Map<String, Character> alphabet){
        this.alphabet = alphabet;
        initialSymbolCount = alphabet.size();
    }

    public void addSymbolFromInput(String input){
        // Inputs must only contain 0s.
        if (input == null || input.isBlank() || input.chars().anyMatch(c -> c != '0')){
            throw new IllegalArgumentException("Invalid input");
        }

        if(input.length() > MAX_ADDITIONAL_SYMBOLS + initialSymbolCount){
            throw  new IllegalArgumentException("Symbol count exceeded.");
        }

        if (alphabet.containsKey(input)){
            return;
        }

        char  symbol = (char) ('a' + alphabet.size() - initialSymbolCount);
        alphabet.put(input, symbol);
    }

    public char getSymbol(String input) {
        return alphabet.get(input);
    }

    public Map<String, Character> getAlphabet() {
        return alphabet;
    }
}

