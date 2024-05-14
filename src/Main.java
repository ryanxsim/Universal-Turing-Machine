import java.util.Scanner;

/**
 * 0. Define all Codes.
 * 111 -> separates TM from input
 * 11 -> separates individual transitions
 * 1 -> separates transition parameters
 * 0 -> q1 (start state)
 * 00 -> q2 (accept state)
 * 000 -> q3 etc.
 * <p>
 * Direction:
 * 0  -> L
 * 00 -> R
 * <p>
 * Input Symbols:
 * 0 -> 0
 * 00 -> 1
 * 000  -> next symbol sorted by ascii value
 * <p>
 * 1. Split TM from input
 * 2. Split transitions
 * 3. Classify each input separated by 1 with the corresponding parameter type from the transition definition:
 * t(qi, Xj) = (qk, Xl, Dm)  -> 0i10j10k10l10m mit (i, j, k, l, m ∈ N) (siehe Slides)
 * <p>
 * 4. Group the starting states of the transitions
 */


/**TODO
 * InputAlphabet rework if needed
 * Das korrekte Ergebnis? Eventuell nochmal überprüfen und Unärcodierung Abfrage machen für Quadratzahlen simulation.
 * Die Eingabe vom Input Wort kann als Binärzeichenreihe eingegeben oder oder aus einer Datei eingelesen werden. Order Parser für Die Eingabeparamter mit einer Dezimahlzahl? was ist damit gemeint?
 */

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean continueRunning = true;
        TuringMachine turingMachine;

        System.out.println("Welcome to the Turing Machine Simulator!");

        while (continueRunning) {
            System.out.println("Please enter a Gödel number:");

            String godelNumber = scanner.nextLine();
            TuringMachineBuilder builder = new TuringMachineBuilder(godelNumber);
            try {
                turingMachine = builder.build();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                continue;
            }

            boolean isRunningMode = getUserConfirmation("Do you want to activate Running mode?");
            try {
                turingMachine.run(isRunningMode);
            } catch (InterruptedException e) {
                System.err.println("An error occurred while running the Turing Machine.");
            }

            continueRunning = getUserConfirmation("Do you want to create a new Turing Machine?");
        }

    }

    private static boolean getUserConfirmation(String message) {
        String input;
        do {
            System.out.print(message + " [y/n]: ");
            input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) {
                return true; // default is 'y'
            }
        } while (!input.equals("y") && !input.equals("n"));
        return input.equals("y");
    }
}