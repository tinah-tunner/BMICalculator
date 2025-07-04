
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class BMICalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        // Ask for name, surname, date, and time
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();

        System.out.print("Enter your age: ");
        int age = getValidAge(scanner);

        System.out.print("Enter your gender (M/F): ");
        char gender = getValidGender(scanner);

        System.out.print("Enter current date (yyyy-MM-dd): ");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.print("Enter current time (HH:mm): ");
        String timeInput = scanner.nextLine();
        LocalTime time = LocalTime.parse(timeInput, DateTimeFormatter.ofPattern("HH:mm"));

        System.out.println("\nHello, " + name + " " + surname);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println();

        char repeat;

        do {
            int unitChoice = getUnitChoice(scanner);

            double weight = (unitChoice == 1)
                    ? getValidInput(scanner, "Enter your weight in kilograms: ", 10, 600)
                    : getValidInput(scanner, "Enter your weight in pounds: ", 22, 1300);

            double height = (unitChoice == 1)
                    ? getValidInput(scanner, "Enter your height in meters: ", 0.5, 2.5)
                    : getValidInput(scanner, "Enter your height in inches: ", 20, 100);

            double bmi = calculateBMI(unitChoice, weight, height);
            System.out.printf("Your BMI is: %.2f\n", bmi);

            // Save results to file
            try {
                saveResults(name, surname, age, gender, bmi, date, time);
                System.out.println("Results saved to bmi_results.txt");
            } catch (IOException e) {
                System.out.println("Error saving results: " + e.getMessage());
            }

            repeat = askToRepeat(scanner);
            scanner.nextLine(); // Clear buffer after char input
            System.out.println();

        } while (repeat == 'Y' || repeat == 'y');

        System.out.println("Thank you for using the BMI Calculator!");
    }

    // Validate age input
    public static int getValidAge(Scanner scanner) {
        int age;
        while (true) {
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (age > 0 && age < 120) {
                    return age;
                } else {
                    System.out.print("Please enter a valid age between 1 and 120: ");
                }
            } else {
                System.out.print("Invalid input. Please enter a numeric age: ");
                scanner.next(); // clear invalid input
            }
        }
    }

    // Validate gender input
    public static char getValidGender(Scanner scanner) {
        char gender;
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.length() == 1) {
                gender = input.charAt(0);
                if (gender == 'M' || gender == 'F') {
                    return gender;
                }
            }
            System.out.print("Invalid input. Please enter M or F: ");
        }
    }

    // Unit choice
    public static int getUnitChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.println("Select a preferred unit:");
            System.out.println("1. Metric (kg, m)");
            System.out.println("2. Imperial (lbs, in)");
            System.out.print("Please select either option 1 or option 2: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 1 || choice == 2) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter either 1 or 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                scanner.next(); // clear invalid input
            }
        }
    }

    // Input validation
    public static double getValidInput(Scanner scanner, String prompt, double min, double max) {
        double value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("Please enter a value between %.2f and %.2f%n", min, max);
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // clear invalid input
            }
        }
    }

    // BMI calculation
    public static double calculateBMI(int unitChoice, double weight, double height) {
        if (unitChoice == 1) {
            return weight / (height * height); // Metric
        } else {
            return (703 * weight) / (height * height); // Imperial
        }
    }

    // Ask to repeat
    public static char askToRepeat(Scanner scanner) {
        System.out.print("Do you want to calculate again? (Y/N): ");
        char repeat = scanner.next().charAt(0);
        while (repeat != 'Y' && repeat != 'y' && repeat != 'N' && repeat != 'n') {
            System.out.print("Invalid input. Please enter Y or N: ");
            repeat = scanner.next().charAt(0);
        }
        return repeat;
    }

    // Save results to file
    public static void saveResults(String name, String surname, int age, char gender, double bmi,
                                   LocalDate date, LocalTime time) throws IOException {
        try (FileWriter writer = new FileWriter("bmi_results.txt", true)) {  // append mode
            writer.write("Name: " + name + " " + surname + "\n");
            writer.write("Age: " + age + "\n");
            writer.write("Gender: " + gender + "\n");
            writer.write(String.format("BMI: %.2f\n", bmi));
            writer.write("Date: " + date + "\n");
            writer.write("Time: " + time + "\n");
            writer.write("-----------------------------\n");
        }
    }
}
