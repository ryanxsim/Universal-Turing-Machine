import models.State;
import models.Tape;
import models.Transition;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TuringMachine {
    private State currentState;
    private final Map<State, List<Transition>> transitions;
    private final Tape tape;

    public TuringMachine(List<Transition> transitions, String tapeInput) {
        for (Transition transition : transitions) {
            if (transition.from().isStart()) {
                currentState = transition.from();
                break;
            }
        }
        if (currentState == null) {
            throw new IllegalArgumentException("Turing machine has no start state.");
        }

        this.transitions = transitions.stream().collect(Collectors.groupingBy(Transition::from));

        this.tape = new Tape(tapeInput);
    }

    public void run() {
        while (currentState != null) {
            tape.displayTape();
            if (currentState.isAccepting()) {
                System.out.println("Accepting state reached.");
                break;
            }
            List<Transition> transitions = this.transitions.get(currentState);
            if (transitions == null) {
                throw new IllegalArgumentException("No transition found for state " + currentState.getState());
            }

            for (Transition t : transitions) {
                if (t.inputSymbol() == tape.read()) {
                    tape.write(t.outputSymbol());
                    tape.move(t.direction());
                    currentState = t.to();
                    break;
                }
            }
        }
    }
}