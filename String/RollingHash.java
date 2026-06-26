
/**
 * 
 * 
 * RollingHash algo steps:
 * 
 * What & Why:
 * Convert string → number (hash) for O(1) comparisons instead of O(n).
 * Used in: pattern matching, duplicate detection, anagram grouping, fast
 * lookups.
 * 
 * Hash = string's fingerprint. Same string → always same hash. Different
 * strings → usually different hash (not guaranteed).
 * 
 * Formula:
 * hash = (s[0]*p^(n-1) + s[1]*p^(n-2) + ... + s[n-1]*p^0) % MOD
 * 
 * p = prime base (usually 31 for lowercase, 131 for all ASCII)
 * MOD = large prime (1e9+7) to avoid overflow
 * 
 * The "Rolling" Part:
 * Slide window without recomputing from scratch:
 *
 * remove left char → add right char
 * new_hash = (old_hash - s[left] * p^(n-1)) * p + s[right]
 * 
 * Collision:
 * Two different strings can have same hash → false positive.
 * Fix: use double hashing (two different MOD values).
 * 
 * 
 */

public class RollingHash {
  static long MOD = (long) 1e9 + 7;
  static long BASE = 131;

  static long power(long base, int exp) {
    long result = 1;
    base %= MOD;
    while (exp > 0) {
      if ((exp & 1) == 1)
        result = result * base % MOD;
      base = base * base % MOD;
      exp >>= 1;
    }
    return result;
  }

  public static boolean rollingHashAlgo(String text, String s) {
    int n = text.length();
    int m = s.length();

    // find hash of pattern and first window
    long hash = 0;
    long newHash = 0;
    for (int i = 0; i < m; i++) {
      char ch = s.charAt(i);
      char cha = text.charAt(i);

      hash += (ch - 'a' + 1) * power(BASE, m - i - 1);
      newHash += (cha - 'a' + 1) * power(BASE, m - i - 1);
    }

    hash = hash % MOD;
    newHash = newHash % MOD;

    // check first window
    if (hash == newHash) {
      boolean match = true;
      for (int i = 0; i < m; i++) {
        if (s.charAt(i) != text.charAt(i)) {
          match = false;
          break;
        }
      }
      if (match)
        return true;
    }

    // Bug 6 fix: r starts at m (next char after first window)
    int l = 0;
    int r = m;

    while (r < n) {
      // Bug 3 fix: correct rolling formula with * BASE shift
      newHash = (newHash - (text.charAt(l) - 'a' + 1) * power(BASE, m - 1) % MOD + MOD) % MOD;
      newHash = (newHash * BASE + (text.charAt(r) - 'a' + 1)) % MOD;

      l++;

      if (newHash == hash) {
        boolean flag = true;
        for (int i = 0; i < m; i++) {
          if (s.charAt(i) != text.charAt(l + i)) {
            flag = false;
            break;
          }
        }
        if (flag)
          return true;
      }

      r++;
    }

    return false;
  }

  public static void main(String[] args) {

  }
}
