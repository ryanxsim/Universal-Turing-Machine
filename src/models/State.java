package models;

public record State(String identifier){
    private static final char PREFIX_STATE = 'q';
    public State{
        if (identifier == null || identifier.isBlank() || identifier.chars().anyMatch(c -> c != '0')){
            throw new IllegalArgumentException("models.State identifier cannot be null, empty or have any other symbol than '0'.");
        }
    }

    public boolean isStart(){
        return identifier.length() == 1;
    }

    public boolean isAccepting(){
        return identifier.length() == 2;
    }

    public String getState(){
        return PREFIX_STATE + String.valueOf(identifier.length());
    }
}

