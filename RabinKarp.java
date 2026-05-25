public class RabinKarp {
    private static final int R = 256;
    private static final long Q = 2_147_483_647L;

    private long iterations;
    private long instructions;

    public int search(String pattern, String text) {
        instructions += 2;
        int patternLength = pattern.length();
        int textLength = text.length();

        instructions++;
        if (patternLength == 0) {
            return 0;
        }

        instructions++;
        if (patternLength > textLength) {
            return textLength;
        }

        instructions++;
        long patternHash = hash(pattern, 0, patternLength);

        instructions++;
        for (int i = 0; i <= textLength - patternLength; i++) {
            iterations++;
            instructions += 2;

            long textHash = hash(text, i, patternLength);
            instructions++;

            if (patternHash == textHash && check(pattern, text, i)) {
                instructions++;
                return i;
            }
        }

        instructions++;
        return textLength;
    }

    private long hash(String value, int start, int length) {
        instructions++;
        long hash = 0;

        instructions++;
        for (int j = 0; j < length; j++) {
            iterations++;
            instructions += 5;
            hash = (hash * R + value.charAt(start + j)) % Q;
        }

        instructions++;
        return hash;
    }

    private boolean check(String pattern, String text, int start) {
        instructions++;
        for (int j = 0; j < pattern.length(); j++) {
            iterations++;
            instructions += 3;

            if (pattern.charAt(j) != text.charAt(start + j)) {
                instructions++;
                return false;
            }
        }

        instructions++;
        return true;
    }

    public long getIterations() {
        return iterations;
    }

    public long getInstructions() {
        return instructions;
    }

    public void resetCounters() {
        iterations = 0;
        instructions = 0;
    }

    private static String repeat(char character, int times) {
        StringBuilder builder = new StringBuilder(times);

        for (int i = 0; i < times; i++) {
            builder.append(character);
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        RabinKarp rabinKarp = new RabinKarp();

        String text = "ABCDCBDCBDACBDABDCBADF";
        String pattern = "ADF";

        int position = rabinKarp.search(pattern, text);
        System.out.println("Teste pequeno");
        System.out.println("Texto: " + text);
        System.out.println("Padrao: " + pattern);
        System.out.println("Posicao encontrada: " + position);
        System.out.println("Iteracoes: " + rabinKarp.getIterations());
        System.out.println("Instrucoes estimadas: " + rabinKarp.getInstructions());

        rabinKarp.resetCounters();

        int textSize = 600_000;
        String largeText = repeat('A', textSize) + "BOM";
        String largePattern = "AAAAABOM";

        long startTime = System.currentTimeMillis();
        int largePosition = rabinKarp.search(largePattern, largeText);
        long endTime = System.currentTimeMillis();

        System.out.println();
        System.out.println("Teste grande");
        System.out.println("Tamanho do texto: " + largeText.length());
        System.out.println("Tamanho do padrao: " + largePattern.length());
        System.out.println("Posicao encontrada: " + largePosition);
        System.out.println("Iteracoes: " + rabinKarp.getIterations());
        System.out.println("Instrucoes estimadas: " + rabinKarp.getInstructions());
        System.out.println("Tempo em ms: " + (endTime - startTime));
        System.out.println();
        System.out.println("Complexidade no pior caso: O((N - M + 1) * M), ou O(N * M).");
    }
}
