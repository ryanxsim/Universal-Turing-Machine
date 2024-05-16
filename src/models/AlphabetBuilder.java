package models;

import java.util.HashMap;
import java.util.Map;

public class AlphabetBuilder {
    private static  final Map<String, Character> INITIAL_TAPE_MAP = Map.of("0", '0', "00", '1', "000", '_');

    public static Alphabet buildAlphabet() {
        return new Alphabet(new HashMap<>(INITIAL_TAPE_MAP));
    }
}
