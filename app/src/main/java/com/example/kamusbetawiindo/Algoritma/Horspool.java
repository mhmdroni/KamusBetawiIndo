package com.example.kamusbetawiindo.Algoritma;

public class Horspool {
    public static final char[] ALPHABET = "abcdefghijklmnoprstuvyz".toCharArray();
    private static int[] shiftTable = new int[ALPHABET.length];

    public int horspool(String source, String patt) {
        char[] text = source.toCharArray();
        char[] pattern = patt.toCharArray();
        prepareTable(pattern);

        // start with lenght of pattern
        int i = pattern.length - 1, k;
        try {
            isMatch:
            while (i < text.length) {
                k = i;
                for (int j = pattern.length - 1; j >= 0; j--) {
                    if (pattern[j] == text[k]) {
                        k--;
                        continue;
                    } else {
                        i += shiftTable[getCharIndex(text[i])];
                        continue isMatch;
                    }
                }
                return k + 1;
            }
        } catch (Exception ignore){}

        return -1;
    }

    public void prepareTable(char[] pattern) {
        for (int i = 0; i < shiftTable.length; i++) {
            shiftTable[i] = pattern.length;
        }
        for (char c : pattern) {
            shiftTable[getCharIndex(c)] = shiftCount(c, pattern);
        }
    }

    private Integer shiftCount(char c, char[] pattern) {
        for (int i = pattern.length - 2/* ignore last char of pattern */; i >= 0; i--) {
            if (pattern[i] == c)
                return pattern.length - 1 - i; // m-1-n
        }
        return pattern.length;
    }

    public int getCharIndex(char i) {
        int j = 0;
        for (char ch : ALPHABET) {
            if (ch == i)
                return j;
            j++;
        }
        return -1;
    }

    /*public static int SIZE = 500;
    public static int table[] = new int[SIZE];

    public void shifttable(String pattern) {

        int i, j, m;
        char p[] = pattern.toCharArray();
        m = pattern.length();

        for (i = 0; i < SIZE; i++)
            table[i] = m;
        for (j = 0; j < m; j++)
            table[p[j]] = m - 1 - j;
    }

    public int horspool(String source, String pattern) {
        int i, k, pos, m;
        char s[] = source.toCharArray();
        char p[] = pattern.toCharArray();
        m = pattern.length();

        for (i = m - 1; i < source.length(); ) {
            k = 0;
            while ((k < m) && (p[m - 1 - k] == s[i - k]))
                k++;
            if (k == m) {
                pos = i - m + 2;
                return pos;
            } else
                i += table[s[i]];
        }
        return -1;
    }

    public static int[] computeLastOcc(String P) {
        int[] lastOcc = new int[128]; // assume ASCII character set

        for (int i = 0; i < 128; i++) {
            lastOcc[i] = -1; // initialize all elements to -1
        }

      *//* ===================================================
	 Horspool omits P[m-1] to compute lastOcc[]
	 =================================================== *//*
        for (int i = 0; i < P.length() - 1; i++) {
            lastOcc[P.charAt(i)] = i; // The LAST value will be store
        }

        return lastOcc;
    }


    public static int horspool(String T, String P) {
        int[] lastOcc;
        int i0, j, m, n;

        n = T.length();
        m = P.length();

        lastOcc = computeLastOcc(P);  // Find last occurence of all characters in P

        System.out.println("Pattern found at position : "+ lastOcc);

        i0 = 0;         // Line P up at T[0]

        while (i0 < (n - m)) {
            j = m - 1;    // Start at the last char in P

            System.out.println("+++++++++++++++++++++++++++++++++++++");


            while (P.charAt(j) == T.charAt(i0 + j)) {
                j--;        // Check "next" (= previous) character

                System.out.println("Pattern found at position : "+ j);

                if (j < 0)
                    return (i0);    // P found !
            }

        *//* =============================================
	   Use T[i0+(m-1)] to slide
	   ============================================= *//*
            i0 = i0 + (m - 1) - lastOcc[T.charAt(i0 + (m - 1))];
        }

        return -1; // no match
    }*/
}
