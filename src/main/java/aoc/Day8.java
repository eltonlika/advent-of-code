package aoc;

import aoc.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Day8 {

    static HauntedMap parse(final String content) {
        final String[] lines = content.lines().toArray(String[]::new);
        final int[] instructions = lines[0].chars().map(i -> (char) i == 'L' ? 0 : 1).toArray();
        final Map<String, String[]> network = new HashMap<>();
        for (int i = 2; i < lines.length; i++) {
            final String[] eq = lines[i].split("=");
            final String[] lr = eq[1].trim().replace("(", "").replace(")", "").split(", ");
            network.put(eq[0].trim(), lr);
        }
        return new HauntedMap(instructions, network);
    }

    static long stepsNeededToArrive(
            final HauntedMap map,
            final String startingNode,
            final Predicate<String> destinationNodeMatcher) {
        long steps = 0;
        int pc = 0;
        String currentNode = startingNode;
        while (!destinationNodeMatcher.test(currentNode)) {
            currentNode = map.nodes.get(currentNode)[map.instructions[pc]];
            pc = (pc == map.instructions.length - 1) ? 0 : pc + 1;
            steps++;
        }
        return steps;
    }

    public static long solvePart1(final String content) {
        final HauntedMap map = parse(content);
        return stepsNeededToArrive(map, "AAA", "ZZZ"::equals);
    }

    public static long solvePart2(final String content) {
        final HauntedMap map = parse(content);
        return map.nodes.keySet().stream()
                .filter(node -> node.endsWith("A"))
                .mapToLong(node -> stepsNeededToArrive(map, node, dest -> dest.endsWith("Z")))
                .reduce(1L, Util::lcm);
    }

    record HauntedMap(int[] instructions, Map<String, String[]> nodes) {}
}
