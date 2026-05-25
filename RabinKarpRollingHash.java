public class RabinKarpRollingHash {
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
        long highestPower = highestPower(patternLength);

        instructions += 2;
        long patternHash = hash(pattern, 0, patternLength);
        long textHash = hash(text, 0, patternLength);

        instructions++;
        for (int i = 0; i <= textLength - patternLength; i++) {
            iterations++;
            instructions += 2;

            if (patternHash == textHash && check(pattern, text, i)) {
                instructions++;
                return i;
            }

            instructions++;
            if (i < textLength - patternLength) {
                instructions++;
                textHash = rollHash(textHash, text.charAt(i), text.charAt(i + patternLength), highestPower);
            }
        }

        instructions++;
        return textLength;
    }

    private long highestPower(int patternLength) {
        instructions++;
        long power = 1;

        instructions++;
        for (int i = 1; i < patternLength; i++) {
            iterations++;
            instructions += 3;
            power = (power * R) % Q;
        }

        instructions++;
        return power;
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

    private long rollHash(long oldHash, char oldCharacter, char newCharacter, long highestPower) {
        instructions += 4;
        long withoutOldCharacter = (oldHash - (oldCharacter * highestPower) % Q + Q) % Q;
        return (withoutOldCharacter * R + newCharacter) % Q;
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
        RabinKarpRollingHash rabinKarp = new RabinKarpRollingHash();

        String text = "ABCDCBDCBDACBDABDCBADF";
        String pattern = "ADF";

        int position = rabinKarp.search(pattern, text);
        System.out.println("Teste pequeno com Rolling Hash");
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
        System.out.println("Teste grande com Rolling Hash");
        System.out.println("Tamanho do texto: " + largeText.length());
        System.out.println("Tamanho do padrao: " + largePattern.length());
        System.out.println("Posicao encontrada: " + largePosition);
        System.out.println("Iteracoes: " + rabinKarp.getIterations());
        System.out.println("Instrucoes estimadas: " + rabinKarp.getInstructions());
        System.out.println("Tempo em ms: " + (endTime - startTime));
        System.out.println();
        System.out.println("Complexidade esperada: O(N + M).");
        System.out.println("Complexidade no pior caso com muitas colisoes: O(N * M).");
    }
}
