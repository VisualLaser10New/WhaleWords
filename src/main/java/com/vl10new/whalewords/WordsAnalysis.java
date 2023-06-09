package com.vl10new.whalewords;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import emoji4j.EmojiUtils;


public class WordsAnalysis extends WordsUtilities {

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

	public static ArrayList<String> phrasesWithWords(String filePath, ArrayList<String> words) throws FileNotFoundException, IOException
	{
		ArrayList<String> output = new ArrayList<>();

		String regex = arrInStrRegexComposer(words);
		for(String data : Utilities.readTextYield(filePath))
		{
			/*var val  = arrInStr(data, words);
			if(val.containsValue(true))
			{
				output.add(data + val.keySet());
			}*/

			if(regexMatch(regex, data))
			{
				output.add(data);
			}
		}
		return output;
	}
	public static SortedSet<Map.Entry<String, Long>> occurences(String filePath, ArrayList<String> stopWords) throws IOException {
		Map<String, Long> occorrenze = new TreeMap<>(); // trattiene il numero di occorrenze per ogni parola

		for(String data : Utilities.readTextYield(filePath))
		{
			String[] tmp = data.split("[\\s+\\.+,?\\(\\)\\!;\\:\\-+\\\"\\\'\\/+\\&+]");

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

		SortedSet<Map.Entry<String, Long>> values = entriesSortedByValues(occorrenze);
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
		limit = Math.min(limit, list.size());

		//iterate list
		for (int i = 0; i < limit; i++) {
			if(!it.hasNext())
				return output;

			output.add(it.next());
		}
		return output;
	}

	public static ArrayList<String> polarizeText(String textFile, Map<String, Integer> weightWords ) throws IOException {
		ArrayList<String> output = new ArrayList<>();
		for(String line : Utilities.readTextYield(textFile))
		{
			int val = WordsUtilities.stringWeight(line, weightWords);
			output.add(line + " " + String.valueOf(val));
		}
		return output;
	}

	public static String StoString(SortedSet<Map.Entry<String, Long>> values)
	{
		StringBuilder output = new StringBuilder();
		for(Map.Entry<String, Long> a : values)
		{
			//with htmlify is as slow as shit
			output.append("<li>'").append(a.getKey()).append("' = ").append(a.getValue().toString()).append("</li>");
			//output.append("<li>'").append(EmojiUtils.htmlify(a.getKey())).append("' = ").append(EmojiUtils.htmlify(a.getValue().toString())).append("</li>");
			//output.append("<li>'").append(a.getKey()).append("' = ").append(a.getValue().toString()).append("</li>");
		}
		return output.toString();
	}

	public static <T> String StoString(ArrayList<T> values)
	{
		StringBuilder output = new StringBuilder();
		for(T o : values)
		{
			output.append("<li>'").append(htmlSpecialEncode(o.toString())).append("'</li>");
			//output.append("<li>'").append(EmojiUtils.htmlify(htmlSpecialEncode(o.toString()))).append("'</li>");
		}
		return output.toString();
	}
}