package aoc.y2023;

import static aoc.util.Direction.down;
import static aoc.util.Direction.right;

import static java.util.stream.Collectors.toMap;

import aoc.AoC;
import aoc.util.Direction;
import aoc.util.Position2D;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Day17 implements AoC {

    private int[][] values;
    private Map<Position2D, Integer> heatLossMap;

    @Override
    public void load(final String input) {
        this.values =
                input.lines()
                        .map(l -> l.chars().map(Character::getNumericValue).toArray())
                        .toArray(int[][]::new);
        heatLossMap = Position2D.generate(values).collect(toMap(p -> p, p -> p.get(values)));
    }

    @Override
    public long getPart1Solution() {
        final Position2D start = Position2D.of(0, 0);
        final Position2D end = Position2D.of(values[0].length - 1, values.length - 1);
        return shortestPath(start, end);
    }

    @Override
    public long getPart2Solution() {
        return 0L;
    }

    private long shortestPath(final Position2D start, final Position2D end) {

        final Map<GraphNode, Long> cost = new HashMap<>();
        final Set<GraphNode> visited = new HashSet<>();
        final PriorityQueue<GraphNode> unvisited =
                new PriorityQueue<>(Comparator.comparing(cost::get));

        cost.put(new GraphNode(start, down, 0), 0L);
        cost.put(new GraphNode(start, right, 0), 0L);
        unvisited.addAll(cost.keySet());

        while (!unvisited.isEmpty()) {
            final GraphNode node = unvisited.poll();
            final Position2D position = node.position();

            if (position.equals(end)) {
                return cost.get(node);
            }

            if (visited.contains(node)) {
                continue;
            }

            visited.add(node);

            for (final Direction newDirection :
                    Arrays.asList(
                            node.direction(),
                            node.direction().rotate90(),
                            node.direction().rotate90Reverse())) {

                if (newDirection == node.direction() && node.directionCount() >= 3) {
                    continue;
                }

                final Position2D neighbour = position.move(newDirection);
                if (!heatLossMap.containsKey(neighbour)) {
                    continue;
                }

                final int newDirectionCount =
                        newDirection == node.direction() ? node.directionCount() + 1 : 1;

                final GraphNode newNode = new GraphNode(neighbour, newDirection, newDirectionCount);
                if (visited.contains(newNode)) {
                    continue;
                }

                final long newCost = heatLossMap.get(neighbour) + cost.get(node);
                final Long existingCost = cost.get(newNode);
                if (existingCost != null && existingCost < newCost) {
                    continue;
                }

                cost.put(newNode, newCost);
                unvisited.add(newNode);
            }
        }

        throw new RuntimeException("No path found to " + end);
    }

    private record GraphNode(Position2D position, Direction direction, int directionCount) {}
}
