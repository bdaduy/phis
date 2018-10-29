package edu.db.tool.deid.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Duy Bui
 */
public class StringFormat {

	

	public static String listToString(Collection<String> strings, String delimiter) {
		StringBuffer buf = new StringBuffer();
		for (String string : strings) {
			if (buf.length() > 0)
				buf.append(delimiter);
			buf.append(string);
		}
		return buf.toString();

	}

	public static String list2Regex(List<String> strings) {
		Collections.sort(strings, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {

				return o2.length() - o1.length();
			}
		});

		StringBuffer buf = new StringBuffer();
		for (String string : strings) {
			string = string.replaceAll("([\\*+\\[\\]\\^\\$\\|)(\\.?\\\\])", "\\\\$1");
			if (buf.length() > 0)
				buf.append("|");

			buf.append(string);
		}
		return buf.toString();

	}


	public static List<String> stringToList(String s, String delimiter) {
		List<String> items = new ArrayList<>();

		String[] parts = s.split(delimiter);
		for (String part : parts) {
			items.add(part);
		}

		return items;

	}

	public static String numberRound2(double s) {
		if (Double.isNaN(s))
			return "NaN";

		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(s);

		// return String.format("%."+numDecimal+"g%n",Double.parseDouble(s));
	}
        
        public static String numberRound(double s) {
		if (Double.isNaN(s))
			return "NaN";

		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(s);

		// return String.format("%."+numDecimal+"g%n",Double.parseDouble(s));
	}
	
	public static String numberRound4(double s) {
		if (Double.isNaN(s))
			return "NaN";

		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(s);

		// return String.format("%."+numDecimal+"g%n",Double.parseDouble(s));
	}

}
