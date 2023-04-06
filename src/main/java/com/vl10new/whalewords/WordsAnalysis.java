package com.vl10new.whalewords;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordsAnalysis {

    public enum ORDER
    {
        DESC,
        ASC
    }
    static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map)
    {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static SortedSet<Map.Entry<String, Long>> occurences(String filePath, ArrayList<String> stopWords) throws FileNotFoundException
    {
        Map<String, Long> occorrenze = new TreeMap<>(); // trattiene il numero di occorrenze per ogni parola

        File file = new File(filePath);

        Scanner myReader = new Scanner(file);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] tmp = data.split("[\\s\\.,?\\(\\)\\!;\\:\\-\\\"\\\'\\/\\&]");

            for (String word : tmp) {
                word = word.strip();
                if (!word.equalsIgnoreCase("") && acceptableWord(stopWords, word)) {
                    if (!occorrenze.containsKey(word)) {
                        occorrenze.put(word, Long.valueOf(1));
                    } else {
                        occorrenze.replace(word, occorrenze.get(word) + Long.valueOf(1));
                    }
                }
            }
        }
        myReader.close();

        SortedSet<Map.Entry<String, Long>> values = entriesSortedByValues(occorrenze);
        return values;
    }

    private static Boolean acceptableWord(ArrayList<String> stopWords, String toCheck)
    {
        Boolean accept = true;
        //ampersand is to avoid the forgetting of previous checks

        accept &= toCheck.length() > 2; //set the condition which is true
        accept &= !isNumeric(toCheck); //means: it mustn't be numeric: is-not-numeric should be true
        accept &= !stopWords.contains(toCheck.toLowerCase());
        return accept;
    }
    public static SortedSet<Map.Entry<String, Long>> removeWords(SortedSet<Map.Entry<String, Long>> values, ArrayList<String> toRemoveWords)
    {
        for (String word : toRemoveWords)
        {
            for(Map.Entry<String, Long> v : values){
                if(v.getKey().equals(word)){
                    values.remove(v);
                }
            }
        }

        return values;
    }

    public static SortedSet<Map.Entry<String, Long>> limitTo(SortedSet<Map.Entry<String, Long>> list, int limit, ORDER oreder)
    {
        //extract last [int limit] values
        SortedSet<Map.Entry<String, Long>> output = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );
        //set the direction through iterate the list
        Iterator<Map.Entry<String, Long>> it = oreder == ORDER.DESC ? list.iterator() : new TreeSet<>(list).descendingIterator();

        //prevent number bigger than list len
        limit = limit > list.size() ? list.size() : limit;

        //iterate list
        for (int i = 0; i < limit; i++) {
            if(!it.hasNext())
                return output;

            output.add(it.next());
        }
        return output;
    }

    public static String StoString(SortedSet<Map.Entry<String, Long>> values)
    {
        StringBuilder output = new StringBuilder();
        for(Map.Entry<String, Long> a : values)
        {
            output.append("<li>'").append(a.getKey()).append("' = ").append(a.getValue()).append("</li>");
        }
        return output.toString();
    }

    public static <T> String StoString(ArrayList<T> values)
    {
        StringBuilder output = new StringBuilder();
        for(T o : values)
        {
            output.append(o).append("<br>");
        }
        return output.toString();
    }
}