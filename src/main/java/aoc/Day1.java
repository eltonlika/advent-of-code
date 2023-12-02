package aoc;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.function.BiFunction;

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

    public static int solvePart1(final String content) {
        return content.lines()
                .mapToInt(line -> getCalibrationValue(line, Day1::getCharacterDigit))
                .sum();
    }

    public static int solvePart2(final String content) {
        return content.lines()
                .mapToInt(line -> getCalibrationValue(line, Day1::getCharacterOrWordDigit))
                .sum();
    }

    static int getCalibrationValue(
            final String line, final BiFunction<String, Integer, Integer> digitGetter) {
        Integer firstDigit = null;
        for (int i = 0; firstDigit == null && i < line.length(); i++) {
            firstDigit = digitGetter.apply(line, i);
        }

        Integer lastDigit = null;
        for (int i = line.length() - 1; lastDigit == null && i >= 0; i--) {
            lastDigit = digitGetter.apply(line, i);
        }

        return (requireNonNull(firstDigit) * 10) + requireNonNull(lastDigit);
    }

    static Integer getCharacterDigit(final String line, final int position) {
        final char c = line.charAt(position);
        return Character.isDigit(c) ? Character.getNumericValue(c) : null;
    }

    static Integer getWordDigit(final String line, final int position) {
        for (final String word : wordToDigitMap.keySet()) {
            if (line.regionMatches(position, word, 0, word.length())) {
                return wordToDigitMap.get(word);
            }
        }
        return null;
    }

    static Integer getCharacterOrWordDigit(final String line, final int position) {
        Integer digit = getCharacterDigit(line, position);
        if (digit == null) {
            digit = getWordDigit(line, position);
        }
        return digit;
    }
}
