package numbers;

import java.util.*;

public class AmazingNumber {
    enum Property {
        EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING
    }
    public static List<List<String>> mutiallyExclusiveProps = List.of(
            List.of(Property.EVEN.toString(), Property.ODD.toString()),
            List.of(Property.DUCK.toString(), Property.SPY.toString()),
            List.of(Property.SUNNY.toString(), Property.SQUARE.toString()));
    private final Long num;
    Map<String, Boolean> numProps = new HashMap<String, Boolean>();

    AmazingNumber(Long num) {
        this.num = num;
        this.numProps.put(Property.BUZZ.toString(), this.isBuzz());
        this.numProps.put(Property.DUCK.toString(), this.isDuck());
        this.numProps.put(Property.PALINDROMIC.toString(), this.isPalidromic());
        this.numProps.put(Property.GAPFUL.toString(), this.isGapful());
        this.numProps.put(Property.SPY.toString(), this.isSpy());
        this.numProps.put(Property.EVEN.toString(), this.isEven());
        this.numProps.put(Property.ODD.toString(), !this.isEven());
        this.numProps.put(Property.SQUARE.toString(), this.isSquare());
        this.numProps.put(Property.SUNNY.toString(), this.isSunny());
        this.numProps.put(Property.JUMPING.toString(), this.isJumping());
    }

    Long value() {
        return num;
    }

    Boolean isNatural() {
        return this.num > 0;
    }

    Boolean isEven() { return this.num % 2 == 0; }

    Boolean isDuck() { return this.num.toString().contains("0"); }

    Boolean isBuzz() { return (this.num % 7 == 0) || (this.num % 10 == 7); }

    Boolean isGapful() {
        String numStr = this.num.toString();
        Integer firstAndLastDigit = Integer.parseInt(numStr.charAt(0) +
                numStr.substring(numStr.length() - 1));
        return numStr.length() > 2 && this.num % firstAndLastDigit == 0;
    }
    Boolean isPalidromic() {
        return this.num.toString().contentEquals(new StringBuilder(this.num.toString()).reverse());
    }

    ArrayList<Integer> numToList() {
        ArrayList<Integer> list = new ArrayList();
        for (Character ch : this.num.toString().toCharArray()) {
            list.add(Character.getNumericValue(ch));
        }
//        Arrays.stream(this.num.toString().toCharArray()).map(Integer::parseInt).collect(Collectors.toList());
        return list;
    }
    Boolean isSpy() {
        return this.numToList().stream()
                .reduce(0, Integer::sum)
                .equals(this.numToList().stream()
                        .reduce(1, (x, y) -> x * y));
    }

    Boolean isSquare() {
        boolean flag = false;
        for (long i = Math.round(Math.sqrt(this.num) + 2); i > Math.round(Math.sqrt(this.num)) - 1; i--) {
            if (i * i == this.num) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    Boolean isSunny() {
        boolean flag = false;
        for (long i = Math.round(Math.sqrt(this.num)) + 1; i > Math.round(Math.sqrt(this.num)) - 1; i--) {
            if (i * i == this.num + 1) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    Boolean isJumping() {
//        return this.numToList().stream().allMatch((x, y) -> (x. - y == 1) || (y - x == 1))
        Integer prev = this.numToList().get(0);
        for (Integer digit : this.numToList().stream().skip(1).toList()) {
            if (prev - digit != 1 && digit - prev != 1) {
                return false;
            }
            prev = digit;
        }
        return true;
    }

    static Boolean isProperty(String property) {
        return Arrays.stream(Property.values())
                .map(Enum::name).toList().contains(property.toUpperCase());
    }


    void printPropertiesColumn() {
        //    Integer[] endWith = {0, 2, 4, 6, 8}; // return (Arrays.asList(endWith).contains(this.num % 10));
        String propertiesColumnString = "Properties of %d%neven: %b%nodd: " +
                "%b%nbuzz: %b%nduck: %b%npalindromic: %b%ngapful: %b" +
                "%nspy: %b%nsquare: %b%nsunny: %b%njumping: %b%n";
        System.out.printf(propertiesColumnString, this.num,
                        this.isEven(),
                        !this.isEven(),
                        this.isBuzz(),
                        this.isDuck(),
                        this.isPalidromic(),
                        this.isGapful(),
                        this.isSpy(),
                        this.isSquare(),
                        this.isSunny(),
                        this.isJumping());
    }

    void printPropertiesRow() {
        List<String> listOfTrueProps = new ArrayList<>();
        if (this.isEven()) { listOfTrueProps.add(Property.EVEN.toString().toLowerCase()); }
        else { listOfTrueProps.add(Property.ODD.toString().toLowerCase()); }
        if (this.isPalidromic()) { listOfTrueProps.add(Property.PALINDROMIC.toString().toLowerCase()); }
        if (this.isDuck()) { listOfTrueProps.add(Property.DUCK.toString().toLowerCase()); }
        if (this.isBuzz()) { listOfTrueProps.add(Property.BUZZ.toString().toLowerCase()); }
        if (this.isGapful()) { listOfTrueProps.add(Property.GAPFUL.toString().toLowerCase()); }
        if (this.isSpy()) { listOfTrueProps.add(Property.SPY.toString().toLowerCase()); }
        if (this.isSquare()) { listOfTrueProps.add(Property.SQUARE.toString().toLowerCase()); }
        if (this.isSunny()) { listOfTrueProps.add(Property.SUNNY.toString().toLowerCase()); }
        if (this.isJumping()) { listOfTrueProps.add(Property.JUMPING.toString().toLowerCase()); }
        System.out.printf("%d is %s%n", this.num, String.join(", ", listOfTrueProps));
    }
}
