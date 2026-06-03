import java.util.Locale;

public class PatternMatchingExperiment {
    private static final String SMALL_TEXT = "ABCDCBDCBDACBDABDCBADF";
    private static final String SMALL_PATTERN = "ADF";
    private static final int SMALL_EXPECTED_POSITION = 19;

    private static final int LARGE_TEXT_BASE_SIZE = 600_000;
    private static final String LARGE_PATTERN = "AAAAABOM";
    private static final int LARGE_EXPECTED_POSITION = 599_995;

    private static class Result {
        private final String algorithm;
        private final int textLength;
        private final int patternLength;
        private final int position;
        private final int expectedPosition;
        private final long iterations;
        private final long instructions;
        private final double timeMs;

        private Result(
                String algorithm,
                int textLength,
                int patternLength,
                int position,
                int expectedPosition,
                long iterations,
                long instructions,
                double timeMs
        ) {
            this.algorithm = algorithm;
            this.textLength = textLength;
            this.patternLength = patternLength;
            this.position = position;
            this.expectedPosition = expectedPosition;
            this.iterations = iterations;
            this.instructions = instructions;
            this.timeMs = timeMs;
        }

        private String status() {
            return position == expectedPosition ? "OK" : "ERRO";
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        String largeText = repeat('A', LARGE_TEXT_BASE_SIZE) + "BOM";

        System.out.println("Tabela de resultados - strings pequenas");
        printTable(new Result[] {
                runNaive(SMALL_TEXT, SMALL_PATTERN, SMALL_EXPECTED_POSITION),
                runRabinKarpHorner(SMALL_TEXT, SMALL_PATTERN, SMALL_EXPECTED_POSITION),
                runRabinKarpRollingHash(SMALL_TEXT, SMALL_PATTERN, SMALL_EXPECTED_POSITION),
                runKnuthMorrisPratt(SMALL_TEXT, SMALL_PATTERN, SMALL_EXPECTED_POSITION)
        });

        System.out.println();
        System.out.println("Tabela de resultados - strings grandes");
        printTable(new Result[] {
                runNaive(largeText, LARGE_PATTERN, LARGE_EXPECTED_POSITION),
                runRabinKarpHorner(largeText, LARGE_PATTERN, LARGE_EXPECTED_POSITION),
                runRabinKarpRollingHash(largeText, LARGE_PATTERN, LARGE_EXPECTED_POSITION),
                runKnuthMorrisPratt(largeText, LARGE_PATTERN, LARGE_EXPECTED_POSITION)
        });

        System.out.println();
        System.out.println("Complexidades no pior caso");
        System.out.println("- Naive: O(N * M)");
        System.out.println("- Rabin-Karp sem rolling hash: O(N * M)");
        System.out.println("- Rabin-Karp com rolling hash: O(N * M) com muitas colisoes; esperado O(N + M)");
        System.out.println("- Knuth-Morris-Pratt: O(N + M)");
    }

    private static Result runNaive(String text, String pattern, int expectedPosition) {
        NaivePatternMatching matcher = new NaivePatternMatching();

        long start = System.nanoTime();
        int position = matcher.search(pattern, text);
        long end = System.nanoTime();

        return createResult("Naive", text, pattern, expectedPosition, position,
                matcher.getIterations(), matcher.getInstructions(), start, end);
    }

    private static Result runRabinKarpHorner(String text, String pattern, int expectedPosition) {
        RabinKarp matcher = new RabinKarp();

        long start = System.nanoTime();
        int position = matcher.search(pattern, text);
        long end = System.nanoTime();

        return createResult("Rabin-Karp sem rolling hash", text, pattern, expectedPosition, position,
                matcher.getIterations(), matcher.getInstructions(), start, end);
    }

    private static Result runRabinKarpRollingHash(String text, String pattern, int expectedPosition) {
        RabinKarpRollingHash matcher = new RabinKarpRollingHash();

        long start = System.nanoTime();
        int position = matcher.search(pattern, text);
        long end = System.nanoTime();

        return createResult("Rabin-Karp com rolling hash", text, pattern, expectedPosition, position,
                matcher.getIterations(), matcher.getInstructions(), start, end);
    }

    private static Result runKnuthMorrisPratt(String text, String pattern, int expectedPosition) {
        KnuthMorrisPratt matcher = new KnuthMorrisPratt();

        long start = System.nanoTime();
        int position = matcher.search(pattern, text);
        long end = System.nanoTime();

        return createResult("Knuth-Morris-Pratt", text, pattern, expectedPosition, position,
                matcher.getIterations(), matcher.getInstructions(), start, end);
    }

    private static Result createResult(
            String algorithm,
            String text,
            String pattern,
            int expectedPosition,
            int position,
            long iterations,
            long instructions,
            long start,
            long end
    ) {
        double timeMs = (end - start) / 1_000_000.0;

        return new Result(algorithm, text.length(), pattern.length(), position, expectedPosition,
                iterations, instructions, timeMs);
    }

    private static void printTable(Result[] results) {
        System.out.println("| Implementacao | Tamanho do texto | Tamanho do padrao | Posicao | Esperado | Iteracoes | Instrucoes estimadas | Tempo (ms) | Status |");
        System.out.println("|---|---:|---:|---:|---:|---:|---:|---:|---|");

        for (Result result : results) {
            System.out.printf(
                    "| %s | %d | %d | %d | %d | %d | %d | %.3f | %s |%n",
                    result.algorithm,
                    result.textLength,
                    result.patternLength,
                    result.position,
                    result.expectedPosition,
                    result.iterations,
                    result.instructions,
                    result.timeMs,
                    result.status()
            );
        }
    }

    private static String repeat(char character, int times) {
        StringBuilder builder = new StringBuilder(times);

        for (int i = 0; i < times; i++) {
            builder.append(character);
        }

        return builder.toString();
    }
}
