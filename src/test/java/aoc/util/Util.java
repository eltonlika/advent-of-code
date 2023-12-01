package aoc.util;

import org.assertj.core.util.Files;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Util {

    public static String read(final String filename) {
        final URL url = Util.class.getClassLoader().getResource(filename);
        final String absolutePath = Objects.requireNonNull(url).getPath();
        return Files.contentOf(new File(absolutePath), StandardCharsets.UTF_8);
    }
}
