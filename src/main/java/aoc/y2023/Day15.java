package aoc.y2023;

import aoc.AoC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day15 implements AoC {

    private List<String> initializationSequence;

    @Override
    public void load(String input) {
        this.initializationSequence = Arrays.stream(input.split(",")).toList();
    }

    @Override
    public long getPart1Solution() {
        return initializationSequence.stream().mapToLong(this::hash).sum();
    }

    @Override
    public long getPart2Solution() {
        final List<List<Lens>> boxes = new ArrayList<List<Lens>>(256);
        IntStream.range(1, 256).forEach(i -> boxes.add(new ArrayList<Lens>()));

        for (final String seq : initializationSequence) {
            String label;
            boolean removeLens = false;
            int focalLength = 0;

            if (seq.endsWith("-")) {
                label = seq.substring(0, seq.length() - 1);
                removeLens = true;
            } else {
                final String[] split = seq.split("=");
                label = split[0];
                focalLength = Integer.parseInt(split[1]);
            }

            final List<Lens> lenses = boxes.get(hash(label));
            final int index = findLens(lenses, label);

            if (removeLens) {
                if (index >= 0) {
                    lenses.remove(index);
                }
                continue;
            }

            final Lens newLens = new Lens(label, focalLength);
            if (index >= 0) {
                lenses.set(index, newLens);
            } else {
                lenses.add(newLens);
            }
        }

        long result = 0;
        int i = 1;
        for (final List<Lens> lenses : boxes) {
            int j = 1;
            for (final Lens lens : lenses) {
                result += (long) i * j * lens.focalLength();
                j++;
            }
            i++;
        }
        return result;
    }

    private int findLens(final List<Lens> lenses, final String label) {
        int i = 0;
        for (final Lens lens : lenses) {
            if (label.equals(lens.label())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private int hash(final String s) {
        int result = 0;
        for (final char c : s.toCharArray()) {
            result += c;
            result = result * 17;
            result = result % 256;
        }
        return result;
    }

    private record Lens(String label, int focalLength) {}
}
