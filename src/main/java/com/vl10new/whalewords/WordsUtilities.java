package com.vl10new.whalewords;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class WordsUtilities {
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
		str = str.replace("&", "&amp;").
				replace("<", "&lt;").
				replace(">", "&gt;").
				replace("'", "&#39;").
				replace("\"", "&quot;");
		return str;
	}

	public static Boolean regexMatch(String regex, String text)
	{
		Pattern pattern = Pattern.compile(regex);
		if(pattern.matcher(text).results().findAny().isPresent())
		{
			return true;
		}
		return false;
	}
	public static String arrInStrRegexComposer(ArrayList<String> arr)
	{
		StringBuilder regex = new StringBuilder();
		regex.append("(?:^|[^a-zA-Z])(");
		for(String a : arr)
		{
			a=a.toLowerCase();
			//regex.append("(").append(a).append(")|");
			regex.append(a).append("|");
		}
		regex.deleteCharAt(regex.length()-1);
		regex.append(")(?:[^a-zA-Z]|$)");
		return regex.toString();
	}

	protected static Boolean acceptableWord(ArrayList<String> stopWords, String toCheck)
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

	public static Map<String, Boolean> arrInStr(String text, ArrayList<String> arr)
	{
		//if the string contains an element of array
		//or an element of array contains the string
		for(String a : arr)
		{
			a=a.toLowerCase();
			text = text.toLowerCase();
			StringBuilder regex = new StringBuilder();
			regex.append("(?:^|[^a-zA-Z])").append(a).append("(?:[^a-zA-Z]|$)");
			Pattern pattern = Pattern.compile(regex.toString());

			if(pattern.matcher(text).results().findAny().isPresent())//|| a.contains(text))
			{
				var output = new HashMap<String, Boolean>();
				output.put(a, true);
				return output;
			}
		}
		return new HashMap<>();
	}

	public static int stringWeight(String text, Map<String, Integer> weightedWords)
	{
		var sent = sentimentAnalysis(text, weightedWords);
		AtomicInteger sum = new AtomicInteger();
		sent.forEach((a) -> sum.addAndGet(a.getValue()));
		return sum.get();
	}

	public static ArrayList<Pair<String, Integer>> sentimentAnalysis(String text, Map<String, Integer> weightedWords)
	{
		ArrayList<Pair<String,Integer>> output = new ArrayList<>();
		String[] tmp = text.split("[\\s+\\.+,?\\(\\)\\!;\\:\\-+\\\"\\\'\\/+\\&+]");
		for (String tempS : tmp)
		{
			tempS = tempS.toLowerCase();
			int val = weightedWords.containsKey(tempS) ? weightedWords.get(tempS) : 0;
			output.add(new MutablePair<>(tempS,val));
		}
		return output;
	}

	public static Map<String, Integer> fromArrayListoToHashMap(ArrayList<String> array, String sepChar)
	{
		HashMap<String, Integer> returnValue = null;
		for (String element : array)
		{
			String[] elementSplitted = element.split(sepChar);
			returnValue.put(elementSplitted[0], Integer.parseInt(elementSplitted[1]));
		}

		return returnValue;
	}
}
