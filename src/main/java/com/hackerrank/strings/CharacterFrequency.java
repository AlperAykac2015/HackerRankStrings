package com.hackerrank.strings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacterFrequency {

  public Map<Character, Long> countCharacters(String str) {
    if (str == null) {
      return Collections.emptyMap();
    }

    return str.chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.groupingBy(
            c -> c,
            Collectors.counting()
        ));
  }

  // Alternative: Traditional approach
  public Map<Character, Integer> countCharactersTraditional(String str) {
    if (str == null) {
      return Collections.emptyMap();
    }

    Map<Character, Integer> frequency = new HashMap<>();

    for (char c : str.toCharArray()) {
      frequency.put(c, frequency.getOrDefault(c, 0) + 1);
    }

    return frequency;
  }

  public  Map<Character, Integer> countCharactersEfficiently(String s) {
    boolean[] seen = new boolean[65536];
    Map<Character, Integer> result = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (!seen[c]) {
        seen[c] = true;
        result.put(c,1);
      } else {
        result.put(c, result.get(c) + 1);
      }
    }
    return result;
  }


}
