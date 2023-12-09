package aoc.y2023;

import aoc.AoC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day2 implements AoC {

    private static final Map<String, Long> part1Limits =
            Map.of(
                    "red", 12L,
                    "green", 13L,
                    "blue", 14L);

    private List<Game> games;

    @Override
    public void load(String input) {
        this.games = input.lines().map(Game::parse).toList();
    }

    @Override
    public long getPart1Solution() {
        return this.games.stream().filter(Game::isPossible).mapToLong(Game::id).sum();
    }

    @Override
    public long getPart2Solution() {
        return this.games.stream().mapToLong(Game::getPowerOfMinimumCubeSet).sum();
    }

    private record Game(long id, List<Map<String, Long>> sets) {

        static Game parse(final String line) {
            final String[] gameSets = line.split(":");
            final long id = Long.parseLong(gameSets[0].substring(5));
            final List<Map<String, Long>> sets = new ArrayList<>();
            for (final String set : gameSets[1].split(";")) {
                final Map<String, Long> setValues = new HashMap<>();
                for (final String color : set.split(",")) {
                    final String[] values = color.trim().split("\\s+");
                    setValues.put(values[1], Long.parseLong(values[0]));
                }
                sets.add(setValues);
            }
            return new Game(id, sets);
        }

        boolean isPossible() {
            return sets().stream()
                    .map(Map::entrySet)
                    .flatMap(Set::stream)
                    .allMatch(e -> e.getValue() <= part1Limits.get(e.getKey()));
        }

        long getPowerOfMinimumCubeSet() {
            return sets().stream()
                    .map(Map::entrySet)
                    .flatMap(Set::stream)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Math::max))
                    .values()
                    .stream()
                    .reduce(1L, (a, b) -> a * b);
        }
    }
}
