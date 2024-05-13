package models;

import enums.Direction;

import java.util.List;
import java.util.stream.Collectors;

public class Tape {
    private static final char BLANK_SYMBOL = '_';

    private final List<Character> tape;
    private int currentPosition;

    public Tape(String input) {
        this.tape = input.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        this.currentPosition = 0;
    }

    public char read() {
        return tape.get(currentPosition);
    }

    public void write(char symbol) {
        tape.set(currentPosition, symbol);
    }

    public void move(Direction direction) {
        if (direction == Direction.LEFT) {
            if (currentPosition == 0) {
                tape.add(0, BLANK_SYMBOL);
                return;
            }

            currentPosition = currentPosition - 1;
        }

        if (direction == Direction.RIGHT) {
            if (currentPosition == tape.size() - 1) {
                tape.add(BLANK_SYMBOL);
            }

            currentPosition = currentPosition + 1;
        }
    }

    public void displayTape() {
        for (int i = 0; i < tape.size(); i++) {
            if (i == currentPosition) {
                System.out.print("[" + tape.get(i) + "]");
            } else {
                System.out.print(" " + tape.get(i) + " ");
            }
        }
        System.out.println();
    }
}