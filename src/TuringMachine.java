import models.State;
import models.Tape;
import models.Transition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TuringMachine {
    private State currentState;
    private final Map<State, List<Transition>> states;
    private final Tape tape;

    public TuringMachine(List<Transition> states, String tapeInput) {
        for (Transition transition : states) {
            if (transition.from().isStart()) {
                currentState = transition.from();
                break;
            }
        }
        if (currentState == null) {
            throw new IllegalArgumentException("Turing machine has no start state.");
        }

        this.states = states.stream().collect(Collectors.groupingBy(Transition::from));
        this.tape = new Tape(tapeInput);
    }

    public void run(boolean runningMode) throws InterruptedException {
        int currentStep = 0;
        while (currentState != null) {
            currentStep++;

            if (currentState.isAccepting()) {
                showFinishingOutput(currentStep);
                break;
            }

            if (runningMode) {
                showIntermediateOutput(currentStep);
                Thread.sleep(1000);
            }

            List<Transition> transitions = states.get(currentState);
            if (transitions == null) {
                throw new IllegalArgumentException("No transition found for state " + currentState.getState());
            }

            boolean transitionFound = false;
            for (Transition t : transitions) {
                if (t.inputSymbol() == tape.read()) {
                    tape.write(t.outputSymbol());
                    tape.move(t.direction());
                    currentState = t.to();
                    transitionFound = true;
                    break;
                }
            }

            if (!transitionFound) {
                showFinishingOutput(currentStep);
                break;
            }
        }
    }

    private void showIntermediateOutput(int currentStep) {
        System.out.println("-------------------------------");
        System.out.println("Step: " + currentStep);
        System.out.println("State: " + currentState.getState());
        System.out.print("Tape: ");
        tape.displayTape();
        System.out.println("-------------------------------");
    }

    private void showFinishingOutput(int currentStep) {
        System.out.println("-------------------------------");
        if (currentState.isAccepting()) {
            System.out.println("Accepting State reached.");
        } else {
            System.out.println("Invalid State reached.");
        }

        System.out.println("Total Steps: " + currentStep);
        System.out.println("State: " + currentState.getState());
        System.out.print("Tape: ");
        tape.displayTape();
        System.out.println("-------------------------------");
    }
}