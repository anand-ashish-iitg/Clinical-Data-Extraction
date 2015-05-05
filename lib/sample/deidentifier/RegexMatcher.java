package sample.deidentifier;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class RegexMatcher {
	private final static Pattern arabicRegex = Pattern
			.compile("^(?:[1-9](?:\\d{0,2})(?:,\\d{3})*(?:\\.\\d*[1-9])?|0?\\.\\d*[1-9]|0)$");
	private final static Pattern romanRegex = Pattern
			.compile("M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})");
	private final static Pattern[] ageRegex = {
			Pattern.compile("([9][0-9]|[1][0-3][0-9])y"),
			Pattern.compile("([9][0-9]|[1][0-3][0-9])([-](year))?([-](old))") };
	private final static Pattern[] idRegex = { Pattern
			.compile("[0-9]+/[a-z]+[0-9]+") };
	private final static Pattern[] phoneNumberRegex = {
			Pattern.compile("\\d{10}"),
			Pattern.compile("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"),
			Pattern.compile("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"),
			Pattern.compile("\\(\\d{3}\\)-\\d{3}-\\d{4}"),
			Pattern.compile("([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})|([\\+(]?(\\d){2,}[)]?[- \\.]?(\\d){2,}[- \\.]?(\\d){2,})") };
	private final static Pattern[] dateRegex = {
			Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|(?:january|jan|march|mar|may|june|jun|august|aug|october|oct|december|dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|(?:january|jan|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep|october|oct|november|nov|december|dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:february|feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|(?:january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep))|(?:1[0-2]|(?:october|oct|november|nov|december|dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"),
			Pattern.compile("((\\b(january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep|october|oct|november|nov|december|dec)\\b(\\sof)?(\\s(0?(1(st)?|2(nd)?|3(rd)?|[4-9](th)?)|[12](1(st)?|2(nd)?|3(rd)?|[4-9](th)?)|3(0(th)?|1(st)?)))?\\b(,?\\s(19|20)\\d\\d\\b)?)|(\\b((0?[1-9]|1[012])[- /.](0?[1-9]|[12][0-9]|3[01])([- /.](19|20)?\\d\\d))?\\b))"),
			Pattern.compile("((\\b(0?(1(st)?|2(nd)?|3(rd)?|[4-9](th)?)|[12](1(st)?|2(nd)?|3(rd)?|[4-9](th)?)|3(0(th)?|1(st)?))\\b(\\sof)?\\s(january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep|october|oct|november|nov|december|dec)\\b((,|(\\sof))?\\s(19|20)\\d\\d\\b)?)|(\\b((0?[1-9]|1[012])[- /.](0?[1-9]|[12][0-9]|3[01])([- /.](19|20)?\\d\\d))?\\b))"),
			Pattern.compile("(1[0-2]|0?[1-9])(/|-)(3[01]|[12][0-9]|0?[1-9])((/|-)([0-9]{4}))?"),
			Pattern.compile("(\b(0?(1(st)?|2(nd)?|3(rd)?|[4-9](th)?)|[12](1(st)?|2(nd)?|3(rd)?|[4-9](th)?)|3(0(th)?|1(st)?)))"),
			Pattern.compile("january|jan|february|feb|march|mar|april|apr|may|june|jun|july|jul|august|aug|september|sep|october|oct|november|nov|december|dec") };

	private static boolean patternMatch(String txt, Pattern[] patterns) {
		for (Pattern pattern : patterns) {
			if (pattern.matcher(txt).matches())
				return true;
		}
		return false;
	}

	public static boolean isArabicNumber(String s) {
		return !StringUtils.isBlank(s) && arabicRegex.matcher(s).matches();
	}

	public static boolean isRomanNumber(String s) {
		return !StringUtils.isBlank(s)
				&& romanRegex.matcher(s.toUpperCase()).matches();
	}

	public static boolean isAge(String s) {
		return !StringUtils.isBlank(s) && patternMatch(s.toLowerCase(), ageRegex);
	}

	public static boolean isID(String s) {
		return !StringUtils.isBlank(s)
				&& patternMatch(s.toLowerCase(), idRegex);
	}

	public static boolean isPhoneNumber(String s) {
		return !StringUtils.isBlank(s)
				&& patternMatch(s.toLowerCase(), phoneNumberRegex);
	}

	public static boolean isDate(String s) {
		return !StringUtils.isBlank(s)
				&& patternMatch(s.toLowerCase(), dateRegex);
	}
}
