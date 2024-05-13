import enums.Direction;
import models.Alphabet;
import models.AlphabetBuilder;
import models.State;
import models.Transition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TuringMachineBuilder {
    private final static int CURR_STATE_POS = 0;
    private final static int INPUT_SYMBOL_POS = 1;
    private final static int NEXT_STATE_POS = 2;
    private final static int OUTPUT_SYMBOL_POS = 3;
    private final static int DIRECTION_POS = 4;

    private final static String INPUT_SEPARATOR = "111";
    private final static String TRANSITION_SEPARATOR = "11";
    private final static String PARAMETER_SEPARATOR = "1";

    private final String godelNumber;
    private String tapeInput;
    private List<String> transitionsInputs;

    public TuringMachineBuilder(String godelNumber) {
        this.godelNumber = godelNumber;
    }

    public TuringMachine build() {
        splitInput();
        List<Transition> transitions = new ArrayList<>();

        // Validate all transitions first
        boolean valid = transitionsInputs.stream()
                .map(s -> s.split(PARAMETER_SEPARATOR))
                .allMatch(params -> params.length == 5);

        if (!valid){
            throw new IllegalArgumentException("Transitions are invalid (not 5 params for each transition)");
        }

        //Alphabet inputAlphabet = buildInputAlphabet(transitionsInputs);
        Alphabet tapeAlphabet = buildTapeAlphabet(transitionsInputs);

        for (String transitionInput : transitionsInputs) {
        String[] parts = transitionInput.split(PARAMETER_SEPARATOR);
            transitions.add(createTransition(parts, tapeAlphabet));
        }

        return new TuringMachine(transitions, tapeInput);
    }

    private Alphabet buildInputAlphabet(List<String> transitionsInputs) {
        var inputAlphabet = AlphabetBuilder.buildAlphabet(AlphabetBuilder.Kind.INPUT);

        transitionsInputs.stream().map(s -> s.split(PARAMETER_SEPARATOR))
                .map(params -> params[INPUT_SYMBOL_POS])
                .forEach(inputAlphabet::addSymbolFromInput);

        return inputAlphabet;
    }

    private Alphabet buildTapeAlphabet(List<String> transitionsInputs) {
        var tapeAlphabet = AlphabetBuilder.buildAlphabet(AlphabetBuilder.Kind.TAPE);

        transitionsInputs.stream().map(s -> s.split(PARAMETER_SEPARATOR))
                .map(params -> params[OUTPUT_SYMBOL_POS])
                .forEach(tapeAlphabet::addSymbolFromInput);

        return tapeAlphabet;
    }

    private void splitInput() {
        if (!godelNumber.contains(INPUT_SEPARATOR)) {
            throw new IllegalArgumentException("Invalid Gödel number: does not contain input word");
        }

        String[] parts = godelNumber.split(INPUT_SEPARATOR, 2);
        tapeInput = parts[1];

        // Split transition from the Turing Machine Gödel  number
        transitionsInputs = Arrays.asList(parts[0].split(TRANSITION_SEPARATOR));
    }

    private State createState(String input) {
        return new State(input);
    }

    private Transition createTransition(String[] parts, Alphabet tapeAlphabet) {

        State currentState = createState(parts[CURR_STATE_POS]);
        char currentSymbol =  tapeAlphabet.getSymbol(parts[INPUT_SYMBOL_POS]);
        State nextState = createState(parts[NEXT_STATE_POS]);
        char nextSymbol = tapeAlphabet.getSymbol(parts[OUTPUT_SYMBOL_POS]);
        Direction direction = Direction.mapDirection(parts[DIRECTION_POS]);

        return new Transition(currentState, currentSymbol, nextState, nextSymbol, direction);
    }
}