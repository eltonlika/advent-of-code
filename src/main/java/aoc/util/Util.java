package aoc.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.LongStream;

public final class Util {
    private Util() {}

    public static String read(final String filename) {
        final URL url = Util.class.getClassLoader().getResource(filename);
        final String absolutePath = Objects.requireNonNull(url).getPath();
        try {
            return Files.readString(Paths.get(absolutePath), UTF_8);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LongStream parseNumbers(final String list, final String delimiter) {
        return Arrays.stream(list.trim().split(delimiter))
                .filter(s -> !s.isBlank())
                .mapToLong(Long::parseLong);
    }

    public static long lcm(final long a, final long b) {
        return a * b / gcd(a, b);
    }

    public static long gcd(final long a, final long b) {
        if (a < b) {
            return gcd(b, a);
        }
        if (a % b == 0) {
            return b;
        }
        return gcd(b, a % b);
    }
}
