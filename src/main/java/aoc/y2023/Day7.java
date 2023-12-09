package aoc.y2023;

import static java.util.stream.Collectors.*;

import aoc.AoC;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

public class Day7 implements AoC {

    private static final char[] CARDS_ORDER1 =
            new char[] {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    private static final char[] CARDS_ORDER2 =
            new char[] {'J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A'};

    private List<String[]> lines;

    private int getCardStrength(final char[] cardsOrder, final char card) {
        return IntStream.range(0, cardsOrder.length)
                .filter(i -> cardsOrder[i] == card)
                .findFirst()
                .orElse(-1);
    }

    private Comparator<Hand> getHandComparator(final char[] cardsOrder) {
        return (h1, h2) -> {
            final int typeDiff = h1.type().ordinal() - h2.type().ordinal();
            if (typeDiff != 0) {
                return typeDiff;
            }
            for (int i = 0; i < h1.cards().length(); i++) {
                final int cardDiff =
                        getCardStrength(cardsOrder, h1.cards().charAt(i))
                                - getCardStrength(cardsOrder, h2.cards().charAt(i));
                if (cardDiff != 0) {
                    return cardDiff;
                }
            }
            return 0;
        };
    }

    private Map<Character, Long> getCardGroups(final String hand) {
        return hand.chars().mapToObj(i -> (char) i).collect(groupingBy(c -> c, counting()));
    }

    private int[] getGroupCounts(final Map<Character, Long> cardGroups) {
        return cardGroups.values().stream().mapToInt(Long::intValue).sorted().toArray();
    }

    private HandType getHandType1(final String hand) {
        return HandType.getHandType(getGroupCounts(getCardGroups(hand)));
    }

    private HandType getHandType2(final String hand) {
        final Map<Character, Long> cardGroups = getCardGroups(hand);

        // If joker is in hand, then assign all counts of joker to the most frequent
        // card that is not the joker.
        if (cardGroups.containsKey('J')) {
            cardGroups.entrySet().stream()
                    .filter(e -> e.getKey() != 'J')
                    .max(Comparator.comparingLong(Entry::getValue))
                    .map(Map.Entry::getKey)
                    .ifPresent(
                            mostFrequentChar -> {
                                cardGroups.put(
                                        mostFrequentChar,
                                        cardGroups.get(mostFrequentChar) + cardGroups.get('J'));
                                cardGroups.remove('J');
                            });
        }

        return HandType.getHandType(getGroupCounts(cardGroups));
    }

    private long sum(final Hand[] hands) {
        long sum = 0;
        long rank = 1;
        for (final Hand hand : hands) {
            sum += rank * hand.bid();
            rank++;
        }
        return sum;
    }

    @Override
    public void load(String input) {
        this.lines = input.lines().map(line -> line.split(" ")).toList();
    }

    @Override
    public long getPart1Solution() {
        final Hand[] hands =
                this.lines.stream()
                        .map(ss -> new Hand(ss[0], getHandType1(ss[0]), Long.parseLong(ss[1])))
                        .sorted(getHandComparator(CARDS_ORDER1))
                        .toArray(Hand[]::new);
        return sum(hands);
    }

    @Override
    public long getPart2Solution() {
        final Hand[] hands =
                this.lines.stream()
                        .map(ss -> new Hand(ss[0], getHandType2(ss[0]), Long.parseLong(ss[1])))
                        .sorted(getHandComparator(CARDS_ORDER2))
                        .toArray(Hand[]::new);
        return sum(hands);
    }

    private enum HandType {
        HIGH_CARD(new int[] {1, 1, 1, 1, 1}),
        ONE_PAIR(new int[] {1, 1, 1, 2}),
        TWO_PAIR(new int[] {1, 2, 2}),
        THREE_OF_A_KIND(new int[] {1, 1, 3}),
        FULL_HOUSE(new int[] {2, 3}),
        FOUR_OF_A_KIND(new int[] {1, 4}),
        FIVE_OF_A_KIND(new int[] {5});

        private final int[] groupCounts;

        HandType(final int[] groupCounts) {
            this.groupCounts = groupCounts;
        }

        static HandType getHandType(final int[] groupCounts) {
            return Arrays.stream(HandType.values())
                    .filter(ht -> Arrays.equals(ht.getGroupCounts(), groupCounts))
                    .findFirst()
                    .orElse(null);
        }

        public int[] getGroupCounts() {
            return groupCounts;
        }
    }

    private record Hand(String cards, HandType type, long bid) {}
}
