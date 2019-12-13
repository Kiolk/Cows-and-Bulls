package com.github.kiolk.cowsandbulls.utils;

import java.util.Random;

/**
 * Util class for generate random number, validate input, calculate number bulls and cows in entered number.
 */
public class NumberUtil {

    private static final int NUMBER_TO_9 = 10;

    /**
     * Generate Random number for game by specific rules. Digits not repeated and possible start from 0.
     * @param length of generated random number.
     * @return String representation random number without repeated digits.
     */
    public static String generateRandom(int length) {
        int[] randomNumber = new int[10];
        StringBuilder result = new StringBuilder();

        for (int i = 0, j; i < length; ++i) {
            Random r = new Random();
            int k = r.nextInt(NUMBER_TO_9);
            if (i == 0) {
                randomNumber[i] = k;
            } else {
                for (j = 0; j < i; ++j) {
                    if (randomNumber[j] == k) {
                        --i;
                        break;
                    } else {
                        randomNumber[i] = k;
                    }
                }
            }
        }

        for (int i = 0; i < length; ++i) {
            result.append(randomNumber[i]);
        }

        return result.toString();
    }

    /**
     * Calculate how many numbers stay on "Bull" position. Stay on same position in coded number.
     * @param codedNumber number was coded
     * @param enteredNumber user inputted number
     * @return number of bulls in entered number
     */
    public static int getBullsNumber(String codedNumber, String enteredNumber) {
        int numberOfBulls = 0;
        char[] codedNumberArray = codedNumber.toCharArray();
        char[] enteredNumberArray = enteredNumber.toCharArray();

        for (int i = 0; i < codedNumber.length(); ++i) {
            if (codedNumberArray[i] == enteredNumberArray[i]) {
                ++numberOfBulls;
            }
        }

        return numberOfBulls;
    }

    /**
     * Calculate how many numbers stay on "Cow" position. Stay on same position in coded number.
     * @param codedNumber number was coded
     * @param enteredNumber user inputted number
     * @return number of cows in entered number
     */
    public static int getCowsNumber(String codedNumber, String enteredNumber) {
        int numberOfCows = 0;
        char[] codedNumberArray = codedNumber.toCharArray();
        char[] enteredNumberArray = enteredNumber.toCharArray();
        int lengthOfNumber = codedNumberArray.length;

        for (int i = 0; i < lengthOfNumber; ++i) {
            for (int j = 0; j < lengthOfNumber; ++j) {
                if (codedNumberArray[i] == enteredNumberArray[j] && i != j) {
                    ++numberOfCows;
                }
            }
        }

        return numberOfCows;
    }

    /**
     * Check correctness user inputted number
     * @param enteredNumber user inputted number
     * @param length length of coded number
     * @return true if number correct by game rule, false - if number incorrect by some reason
     */
    public static boolean checkCorrectInput(String enteredNumber, int length) {

        if (enteredNumber.length() == length) {

            for (int i = 0; i < length; ++i) {
                if (enteredNumber.charAt(i) < '0' || enteredNumber.charAt(i) > '9') {
                    return false;
                }
            }

            int numberForChecking = Integer.parseInt(enteredNumber);
            int k = 1;

            for (int i = 0; i < length - 1; ++i) {
                k = k * 10;
            }

            if (numberForChecking >= k) {
                int[] numberArray;
                numberArray = new int[length];
                for (int i = length - 1; i >= 0; --i) {
                    numberArray[i] = numberForChecking % 10;
                    numberForChecking = numberForChecking / 10;
                }
                for (int i = 0, cnt = 0; i < length; ++i) {
                    for (int j = 0; j < length; ++j) {
                        if (numberArray[i] == numberArray[j]) {
                            ++cnt;
                        }
                        if (cnt > length) {
                            return false;
                        }
                    }
                }

                return true;

            } else if (enteredNumber.charAt(0) == '0' && numberForChecking >= k / 10) {
                int[] numberArray;
                numberArray = new int[length];

                for (int i = length - 1; i >= 1; --i) {
                    numberArray[i] = numberForChecking % 10;
                    numberForChecking = numberForChecking / 10;
                }

                for (int i = 0, cnt = 0; i < length; ++i) {
                    for (int j = 0; j < length; ++j) {
                        if (numberArray[i] == numberArray[j]) {
                            ++cnt;
                        }
                        if (cnt > length) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }
}
