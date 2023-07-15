package numbers;

import java.util.*;

public class AmazingNumber {
    enum Property {
        EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD,
        _EVEN, _ODD, _BUZZ, _DUCK, _PALINDROMIC, _GAPFUL, _SPY, _SQUARE, _SUNNY, _JUMPING, _HAPPY, _SAD
    }
    public static List<List<String>> mutuallyExclusiveProps;

    static {
        mutuallyExclusiveProps = List.of(
                List.of(Property.EVEN.toString(), Property.ODD.toString()),
                List.of(Property._EVEN.toString(), Property._ODD.toString()),
                List.of(Property.DUCK.toString(), Property.SPY.toString()),
                List.of(Property.SUNNY.toString(), Property.SQUARE.toString()),
                List.of(Property.HAPPY.toString(), Property.SAD.toString()),
                List.of(Property._HAPPY.toString(), Property._SAD.toString()),
                List.of(Property.EVEN.toString(), Property._EVEN.toString()),
                List.of(Property.ODD.toString(), Property._ODD.toString()),
                List.of(Property.BUZZ.toString(), Property._BUZZ.toString()),
                List.of(Property.DUCK.toString(), Property._DUCK.toString()),
                List.of(Property.PALINDROMIC.toString(), Property._PALINDROMIC.toString()),
                List.of(Property.GAPFUL.toString(), Property._GAPFUL.toString()),
                List.of(Property.SPY.toString(), Property._SPY.toString()),
                List.of(Property.SQUARE.toString(), Property._SQUARE.toString()),
                List.of(Property.SUNNY.toString(), Property._SUNNY.toString()),
                List.of(Property.JUMPING.toString(), Property._JUMPING.toString()),
                List.of(Property.HAPPY.toString(), Property._HAPPY.toString()),
                List.of(Property.SAD.toString(), Property._SAD.toString())
                );
    }

    private final Long num;
    Map<String, Boolean> numProps = new HashMap<>();

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
        this.numProps.put(Property.HAPPY.toString(), this.isHappy());
        this.numProps.put(Property.SAD.toString(), !this.isHappy());
    }

//    Boolean isNatural() {
//        return this.num > 0;
//    }

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

    static ArrayList<Integer> numToList(Long num) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Character ch : num.toString().toCharArray()) {
            list.add(Character.getNumericValue(ch));
        }
        return list;
    }
    Boolean isSpy() {
        return numToList(this.num).stream()
                .reduce(0, Integer::sum)
                .equals(numToList(this.num).stream()
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
        Integer prev = numToList(this.num).get(0);
        for (Integer digit : numToList(this.num).stream().skip(1).toList()) {
            if (prev - digit != 1 && digit - prev != 1) {
                return false;
            }
            prev = digit;
        }
        return true;
    }

    Boolean isHappy() {
        List<Integer> listOfDigits = numToList(this.num);
        long sumOfSquareDigits;
        int depth = 1000;
        do {
            sumOfSquareDigits = listOfDigits
                    .stream()
                    .mapToLong(x -> x * x)
                    .sum();
            if (sumOfSquareDigits == 1) {
                return true;
            } else if (sumOfSquareDigits == this.num || sumOfSquareDigits == 4) {
                return false;
            }
            listOfDigits = numToList(sumOfSquareDigits);
            depth--;
        } while (depth > 0);
        return  false;
    }

    static Boolean isProperty(String property) {
        return Arrays.stream(Property.values())
                .map(Enum::name).toList().contains(property.toUpperCase());
    }


    void printPropertiesColumn() {
        String propertiesColumnString = "Properties of %d%neven: %b%nodd: " +
                "%b%nbuzz: %b%nduck: %b%npalindromic: %b%ngapful: %b" +
                "%nspy: %b%nsquare: %b%nsunny: %b%njumping: %b"+
                "%nhappy: %b%nsad: %b%n";
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
                        this.isJumping(),
                        this.isHappy(),
                        !this.isHappy()
                );
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
        if (this.isHappy()) { listOfTrueProps.add(Property.HAPPY.toString().toLowerCase()); }
        else { listOfTrueProps.add(Property.SAD.toString().toLowerCase()); }
        System.out.printf("%d is %s%n", this.num, String.join(", ", listOfTrueProps));
    }
}
