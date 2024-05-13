package enums;

public enum Direction {
    LEFT("0"),
    RIGHT("00");

    private final String code;

    Direction(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Direction mapDirection(String code) {
        for (Direction direction : values()) {
            if (direction.getCode().equals(code)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid direction code: " + code);
    }
}