public class NaivePatternMatching {
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
        for (int i = 0; i <= textLength - patternLength; i++) {
            iterations++;
            instructions += 2;

            int j = 0;
            instructions++;

            while (j < patternLength) {
                iterations++;
                instructions += 3;

                if (text.charAt(i + j) != pattern.charAt(j)) {
                    instructions++;
                    break;
                }

                instructions++;
                j++;
            }

            instructions++;
            if (j == patternLength) {
                return i;
            }
        }

        instructions++;
        return textLength;
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
}
