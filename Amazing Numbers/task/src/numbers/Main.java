package numbers;

import java.util.List;
import java.util.Scanner;

public class Main {

    public enum Variants {
        PROPER_FIRST_PARAMETER,
        PROPER_SECOND_PARAMETER,
        PROPER_THIRD_PARAMETER,
        PROPER_FOURTH_PARAMETER,
        BAD_FIRST_PARAMETER,
        BAD_SECOND_PARAMETER,
        BAD_THIRD_PARAMETER,
        BAD_FOURTH_PARAMETER,
        BAD_THIRD_AND_FOURTH_PARAMETERS,
        EXIT
    }
    public static Long getNumber(String input) {
        return Long.parseLong(input.split(" ")[0]);
    }

    public static Integer getQty(String input) {
        return Integer.parseInt(input.split(" ")[1]);
    }

    public static String getFirstProperty(String input) {
        return input.split(" ")[2].toUpperCase();
    }

    public static String getSecondProperty(String input) {
        return input.split(" ")[3].toUpperCase();
    }

    public static Boolean isMutuallyExclusiveProperties(String input) {
        String firstProp = getFirstProperty(input);
        String secondProp = getSecondProperty(input);
        for (List<String> list : AmazingNumber.mutiallyExclusiveProps) {
            if (list.contains(firstProp) && list.contains(secondProp)) {
                return true;
            }
        }
        return false;
    }

    public static void printPropertiesByColumn(String input) {
        new AmazingNumber(getNumber(input))
                .printPropertiesColumn();
    }

    public static void printNumbersByQty(String input) {
        AmazingNumber amazingNumber;
        Long number = getNumber(input);
        for (int i = 0; i < getQty(input); i++) {
            amazingNumber = new AmazingNumber(number + i);
            amazingNumber.printPropertiesRow();
        }
    }

    public static void printNumbersByProperty(String input) {
        AmazingNumber amazingNumber;
        Long number = getNumber(input);
        String firstProperty = getFirstProperty(input);
        Integer counter = 0;
        for (int i = 0; i < getQty(input); i++) {
            do {
                amazingNumber = new AmazingNumber(number + counter);
                if (amazingNumber.numProps.get(firstProperty).equals(true)) {
                    amazingNumber.printPropertiesRow();
                    counter++;
                    break;
                }
                counter++;
            } while (true);
        }
    }

    public static void printNumbersByTwoProperties(String input) {
        AmazingNumber amazingNumber;
        Long number = getNumber(input);
        String firstProperty = getFirstProperty(input);
        String secondProperty = getSecondProperty(input);
        Integer counter = 0;
        for (int i = 0; i < getQty(input); i++) {
            do {
                amazingNumber = new AmazingNumber(number + counter);
                if (amazingNumber.numProps.get(firstProperty).equals(true) &&
                        amazingNumber.numProps.get(secondProperty).equals(true)) {
                    amazingNumber.printPropertiesRow();
                    counter++;
                    break;
                }
                counter++;
            } while (true);
        }
    }
    public static Variants check(String inputLine) {
        Variants variant;
        Scanner scanner = new Scanner(inputLine);
        if (scanner.hasNextLong()) { // check the first parameter
            Long inputNumber = scanner.nextLong();
            if (inputNumber == 0) {
                return Variants.EXIT;
            } else if (inputNumber < 0) {
                return Variants.BAD_FIRST_PARAMETER;
            }
        } else {
            return Variants.BAD_FIRST_PARAMETER;
        }

        if (scanner.hasNext()) { // check the second parameter
            if (scanner.hasNextInt()) {
                Integer inputQty = scanner.nextInt();
                if (inputQty < 0) {
                    return Variants.BAD_SECOND_PARAMETER;
                }
            } else {
                return Variants.BAD_SECOND_PARAMETER;
            }
        } else {
            return Variants.PROPER_FIRST_PARAMETER;
        }

        if (scanner.hasNext()) { // check the third parameter
            String inputProperty = scanner.next();
            if (AmazingNumber.isProperty(inputProperty)) {
                variant = Variants.PROPER_THIRD_PARAMETER;
            } else {
                variant = Variants.BAD_THIRD_PARAMETER;
            }
        } else {
            return Variants.PROPER_SECOND_PARAMETER;
        }

        if (scanner.hasNext()) { // check the third parameter
            String inputProperty = scanner.next();
            if (AmazingNumber.isProperty(inputProperty)) {
                if (variant == Variants.BAD_THIRD_PARAMETER) {
                    return Variants.BAD_THIRD_PARAMETER;
                } else {
                    return Variants.PROPER_FOURTH_PARAMETER;
                }
            } else {
                if (variant == Variants.BAD_THIRD_PARAMETER) {
                    return Variants.BAD_THIRD_AND_FOURTH_PARAMETERS;
                } else {
                    return Variants.BAD_FOURTH_PARAMETER;
                }
            }
        } else {
            return variant;
        }
    }

    public static void main(String[] args) {
        String instructions = "Welcome to Amazing Numbers!\n" +
                    "Supported requests:\n" +
                    "- enter a natural number to know its properties; \n" +
                    "- enter two natural numbers to obtain the properties of the list:\n" +
                    "  * the first parameter represents a starting number;\n" +
                    "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                    "- two natural numbers and a property to search for;\n" +
                    "- two natural numbers and two properties to search for;\n" +
                    "- separate the parameters with one space;\n" +
                    "- enter 0 to exit.";
        System.out.println(instructions);
        Scanner scanner = new Scanner(System.in);
        String input;
        AmazingNumber amazingNumber;
        Boolean isExit = false;
        do {
            System.out.print("Enter a request: ");
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
                switch(check(input)) {
                    case EXIT:
                        System.out.println("Goodbye!\nProcess finished with exit code 0");
                        isExit = true;
                        break;
                    case BAD_FIRST_PARAMETER:
                        System.out.println("The first parameter should be a natural number or zero.");
                        break;
                    case BAD_SECOND_PARAMETER:
                        System.out.println("The second parameter should be a natural number.");
                        break;
                    case BAD_THIRD_PARAMETER:
                        System.out.printf("The property [%s] is wrong.\n" +
                                "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]%n",
                                input.split(" ")[2]);
                        break;
                    case BAD_FOURTH_PARAMETER:
                        System.out.printf("The property [%s] is wrong.\n" +
                                "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]%n",
                                input.split(" ")[3]);
                        break;
                    case BAD_THIRD_AND_FOURTH_PARAMETERS:
                        System.out.printf("The properties [%s, %s] are wrong.\n" +
                                "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]%n",
                                input.split(" ")[2],
                                input.split(" ")[3]);
                        break;
                    case PROPER_FIRST_PARAMETER:
                        printPropertiesByColumn(input);
                        break;
                    case PROPER_SECOND_PARAMETER:
                        printNumbersByQty(input);
                        break;
                    case PROPER_THIRD_PARAMETER:
                        printNumbersByProperty(input);
                        break;
                    case PROPER_FOURTH_PARAMETER:
                        if (isMutuallyExclusiveProperties(input)) {
                            System.out.printf("The request contains mutually exclusive properties: [%s, $s]\n" +
                                    "There are no numbers with these properties.",
                                    getFirstProperty(input),
                                    getSecondProperty(input));
                        } else {
                            printNumbersByTwoProperties(input);
                        }
                        break;
                }
            } else {
                System.out.println(instructions);
            }
        } while (!isExit);
        if (scanner != null) scanner.close();
    }
}
