package numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String instructions = "Welcome to Amazing Numbers!\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.";
        System.out.println(instructions);
        Scanner scanner = new Scanner(System.in);
        String input;
        Long inputNumber = -1L;
        Integer qty;
        do {
            System.out.print("Enter a request: ");
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
                inputNumber = Long.parseLong(input.split(" ")[0]);
                if (inputNumber == 0) {
                    System.out.println("Goodbye!");
                } else {
                    if (inputNumber > 0) {
                        if (input.split(" ").length > 1) {
                            qty = Integer.parseInt(input.split(" ")[1]);
                            if (qty > 0) {
                                for (int i = 0; i < qty; i++) {
                                    AmazingNumber amazingNumber = new AmazingNumber(inputNumber + i);
                                    amazingNumber.printPropertiesRow();
                                }
                            } else {
                                System.out.println("The second parameter should be a natural number.");
                            }
                        } else {
                            AmazingNumber amazingNumber = new AmazingNumber(inputNumber);
                            amazingNumber.printPropertiesColumn();
                        }
                    } else {
                        System.out.println("The first parameter should be a natural number or zero.");
                    }
                }
            } else {
                System.out.println(instructions);
            }
        } while (inputNumber != 0);
        if (scanner != null) scanner.close();
    }
}
