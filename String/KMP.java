/**
 * Knuth morris-prat: KMP algo with LPS -> longest prefix and suffix.
 * 
 * pattern = "ABABABD";
 * text = "ABABD";
 * LPS[j] = (0 to j - 1) => longest prefix and suffix with equal length.
 * 
 * 
 * Imp part cal LPS: After LPS cal, KMP is to easy.
 * 
 */

public class KMP {

  /*
   * 
   * LPS Array Construction:
   * 
   * lps[0] = 0, len = 0, i = 1
   * If pat[i] == pat[len] → len++, lps[i] = len, i++
   * Else if len != 0 → len = lps[len-1]
   * Else → lps[i] = 0, i++
   * Repeat till i < m
   * 
   */

  public static int[] LPS(String pattern) {
    int m = pattern.length();

    // create a LPS array of size text(m)
    int lps[] = new int[m];

    // text = "abababd";
    // pattern = "ababd";

    lps[0] = 0;
    int i = 1, len = 0; // len -> length of previous longest prefix-suffix

    while (i < m) {
      if (pattern.charAt(i) == pattern.charAt(len)) {
        len++;
        lps[i] = len;
        i++;
      } else {
        if (len != 0) {
          len = lps[len - 1];
        } else {
          lps[i] = 0;
          i++;
        }
      }
    }

    return lps;

  }

  /**
   * Build lps[] of pattern
   * i = 0, j = 0
   * If txt[i] == pat[j] → i++, j++
   * If j == m → match found at i-j, set j = lps[j-1]
   * Else if mismatch:
   * 
   * j != 0 → j = lps[j-1]
   * j == 0 → i++
   * 
   * 
   * Repeat till i < n
   * 
   * 
   * Pattern Found at index: i - j
   */

  public static boolean Kmp_algo(String text, String pattern, int lps[]) {
    int n = text.length();
    int m = pattern.length();
    int i = 0, j = 0;

    while (i < n) {
      if (text.charAt(i) == pattern.charAt(j)) {
        i++;
        j++;
        if (j == m) {
          System.out.println("Pattern found at index: " + (i - j));
          j = lps[j - 1]; // continue searching for next match
        }
      } else if (j > 0) {
        j = lps[j - 1];
      } else {
        i++;
      }
    }
    return false; // or track matches in a list and return that
  }

  public static void main(String[] args) {

  }
}
