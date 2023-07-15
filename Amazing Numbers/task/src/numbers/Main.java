package numbers;

import java.util.*;

public class Main {

    public static List<String> badProps = new ArrayList<>();
    public static List<String> mutuallyExclProps = new ArrayList<>();

    public enum Variants {
        PROPER_NUMBER,
        PROPER_QTY,
        PROPER_PROPERTIES,
        BAD_NUMBER,
        BAD_QTY,
        BAD_PROPERTIES,
        MUTUALLY_EXCLUSIVE_PROPERTIES,
        EXIT
    }
    public static Long getNumber(String input) {
        return Long.parseLong(input.split(" ")[0]);
    }

    public static Integer getQty(String input) {
        return Integer.parseInt(input.split(" ")[1]);
    }

    public static List<String> getProperties(String input) {
        return Arrays.stream(input.split(" "))
                .skip(2)
                .map(x -> x.replace("-", "_"))
                .map(String::toUpperCase).toList();
    }

    public static Boolean isMutuallyExclusiveProperties(List<String> props) {
        for (List<String> list : AmazingNumber.mutuallyExclusiveProps) {
            if (props.contains(list.get(0)) && props.contains(list.get(1))) {
                mutuallyExclProps.addAll(list);
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
        long number = getNumber(input);
        for (int i = 0; i < getQty(input); i++) {
            amazingNumber = new AmazingNumber(number + i);
            amazingNumber.printPropertiesRow();
        }
    }

    public static void printNumbersByProperties(String input) {
        AmazingNumber amazingNumber;
        Long number = getNumber(input);
        List<String> properties = getProperties(input);
        Integer counter = 0;
        boolean isAllProps = true;
        String negProp;
        for (int i = 0; i < getQty(input); i++) {
            do {
                amazingNumber = new AmazingNumber(number + counter);
                for (String prop : properties) {
                    if (prop.charAt(0) == '_') {
                        negProp = prop.replace("_", "");
                        if (amazingNumber.numProps.get(negProp).equals(true)) {
                            isAllProps = false;
                            break;
                        }
                    } else {
                        if (amazingNumber.numProps.get(prop).equals(false)) {
                            isAllProps = false;
                            break;
                        }
                    }
                }
                counter++;
                if (isAllProps) {
                    amazingNumber.printPropertiesRow();
                    break;
                }
                isAllProps = true;
            } while (i < getQty(input));
        }
    }

    public static Variants check(String input) {
        Scanner scanner = new Scanner(input);
        if (scanner.hasNextLong()) { // check the first parameter
            long inputNumber = scanner.nextLong();
            if (inputNumber == 0) {
                return Variants.EXIT;
            } else if (inputNumber < 0) {
                return Variants.BAD_NUMBER;
            }
        } else {
            return Variants.BAD_NUMBER;
        }

        if (scanner.hasNext()) { // check the second parameter
            if (scanner.hasNextInt()) {
                int inputQty = scanner.nextInt();
                if (inputQty < 0) {
                    return Variants.BAD_QTY;
                }
            } else {
                return Variants.BAD_QTY;
            }
            if (!scanner.hasNext()) { return Variants.PROPER_QTY; }
        } else {
            return Variants.PROPER_NUMBER;
        }

        List<String> properties = getProperties(input);
        for (String prop : properties) {
            if (!AmazingNumber.isProperty(prop)) {
                badProps.add(prop);
            }
        }
        if (badProps.isEmpty()) {
            if (isMutuallyExclusiveProperties(properties)) {
                return Variants.MUTUALLY_EXCLUSIVE_PROPERTIES;
            } else {
                return Variants.PROPER_PROPERTIES;
            }
        } else {
            return Variants.BAD_PROPERTIES;
        }
    }

    public static void main(String[] args) {
        String instructions = """
                Welcome to Amazing Numbers!
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be processed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.""";
        System.out.println(instructions);
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean isExit = false;
        do {
            System.out.print("Enter a request: ");
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
                switch (check(input)) {
                    case EXIT -> {
                        System.out.println("Goodbye!\nProcess finished with exit code 0");
                        isExit = true;
                    }
                    case BAD_NUMBER -> System.out.println("The first parameter should be a natural number or zero.");
                    case BAD_QTY -> System.out.println("The second parameter should be a natural number.");
                    case BAD_PROPERTIES -> {
                        if (badProps.size() == 1) {
                            System.out.printf("The property [%s] is wrong.\n" +
                                            "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]%n",
                                    badProps.get(0));
                        } else {
                            System.out.printf("The properties [%s] are wrong.\n" +
                                            "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]%n",
                                    badProps);
                        }
                    }
                    case MUTUALLY_EXCLUSIVE_PROPERTIES ->
                            System.out.printf("The request contains mutually exclusive properties: [%s]\n" +
                                            "There are no numbers with these properties.",
                                    mutuallyExclProps.toString());
                    case PROPER_NUMBER -> printPropertiesByColumn(input);
                    case PROPER_QTY -> printNumbersByQty(input);
                    case PROPER_PROPERTIES -> printNumbersByProperties(input);
                }
            } else {
                System.out.println(instructions);
            }
        } while (!isExit);
        scanner.close();
    }
}
