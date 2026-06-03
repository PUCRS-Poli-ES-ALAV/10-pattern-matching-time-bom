public class KnuthMorrisPratt {
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
        int[] lps = computeLps(pattern);

        instructions += 2;
        int i = 0;
        int j = 0;

        instructions++;
        while (i < textLength) {
            iterations++;
            instructions += 3;

            if (pattern.charAt(j) == text.charAt(i)) {
                instructions += 2;
                i++;
                j++;
            }

            instructions++;
            if (j == patternLength) {
                instructions++;
                return i - j;
            }

            instructions += 2;
            if (i < textLength && pattern.charAt(j) != text.charAt(i)) {
                instructions++;
                if (j != 0) {
                    instructions++;
                    j = lps[j - 1];
                } else {
                    instructions++;
                    i++;
                }
            }
        }

        instructions++;
        return textLength;
    }

    private int[] computeLps(String pattern) {
        instructions++;
        int patternLength = pattern.length();

        instructions++;
        int[] lps = new int[patternLength];

        instructions += 3;
        int length = 0;
        int i = 1;
        lps[0] = 0;

        instructions++;
        while (i < patternLength) {
            iterations++;
            instructions += 3;

            if (pattern.charAt(i) == pattern.charAt(length)) {
                instructions += 3;
                length++;
                lps[i] = length;
                i++;
            } else {
                instructions++;
                if (length != 0) {
                    instructions++;
                    length = lps[length - 1];
                } else {
                    instructions += 2;
                    lps[i] = 0;
                    i++;
                }
            }
        }

        instructions++;
        return lps;
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
