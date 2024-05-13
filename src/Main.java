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

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a Gödel number:");
        String godelNumber = scanner.nextLine();
        TuringMachineBuilder builder = new TuringMachineBuilder(godelNumber);
        try {
           TuringMachine turingMachine = builder.build();
             turingMachine.run();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}