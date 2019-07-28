package com.baeldung.binarynumbers;

public class BinaryNumbers {

    /**
     * This method takes a decimal number and convert it into a binary number.
     * example:- input:10, output:1010
     *
     * @param decimalNumber
     * @return binary number
     */
    public Integer convertDecimalToBinary(Integer decimalNumber) {

        if (decimalNumber == 0) {
            return decimalNumber;
        }

        // this will hold the final binary number
        StringBuilder binaryNumber = new StringBuilder();

        while (decimalNumber > 0) {

            int remainder = decimalNumber % 2;
            int result = decimalNumber / 2;

            binaryNumber.append(remainder);
            decimalNumber = result;
        }

        // reversing the binary string
        binaryNumber = binaryNumber.reverse();

        // converting string to integer
        return Integer.valueOf(binaryNumber.toString());
    }

    /**
     * This method takes a binary number and convert it into a decimal number.
     * example:- input:101, output:5
     *
     * @param binary number
     * @return decimal Number
     */
    public Integer convertBinaryToDecimal(Integer binaryNumber) {

        // final decimal number
        Integer result = 0;
        Integer base = 1;

        while (binaryNumber > 0) {

            int lastDigit = binaryNumber % 10;
            binaryNumber = binaryNumber / 10;

            // multiplying last digit with base
            // and adding last digit's decimal value
            result += lastDigit * base;

            // Increasing base value for next loop..
            // base goes as 2^0,2^1,2^2,2^3...
            base = base * 2;
        }
        return result;
    }

    /**
     * This method accepts two binary numbers and returns sum of input numbers.
     * Example:- firstNum: 101, secondNum: 100, output: 1001
     *
     * @param firstNum
     * @param secondNum
     * @return addition of input numbers
     */
    public Integer addBinaryNumber(Integer firstNum, Integer secondNum) {

        // variable to store addition
        StringBuilder output = new StringBuilder();

        int carry = 0;
        int temp;

        while (firstNum != 0 || secondNum != 0) {

            // adding last digit from each number
            temp = (firstNum % 10 + secondNum % 10 + carry) % 2;
            output.append(temp);

            // calculate the carry generated by sum of two digit
            carry = (firstNum % 10 + secondNum % 10 + carry) / 2;

            firstNum = firstNum / 10;
            secondNum = secondNum / 10;
        }

        // putting final carry in the end
        if (carry != 0) {
            output.append(carry);
        }

        // reversing the string and converting it into an Integer
        return Integer.valueOf(output.reverse()
            .toString());
    }

    /**
    * This method takes two binary number as input and subtract second number from the first number.
    * example:- firstNum: 1000, secondNum: 11, output: 101
    * @param firstNum
    * @param secondNum
    * @return Result of subtraction of secondNum from first
    */
    public Integer substractBinaryNumber(Integer firstNum, Integer secondNum) {

        // Step 1: one's complement of subtrahend
        int onesComplement = Integer.valueOf(onesComplement(secondNum));

        // variable to store addition
        StringBuilder output = new StringBuilder();

        int carry = 0;
        int temp;

        // step 2: addition of one's complement of subtrahend and minuend
        while (firstNum != 0 || onesComplement != 0) {

            // adding last digit from each number
            temp = (firstNum % 10 + onesComplement % 10 + carry) % 2;
            output.append(temp);

            // calculate the carry generated by sum of two digit
            carry = (firstNum % 10 + onesComplement % 10 + carry) / 2;

            firstNum = firstNum / 10;
            onesComplement = onesComplement / 10;
        }

        String additionOfFirstNumAndOnesComplement = output.reverse()
            .toString();

        if (carry == 1) {
            // step 3: if carry is present, then add carry to the result of step 2
            return addBinaryNumber(Integer.valueOf(additionOfFirstNumAndOnesComplement), carry);
        } else {
            // step 3: if carry is not present, 1's complement of the result of step 2 is the final result
            return onesComplement(Integer.valueOf(additionOfFirstNumAndOnesComplement));
        }

    }

    public Integer onesComplement(Integer num) {

        StringBuilder onesComplement = new StringBuilder();

        while (num > 0) {
            int lastDigit = num % 10;
            if (lastDigit == 0) {
                onesComplement.append(1);
            } else {
                onesComplement.append(0);
            }
            num = num / 10;
        }

        return Integer.valueOf(onesComplement.reverse()
            .toString());
    }

}
