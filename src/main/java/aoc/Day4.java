package aoc;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Day4 {

    public static int solvePart1(final String content) {
        return content.lines()
                .map(Day4::parseCard)
                .map(Day4::countMatches)
                .mapToInt(matches -> matches < 1 ? 0 : (int) Math.pow(2, matches - 1))
                .sum();
    }

    public static int solvePart2(final String content) {
        final Card[] cards = content.lines().map(Day4::parseCard).toArray(Card[]::new);
        final int[] multiplier = new int[cards.length];
        Arrays.fill(multiplier, 1);
        for (int i = 0; i < cards.length; i++) {
            final long matches = countMatches(cards[i]);
            for (int j = 1; j <= matches; j++) {
                multiplier[i + j] += multiplier[i];
            }
        }
        return Arrays.stream(multiplier).sum();
    }

    static long countMatches(final Card card) {
        return card.draw().stream().filter(card.winning()::contains).count();
    }

    static Card parseCard(final String line) {
        final String[] cardAndNumbers = line.split(":");
        final int id = Integer.parseInt(cardAndNumbers[0].substring(5).trim());
        final String[] pipeDelimited = cardAndNumbers[1].split("\\|");
        return new Card(id, parseNumbers(pipeDelimited[0]), parseNumbers(pipeDelimited[1]));
    }

    static Set<Integer> parseNumbers(final String line) {
        return Arrays.stream(line.trim().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    record Card(int id, Set<Integer> draw, Set<Integer> winning) {}
}
