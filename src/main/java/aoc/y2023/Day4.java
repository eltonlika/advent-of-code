package aoc.y2023;

import static aoc.util.Util.parseNumbers;

import aoc.AoC;

import java.util.Arrays;
import java.util.List;

public class Day4 implements AoC {

    private Card[] cards;

    @Override
    public void load(String input) {
        this.cards = input.lines().map(Card::parse).toArray(Card[]::new);
    }

    @Override
    public long getPart1Solution() {
        long sum = 0L;
        for (final Card card : this.cards) {
            final long matches = card.countMatches();
            sum += matches < 1 ? 0L : (long) Math.pow(2, matches - 1);
        }
        return sum;
    }

    @Override
    public long getPart2Solution() {
        long sum = 0L;
        final long[] cardCount = Arrays.stream(this.cards).mapToLong(c -> 1L).toArray();
        for (int i = 0; i < this.cards.length; i++) {
            sum += cardCount[i];
            final long matches = this.cards[i].countMatches();
            for (int j = 1; j <= matches; j++) {
                cardCount[i + j] += cardCount[i];
            }
        }
        return sum;
    }

    private record Card(long id, List<Long> draw, List<Long> winning) {

        static Card parse(final String line) {
            final String[] cardAndNumbers = line.split(":");
            final String[] pipeDelimited = cardAndNumbers[1].split("\\|");
            return new Card(
                    Long.parseLong(cardAndNumbers[0].substring(5).trim()),
                    parseNumbers(pipeDelimited[0], " ").boxed().toList(),
                    parseNumbers(pipeDelimited[1], " ").boxed().toList());
        }

        long countMatches() {
            return draw().stream().filter(winning()::contains).count();
        }
    }
}
