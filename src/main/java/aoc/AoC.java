package aoc;

import static aoc.util.Util.read;

public interface AoC {

    default AoC withInputFile(final String inputFile) {
        load(read(inputFile));
        return this;
    }

    void load(final String input);

    long getPart1Solution();

    long getPart2Solution();
}
