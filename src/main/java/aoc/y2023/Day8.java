package aoc.y2023;

import aoc.AoC;
import aoc.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Day8 implements AoC {

    private HauntedMap map;

    private long stepsNeededToArrive(
            final String startingNode, final Predicate<String> destinationNodeMatcher) {
        long steps = 0;
        int pc = 0;
        String currentNode = startingNode;
        while (!destinationNodeMatcher.test(currentNode)) {
            currentNode = this.map.nodes.get(currentNode)[this.map.instructions[pc]];
            pc = (pc == this.map.instructions.length - 1) ? 0 : pc + 1;
            steps++;
        }
        return steps;
    }

    @Override
    public void load(String input) {
        final String[] lines = input.lines().toArray(String[]::new);
        final int[] instructions = lines[0].chars().map(i -> (char) i == 'L' ? 0 : 1).toArray();
        final Map<String, String[]> network = new HashMap<>();
        for (int i = 2; i < lines.length; i++) {
            final String[] eq = lines[i].split("=");
            final String[] lr = eq[1].trim().replace("(", "").replace(")", "").split(", ");
            network.put(eq[0].trim(), lr);
        }
        this.map = new HauntedMap(instructions, network);
    }

    @Override
    public long getPart1Solution() {
        return stepsNeededToArrive("AAA", "ZZZ"::equals);
    }

    @Override
    public long getPart2Solution() {
        return this.map.nodes.keySet().stream()
                .filter(node -> node.endsWith("A"))
                .mapToLong(node -> stepsNeededToArrive(node, dest -> dest.endsWith("Z")))
                .reduce(1L, Util::lcm);
    }

    private record HauntedMap(int[] instructions, Map<String, String[]> nodes) {}
}
