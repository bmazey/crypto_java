package org.nyu.dto;

import java.util.ArrayList;
import java.util.Arrays;

public class Dictionary {

    /**
     * This class represents a Dictionary POJO
     * b.mazey@nyu.edu
     */

    private String[] words;

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    /**
     * Check word if present in the dictionary
     * @param word
     * @return
     */
    public String[] checkForWord(String[] word) {
        ArrayList<String> temp = new ArrayList<String>();
        temp.addAll(Arrays.asList(words));
        String regex = "\\d+";
        for (String w : temp) {
            String[] test_array = w.split("");
            boolean word_found = true;
            if (test_array.length == word.length) {
                for (int i = 0 ; i< test_array.length; i++) {
                    if (word[i].matches(regex)) {
                        continue;
                    } else {
                        if (!test_array[i].equalsIgnoreCase(word[i])){
                            word_found = false;
                            break;
                        }
                    }
                }
                if(word_found) {
                    return w.split("");
                }
            }
        }
        return null;
    }

}
