package models;

import enums.Direction;

import java.util.List;
import java.util.stream.Collectors;

public class Tape {
    private static final char BLANK_SYMBOL = '_';
    public static final int TAPE_HEAD_OFFSET = 15;

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
        int tapeStart = Math.max(0, currentPosition - TAPE_HEAD_OFFSET);
        int tapeEnd = Math.min(tape.size(), currentPosition + TAPE_HEAD_OFFSET);

        // If there are fewer than 15 elements before the current position, fill the remaining spaces with '_'
        for (int i = currentPosition - TAPE_HEAD_OFFSET; i < tapeStart; i++) {
            System.out.print(" _ ");
        }

        // Display 15 elements before and after the current position
        for (int i = tapeStart; i < tapeEnd; i++) {
            if (i == currentPosition) {
                System.out.print("[" + tape.get(i) + "]");
            } else {
                System.out.print(" " + tape.get(i) + " ");
            }
        }

        // If there are fewer than 15 elements after the current position, fill the remaining spaces with '_'
        for (int i = tapeEnd; i < currentPosition + TAPE_HEAD_OFFSET + 1; i++) {
            System.out.print(" _ ");
        }

        System.out.println();
    }
}