package models;

import enums.Direction;

public record Transition(State from, char inputSymbol, State to, char outputSymbol, Direction direction) {
    public Transition {
        if (from == null || to == null || direction == null) {
            throw new IllegalArgumentException("models.Transition states and direction cannot be null.");
        }
    }
}
