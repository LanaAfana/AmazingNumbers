package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AmazingNumber {
    private Long num;
//    Integer[] endWith = {0, 2, 4, 6, 8}; // return (Arrays.asList(endWith).contains(this.num % 10));
    private String propertiesColumn = "Properties of %d%neven: %b%nodd: " +
        "%b%nbuzz: %b%nduck: %b%npalindromic: %b%ngapful: %b";

    AmazingNumber(Long num) {
        this.num = num;
    }

    Long value() {
        return num;
    }

    Boolean isNatural() {
        return this.num > 0;
    }

    Boolean isEven() { return this.num % 2 == 0; }

    Boolean isDivisibleBy7() { return this.num % 7 == 0; }

    Boolean isEndWith7() {
        return this.num % 10 == 7;
    }

    Boolean isDuck() { return this.num.toString().contains("0"); }

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

    void printPropertiesColumn() {
        System.out.println(propertiesColumn.formatted(this.num,
                        this.isEven(),
                        !this.isEven(),
                (this.isDivisibleBy7() || this.isEndWith7()),
                        this.isDuck(),
                        this.isPalidromic(),
                        this.isGapful()));
    }

    void printPropertiesRow() {
        List listOfTrueProps = new ArrayList<String>();
        if (this.isEven()) { listOfTrueProps.add("even"); }
        else { listOfTrueProps.add("odd"); }
        if (this.isPalidromic()) { listOfTrueProps.add("palindromic"); }
        if (this.isDuck()) { listOfTrueProps.add("duck"); }
        if (this.isDivisibleBy7() || this.isEndWith7()) { listOfTrueProps.add("buzz"); }
        if (this.isGapful()) { listOfTrueProps.add("gapful"); }
        System.out.printf("%d is %s%n", this.num, String.join(", ", listOfTrueProps));
    }
}
