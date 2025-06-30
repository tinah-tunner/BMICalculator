package bmicalculator;

import java.util.Scanner;
import java.util.Locale;

public class BmiCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.println("Welcome to the BMI Calculator!");
        System.out.println("This program will help you calculate your Body Mass Index.\n");

        char repeat;

        do {
            System.out.println("Please enter your name:");
            String name = scanner.next();

            int age = getValidAge(scanner);

            int unitChoice = getUnitChoice(scanner);

            double weight = (unitChoice == 1)
                    ? getValidInput(scanner, "Enter your weight in kilograms:", 10, 600)
                    : getValidInput(scanner, "Enter your weight in pounds:", 22, 1300);

            double height = (unitChoice == 1)
                    ? getValidInput(scanner, "Enter your height in meters:", 0.5, 2.5)
                    : getValidInput(scanner, "Enter your height in inches:", 20, 100);

            double bmi = calculateBMI(unitChoice, weight, height);

            System.out.printf("%nHello %s, age %d.%nYour BMI is %.2f%n", name, age, bmi);

            repeat = askToRepeat(scanner);
            System.out.println();

        } while (repeat == 'Y' || repeat == 'y');

        System.out.println("Thank you for using the BMI Calculator. Goodbye!");
        scanner.close();
    }

    public static int getValidAge(Scanner scanner) {
        int age;
        while (true) {
            System.out.println("Please enter your age (1–120):");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                if (age >= 1 && age <= 120) {
                    break;
                } else {
                    System.out.println("Invalid age. Please enter between 1 and 120.");
                }
            } else {
                System.out.println("Invalid input. Enter a valid age.");
                scanner.next();
            }
        }
        return age;
    }

    public static int getUnitChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.println("Choose your preferred unit:");
            System.out.println("1. Metric (kg, m)");
            System.out.println("2. Imperial (lbs, in)");
            System.out.print("Enter 1 or 2: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice == 1 || choice == 2) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
    }

    public static double getValidInput(Scanner scanner, String prompt, double min, double max) {
        double value;
        while (true) {
            System.out.println(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (value >= min && value <= max) {
                    break;
                } else {
                    System.out.printf("Please enter a value between %.2f and %.2f.%n", min, max);
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return value;
    }

    public static double calculateBMI(int unitChoice, double weight, double height) {
        if (unitChoice == 1) {
            return weight / (height * height);
        } else {
            return (703 * weight) / (height * height);
        }
    }

    public static char askToRepeat(Scanner scanner) {
        System.out.println("Would you like to calculate again? (Y/N):");
        String input = scanner.next();
        return input.length() > 0 ? input.charAt(0) : 'N';
    }
}
