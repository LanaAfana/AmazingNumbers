package numbers;

import java.util.Scanner;

public class Main {

    public static void printNumbersByProperty(Long number, Integer qty, String property) {
        AmazingNumber amazingNumber;
        Integer counter = 0;
        for (int i = 0; i < qty; i++) {
            do {
                amazingNumber = new AmazingNumber(number + counter);
                if (amazingNumber.numProps.get(property.toUpperCase()).equals(true)) {
                    amazingNumber.printPropertiesRow();
                    counter++;
                    break;
                }
                counter++;
            } while (true);
        }
    }

    public static void printNumbersByQty(Long num, Integer qty) {
        AmazingNumber amazingNumber;
        for (int i = 0; i < qty; i++) {
            amazingNumber = new AmazingNumber(num + i);
            amazingNumber.printPropertiesRow();
        }
    }

    public static void main(String[] args) {
        String instructions = "Welcome to Amazing Numbers!\n" +
            "Supported requests:\n" +
            "- enter a natural number to know its properties;\n" +
            "- enter two natural numbers to obtain the properties of the list:\n" +
            "  * the first parameter represents a starting number;\n" +
            "  * the second parameters show how many consecutive numbers are to be processed;\n" +
            "- two natural numbers and a property to search for;\n" +
            "- separate the parameters with one space;\n" +
            "- enter 0 to exit.";
        System.out.println(instructions);
        Scanner scanner = new Scanner(System.in);
        String input;
        Long inputNumber = -1L;
        Integer qty;
        String property;
        do {
            System.out.print("Enter a request: ");
            if (scanner.hasNextLine()) {
                if (scanner.hasNextLong()) {
                    input = scanner.nextLine();
                    inputNumber = Long.parseLong(input.split(" ")[0]);
                    if (inputNumber == 0) {
                        System.out.println("Goodbye!\nProcess finished with exit code 0");
                    } else {
                        if (inputNumber > 0) {
                            AmazingNumber amazingNumber;
                            switch (input.split(" ").length) {
                                case 1:
                                    amazingNumber = new AmazingNumber(inputNumber);
                                    amazingNumber.printPropertiesColumn();
                                    break;
                                case 2:
                                    qty = Integer.parseInt(input.split(" ")[1]);
                                    if (qty > 0) {
                                        printNumbersByQty(inputNumber, qty);
                                    } else {
                                        System.out.println("The second parameter should be a natural number.");
                                    }
                                    break;
                                case 3:
                                    qty = Integer.parseInt(input.split(" ")[1]);
                                    property = input.split(" ")[2];
                                    if (qty < 1) {
                                        System.out.println("The second parameter should be a natural number.");
                                        break;
                                    }
                                    if (!AmazingNumber.isProperty(property)) {
                                        System.out.printf("The property [%s] is wrong.\n" +
                                                "Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD]%n", property);
                                        break;
                                    }
                                    printNumbersByProperty(inputNumber, qty, property);
                                    break;
                                default:
                            }
                        } else {
                            System.out.println("The first parameter should be a natural number or zero.");
                        }
                    }
                } else {
                    System.out.println("The first parameter should be a natural number or zero.");
                }
            } else {
                System.out.println(instructions);
            }
        } while (inputNumber != 0);
        if (scanner != null) scanner.close();
    }
}
