package aoc;

import static java.util.Objects.requireNonNull;

import java.util.Map;

public class Day1 {

    static final Map<String, Integer> wordToDigitMap =
            Map.of(
                    "one", 1,
                    "two", 2,
                    "three", 3,
                    "four", 4,
                    "five", 5,
                    "six", 6,
                    "seven", 7,
                    "eight", 8,
                    "nine", 9);

    public static int solve(final String content) {
        return content.lines().mapToInt(Day1::getCalibrationValue).sum();
    }

    static int getCalibrationValue(final String line) {
        Integer firstDigit = null;
        for (int i = 0; firstDigit == null && i < line.length(); i++) {
            firstDigit = getDigit(line, i);
        }

        Integer lastDigit = null;
        for (int i = line.length() - 1; lastDigit == null && i >= 0; i--) {
            lastDigit = getDigit(line, i);
        }

        return (requireNonNull(firstDigit) * 10) + requireNonNull(lastDigit);
    }

    static Integer getDigit(final String line, final int position) {
        // if current character represents a digit then return the digit value
        final char c = line.charAt(position);
        if (Character.isDigit(c)) {
            return Character.getNumericValue(c);
        }

        // if current position has a word then return corresponding digit
        for (final String word : wordToDigitMap.keySet()) {
            if (line.regionMatches(position, word, 0, word.length())) {
                return wordToDigitMap.get(word);
            }
        }

        return null;
    }
}
