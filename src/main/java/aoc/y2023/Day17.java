package aoc.y2023;

import static java.lang.Character.getNumericValue;
import static java.util.stream.Collectors.toMap;

import aoc.AoC;
import aoc.util.Direction;
import aoc.util.Position2D;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Day17 implements AoC {

    private Position2D start;
    private Position2D end;
    private Map<Position2D, Integer> heatLossMap;

    @Override
    public void load(final String input) {
        final char[][] chars = input.lines().map(String::toCharArray).toArray(char[][]::new);
        this.start = Position2D.of(0, 0);
        this.end = Position2D.of(chars[0].length - 1, chars.length - 1);
        this.heatLossMap =
                Position2D.generate(chars)
                        .collect(toMap(p -> p, p -> getNumericValue(p.get(chars))));
    }

    @Override
    public long getPart1Solution() {
        return shortestPath(1, 3);
    }

    @Override
    public long getPart2Solution() {
        return shortestPath(4, 10);
    }

    private long shortestPath(final int minSteps, final int maxSteps) {
        final Map<GraphNode, Long> cost = new HashMap<>();
        final Set<GraphNode> visited = new HashSet<>();
        final PriorityQueue<GraphNode> unvisited =
                new PriorityQueue<>(Comparator.comparing(cost::get));

        cost.put(new GraphNode(start, Direction.down), 0L);
        cost.put(new GraphNode(start, Direction.right), 0L);
        unvisited.addAll(cost.keySet());

        while (!unvisited.isEmpty()) {
            final GraphNode node = unvisited.poll();

            if (node.position().equals(end)) {
                return cost.get(node);
            }

            if (visited.contains(node)) {
                continue;
            }

            visited.add(node);

            for (final Direction direction :
                    List.of(node.direction().rotate90(), node.direction().rotate90Reverse())) {

                Position2D newPosition = node.position();
                long newCost = cost.get(node);

                for (int step = 1; step <= maxSteps; step++) {
                    newPosition = newPosition.move(direction);
                    if (!heatLossMap.containsKey(newPosition)) {
                        break;
                    }

                    newCost += heatLossMap.get(newPosition);

                    if (step < minSteps) {
                        continue;
                    }

                    final GraphNode newNode = new GraphNode(newPosition, direction);
                    if (cost.containsKey(newNode) && cost.get(newNode) < newCost) {
                        continue;
                    }

                    cost.put(newNode, newCost);
                    unvisited.add(newNode);
                }
            }
        }

        throw new RuntimeException("No path found to " + end);
    }

    private record GraphNode(Position2D position, Direction direction) {}
}
