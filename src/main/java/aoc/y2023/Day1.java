package aoc.y2023;

import static java.util.Objects.requireNonNull;

import aoc.AoC;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Day1 implements AoC {

    private static final Map<String, Integer> wordToDigitMap =
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

    private List<String> lines;

    @Override
    public void load(String input) {
        this.lines = input.lines().toList();
    }

    @Override
    public long getPart1Solution() {
        return this.lines.stream()
                .mapToLong(line -> getCalibrationValue(line, this::getCharacterDigit))
                .sum();
    }

    @Override
    public long getPart2Solution() {
        return this.lines.stream()
                .mapToLong(line -> getCalibrationValue(line, this::getCharacterOrWordDigit))
                .sum();
    }

    private int getCalibrationValue(
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

    private Integer getCharacterDigit(final String line, final int position) {
        final char c = line.charAt(position);
        return Character.isDigit(c) ? Character.getNumericValue(c) : null;
    }

    private Integer getWordDigit(final String line, final int position) {
        return wordToDigitMap.keySet().stream()
                .filter(word -> line.regionMatches(position, word, 0, word.length()))
                .findFirst()
                .map(wordToDigitMap::get)
                .orElse(null);
    }

    private Integer getCharacterOrWordDigit(final String line, final int position) {
        final Integer charDigit = getCharacterDigit(line, position);
        return charDigit != null ? charDigit : getWordDigit(line, position);
    }
}
