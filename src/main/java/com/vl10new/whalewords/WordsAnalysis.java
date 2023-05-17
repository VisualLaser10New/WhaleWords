package com.vl10new.whalewords;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import emoji4j.EmojiUtils;

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

	public static String htmlSpecialEncode(String str)
	{
		str.replace("<", "&lt;");
		str.replace(">", "&gt;");
		str.replace("'", "&#39;");
		str.replace("\"", "&quot;");
		str.replace("&", "&amp;");
		return str;
	}

	public static ArrayList<String> phrasesWithWords(String filePath, ArrayList<String> words) throws FileNotFoundException, IOException
	{
		ArrayList<String> output = new ArrayList<>();
		FileReader file = new FileReader(filePath);

		BufferedReader myReader = new BufferedReader(file);
		int i = 0;

		String data = myReader.readLine();
		while (data != null)
		{
			i++;
			var val  = arrInStr(data, words);
			if(val.containsValue(true))
			{
				output.add(data + val.keySet());
			}
			data = myReader.readLine();
		}
		myReader.close();
		return output;
	}
	public static SortedSet<Map.Entry<String, Long>> occurences(String filePath, ArrayList<String> stopWords) throws FileNotFoundException, UnsupportedEncodingException {
		Map<String, Long> occorrenze = new TreeMap<>(); // trattiene il numero di occorrenze per ogni parola

		File file = new File(filePath);
		//Reader myReader = new InputStreamReader(new FileInputStream(file), "UTF-8"));

		Scanner myReader = new Scanner(file);

		while (myReader.hasNextLine())
		{
			String data = myReader.nextLine();
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
		myReader.close();

		SortedSet<Map.Entry<String, Long>> values = entriesSortedByValues(occorrenze);
		return values;
	}

	public static Map<String, Boolean> arrInStr(String text, ArrayList<String> arr)
	{
		//if the string contains an element of array
		//or an element of array contains the string
		for(String a : arr)
		{
			a=a.toLowerCase();
			text = text.toLowerCase();
			if(Pattern.compile("(?:^|[^a-zA-Z])"+a+"(?:[^a-zA-Z]|$)").matcher(text).matches())//|| a.contains(text))
			{
				var output = new HashMap<String, Boolean>();
				output.put(a, true);
				return output;
			}
		}
		return new HashMap<>();
	}

	private static Boolean acceptableWord(ArrayList<String> stopWords, String toCheck)
	{
		Boolean accept = true;
		//ampersand is to avoid the forgetting of previous checks

		accept &= toCheck.length() > 2; //set the condition which is true
		accept &= !isNumeric(toCheck); //means: it mustn't be numeric: is-not-numeric should be true
		accept &= !stopWords.contains(toCheck.toLowerCase());
		//accept &= !EmojiUtils.isEmoji(toCheck);
		accept &= !containsSpecialCharacter(toCheck);
		return accept;
	}

	private static boolean containsSpecialCharacter(String s) {
		return (s == null) ? false : s.matches("[^A-Za-z0-9]");
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
			//with htmlify is as slow as shit
			output.append("<li>'").append(EmojiUtils.htmlify(a.getKey())).append("' = ").append(EmojiUtils.htmlify(a.getValue().toString())).append("</li>");
			//output.append("<li>'").append(a.getKey()).append("' = ").append(a.getValue().toString()).append("</li>");
		}
		return output.toString();
	}

	public static <T> String StoString(ArrayList<T> values)
	{
		StringBuilder output = new StringBuilder();
		for(T o : values)
		{
			output.append("<li>'").append(htmlSpecialEncode(EmojiUtils.htmlify((String) o))).append("'</li>");
		}
		return output.toString();
	}
}