package models;

import java.util.HashMap;
import java.util.Map;

public class AlphabetBuilder {
    private static  final Map<String, Character> INITIAL_INPUT_MAP  = Map.of("0", '0', "00", '1');
    private static  final Map<String, Character> INITIAL_TAPE_MAP = Map.of("0", '0', "00", '1', "000", '_');

    public static Alphabet buildAlphabet(Kind alphabetKind) {
        return switch(alphabetKind) {
            case INPUT -> new Alphabet(new HashMap<>(INITIAL_INPUT_MAP));
            case TAPE -> new Alphabet(new HashMap<>(INITIAL_TAPE_MAP));
            default -> throw new IllegalArgumentException("Invalid alphabet kind.");
        };
    }

    public enum Kind {
        INPUT,
        TAPE
    }
}
