package numbers;

import java.util.*;

public class AmazingNumber {
    enum Property {
        BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD
    }
    private Long num;
    Map numProps = new HashMap<String, Boolean>();

//    Integer[] endWith = {0, 2, 4, 6, 8}; // return (Arrays.asList(endWith).contains(this.num % 10));
    private String propertiesColumnString = "Properties of %d%neven: %b%nodd: " +
        "%b%nbuzz: %b%nduck: %b%npalindromic: %b%ngapful: %b%nspy: %b";

    AmazingNumber(Long num) {
        this.num = num;
        this.numProps.put(Property.BUZZ.toString(), this.isBuzz());
        this.numProps.put(Property.DUCK.toString(), this.isDuck());
        this.numProps.put(Property.PALINDROMIC.toString(), this.isPalidromic());
        this.numProps.put(Property.GAPFUL.toString(), this.isGapful());
        this.numProps.put(Property.SPY.toString(), this.isSpy());
        this.numProps.put(Property.EVEN.toString(), this.isEven());
        this.numProps.put(Property.ODD.toString(), !this.isEven());
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
        Integer firstAndLastDigit = Integer.parseInt(numStr.substring(0, 1) +
                numStr.substring(numStr.length() - 1));
        if (numStr.length() > 2 && this.num % firstAndLastDigit == 0) {
            return true;
        } else {
            return false;
        }
    }
    Boolean isPalidromic() {
        return this.num.toString().equals(new StringBuilder(this.num.toString()).reverse().toString());
    }

    ArrayList<Integer> numToList() {
        ArrayList list = new ArrayList<Integer>();
        for (Character ch : this.num.toString().toCharArray()) {
            list.add(Character.getNumericValue(ch));
        }
//        Arrays.stream(this.num.toString().toCharArray()).map(Integer::parseInt).collect(Collectors.toList());
        return list;
    }
    Boolean isSpy() {
        return this.numToList().stream().reduce(0, Integer::sum) ==
                this.numToList().stream().reduce(1, (x, y) -> x * y);
    }

    static Boolean isProperty(String property) {
        return Arrays.stream(Property.values()).map(x -> x.name()).toList().contains(property.toUpperCase());
//        for (Object prop : this.numProps.keySet()) {
//            if (property.toUpperCase().equals(prop)) {
//                flag = true;
//                break;
//            }
//        }
    }


    void printPropertiesColumn() {
        System.out.println(propertiesColumnString.formatted(this.num,
                        this.isEven(),
                        !this.isEven(),
                        this.isBuzz(),
                        this.isDuck(),
                        this.isPalidromic(),
                        this.isGapful(),
                        this.isSpy()));
    }

    void printPropertiesRow() {
        List listOfTrueProps = new ArrayList<String>();
        if (this.isEven()) { listOfTrueProps.add("even"); }
        else { listOfTrueProps.add("odd"); }
        if (this.isPalidromic()) { listOfTrueProps.add("palindromic"); }
        if (this.isDuck()) { listOfTrueProps.add("duck"); }
        if (this.isBuzz()) { listOfTrueProps.add("buzz"); }
        if (this.isGapful()) { listOfTrueProps.add("gapful"); }
        if (this.isSpy()) { listOfTrueProps.add("spy"); }
        System.out.printf("%d is %s%n", this.num, String.join(", ", listOfTrueProps));
    }
}
