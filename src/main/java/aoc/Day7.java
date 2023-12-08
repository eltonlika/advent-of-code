package aoc;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.stream.Collectors.*;

import java.util.Arrays;

public class Day7 {

    private static final char[] CARDS_ORDER1 =
            new char[] {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    private static final char[] CARDS_ORDER2 =
            new char[] {'J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A'};

    private static int getCardStrength(final char[] cardsOrder, final char card) {
        for (int i = 0; i < cardsOrder.length; i++) {
            if (cardsOrder[i] == card) {
                return i;
            }
        }
        return -1;
    }

    private static Map<Character, Long> getCardGroups(final String hand) {
        return hand.chars().mapToObj(i -> (char) i).collect(groupingBy(c -> c, counting()));
    }

    private static int[] getFrequencies(final Map<Character, Long> cardGroups) {
        return cardGroups.values().stream().mapToInt(Long::intValue).sorted().toArray();
    }

    private static HandType getHandType(final int[] frequencies) {
        for (final HandType handType : HandType.values()) {
            if (Arrays.equals(handType.frequencies, frequencies)) {
                return handType;
            }
        }
        return null;
    }

    private static HandType getHandType1(final String hand) {
        return getHandType(getFrequencies(getCardGroups(hand)));
    }

    private static HandType getHandType2(final String hand) {
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

        return getHandType(getFrequencies(cardGroups));
    }

    private static Comparator<Hand> getHandComparator(final char[] cardsOrder) {
        return (h1, h2) -> {
            final int typeDiff = h1.type().ordinal() - h2.type().ordinal();
            if (typeDiff != 0) {
                return typeDiff;
            }
            for (int i = 0; i < h1.cards().length(); i++) {
                final char card1 = h1.cards().charAt(i);
                final char card2 = h2.cards().charAt(i);
                final int cardDiff =
                        getCardStrength(cardsOrder, card1) - getCardStrength(cardsOrder, card2);
                if (cardDiff != 0) {
                    return cardDiff;
                }
            }
            return 0;
        };
    }

    private static long sum(final Hand[] hands) {
        long rank = 1, sum = 0;
        for (final Hand hand : hands) {
            sum += rank++ * hand.bid();
        }
        return sum;
    }

    public static long solvePart1(final String content) {
        final Hand[] hands =
                content.lines()
                        .map(line -> line.split("\\s+"))
                        .map(ss -> new Hand(ss[0], getHandType1(ss[0]), Integer.parseInt(ss[1])))
                        .sorted(getHandComparator(CARDS_ORDER1))
                        .toArray(Hand[]::new);
        return sum(hands);
    }

    public static long solvePart2(final String content) {
        final Hand[] hands =
                content.lines()
                        .map(line -> line.split("\\s+"))
                        .map(ss -> new Hand(ss[0], getHandType2(ss[0]), Integer.parseInt(ss[1])))
                        .sorted(getHandComparator(CARDS_ORDER2))
                        .toArray(Hand[]::new);
        return sum(hands);
    }

    enum HandType {
        HIGH_CARD(new int[] {1, 1, 1, 1, 1}),
        ONE_PAIR(new int[] {1, 1, 1, 2}),
        TWO_PAIR(new int[] {1, 2, 2}),
        THREE_OF_A_KIND(new int[] {1, 1, 3}),
        FULL_HOUSE(new int[] {2, 3}),
        FOUR_OF_A_KIND(new int[] {1, 4}),
        FIVE_OF_A_KIND(new int[] {5});

        private final int[] frequencies;

        HandType(final int[] frequencies) {
            this.frequencies = frequencies;
        }
    }

    record Hand(String cards, HandType type, long bid) {}
}
