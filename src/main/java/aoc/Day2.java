package aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day2 {

    static final Map<String, Integer> part1Limits =
            Map.of(
                    "red", 12,
                    "green", 13,
                    "blue", 14);

    public static int solvePart1(final String content) {
        return content.lines()
                .map(Day2::parseGame)
                .filter(Day2::isGamePossible)
                .mapToInt(Game::id)
                .sum();
    }

    public static int solvePart2(final String content) {
        return content.lines().map(Day2::parseGame).mapToInt(Day2::getPowerOfMinimumCubeSet).sum();
    }

    static Game parseGame(final String line) {
        final String[] gameSets = line.split(":");
        final int id = Integer.parseInt(gameSets[0].substring(5));
        final List<Map<String, Integer>> sets = new ArrayList<>();
        for (final String set : gameSets[1].split(";")) {
            final Map<String, Integer> setValues = new HashMap<>();
            for (final String color : set.split(",")) {
                final String[] values = color.trim().split("\\s+");
                setValues.put(values[1], Integer.parseInt(values[0]));
            }
            sets.add(setValues);
        }
        return new Game(id, sets);
    }

    static boolean isGamePossible(final Game game) {
        return game.sets().stream()
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .allMatch(e -> e.getValue() <= part1Limits.get(e.getKey()));
    }

    static int getPowerOfMinimumCubeSet(final Game game) {
        return game.sets().stream()
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Math::max))
                .values()
                .stream()
                .reduce(1, (a, b) -> a * b);
    }

    record Game(int id, List<Map<String, Integer>> sets) {}
}
