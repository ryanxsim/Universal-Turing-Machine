import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static final int MANUAL_INPUT = 1;
    private static final int FILE_INPUT = 2;

    public static void main(String[] args) throws IOException {
        boolean continueRunning = true;
        TuringMachine turingMachine;

        System.out.println("Welcome to the Turing Machine Emulator!");

        while (continueRunning) {
            int choice = getInputChoice();
            try {
                String godelNumber = getGodelNumber(choice);
                TuringMachineBuilder builder = new TuringMachineBuilder(godelNumber);
                turingMachine = builder.build();
            } catch (IOException e) {
                System.err.println("An error occurred trying to read the file.");
                continue;
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

    private static String getGodelNumber(int choice) throws IOException {
        clearScannerBuffer();

        return switch (choice) {
            case MANUAL_INPUT -> getManualInput();
            case FILE_INPUT -> getFileInput();
            default -> throw new IllegalArgumentException("Unexpected value: " + choice);
        };
    }

    private static String getManualInput() {
        boolean validInput = true;
        String input = "";
        do {
            System.out.println("Please enter a Gödel number (binary or integer):");
            input = scanner.nextLine();

            if (input.isEmpty() || input.isBlank()) {
                System.err.println("Invalid Gödel number. Please enter a binary or integer number.");
                clearScannerBuffer();
                validInput = false;
                continue;
            }

            Optional<String> errorMessage = validateInput(input);

            if (errorMessage.isPresent()) {
                System.err.println(errorMessage.get());
                clearScannerBuffer();
                validInput = false;
                continue;
            }

            if (!isBinary(input)) {
                var bigInteger = new BigDecimal(input).toBigInteger();
                input = bigInteger.toString(2);
            }
        } while (!validInput);

        return input;
    }

    private static String getFileInput() throws IOException {
        boolean validFile = true;
        String fileContent = "";
        do {
            System.out.println("Please enter the file path:");
            String filePath = scanner.nextLine();
            Path path = Path.of(filePath);

            if (!Files.exists(path)) {
                System.err.println("File does not exist. Please enter a valid file path.");
                clearScannerBuffer();
                validFile = false;
                continue;
            }

            fileContent = Files.readString(path);

            if (fileContent.isEmpty() || fileContent.isBlank()) {
                System.err.println("File is empty. Please enter a valid file path.");
                validFile = false;
            }

            // Remove line breaks
            fileContent = fileContent.replaceAll("\n", "").replaceAll("\r", "");

            Optional<String> errorMessage = validateInput(fileContent);
            if (errorMessage.isPresent()) {
                System.err.println(errorMessage.get());
                validFile = false;
            }

            if (!isBinary(fileContent)) {
                var bigInteger = new BigDecimal(fileContent).toBigInteger();
                fileContent = bigInteger.toString(2);
            }

        } while (!validFile);

        return fileContent;
    }

    private static int getInputChoice() throws IOException {
        boolean validInput = false;
        int choice = 0;
        while (!validInput) {
            System.out.println("Please choose an input method:");
            System.out.println(MANUAL_INPUT + ". Enter Gödel number manually");
            System.out.println(FILE_INPUT + ". Read Gödel number from file");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.err.println("Invalid input. Please enter a number.");
                clearScannerBuffer();
                continue;
            }

            choice = scanner.nextInt();

            if (choice == MANUAL_INPUT || choice == FILE_INPUT) {
                validInput = true;
            } else {
                System.err.println("Invalid choice. Please enter 1 or 2.");
            }
        }

        return choice;
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

    private static Optional<String> validateInput(String input) {
        // Validate if input is binary
        if (isBinary(input)) {
            return Optional.empty();
        }

        // Validate if input is an integer
        try {
            // Have to use BigDecimal because of number conventions like 2.352503242283226e+252
            new BigDecimal(input);
        } catch (NumberFormatException e) {
            return Optional.of("Invalid Gödel number. Please enter a binary or integer number.");
        }

        return Optional.empty();
    }

    private static boolean isBinary(String input) {
        return input.matches("[01]+");
    }

    private static void clearScannerBuffer() {
        // Idc
        scanner = new Scanner(System.in);
    }
}