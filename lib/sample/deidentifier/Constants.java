package sample.deidentifier;

public class Constants {
//	public static String[] TRIGGER_WORDS = { "name", "mr.", "mrs.", "summary",
//			"mrn", "dr.", "m.d.", "mbbs", "physician", "md", "hospital",
//			"clinic", "institution", "transferred", "university", "memorial",
//			"department", "emergency", "discharge", "admission", "date", "am",
//			"pm", "call", "index", "no.", "ref", "#", "live", "lives", "on",
//			"morning", "evening", "today", "noon", "tr", "dd", "td", "cc",
//			"unit number", "pcc", "batch", "d", "t", };
//	public static String[] BIGRAMS = { "admitted to", "of medical",
//			"department at", "summary name", "transferred to", "discharged on",
//			"performed on", "admitted on", "registration date", "done on",
//			"lives in", "please call", "phone number", "hospital in",
//			"center in", "information please", "admission date",
//			"discharge date", };
//	public static String[] HEADERS = {};

	public static String[] TRIGGER_WORDS = { "name", "mr.", "mrs.", "summary",
			"dr.", "m.d.", "mbbs", "physician", "md", "hospital", "clinic",
			"institution", "transferred", "university", "memorial", "tel",
			"department", "emergency", "discharge", "admission", "date", "a.m",
			"p.m", "am", "pm", "call", "index", "no.", "ref", "#", "live",
			"lives", "on", "morning", "evening", "today", "noon", "night" };

	public static String[] HEADERS = { "admission date", "discharge date",
			"registration date", "discharge date / time :",
			"admission diagnosis", "history of present illness",
			"past medical history", "medications on admission",
			"physical examination", "laboratory data", "tr", "dd", "td",
			"discharge summary name", "unit number", "associated diagnosis",
			"allergies", "family history", "social history",
			"hospital course and treatment", "pcc", "mrn",
			"addendum to discharge summary", "discharge diagnosis",
			"hospital course", "attending", "batch", "d", "t",
			"discharge notification / summary", "entered by",
			"principal diagnosis", "other diagnosis", "habits", "disposition",
			"discharge medications", "cc", "room", "discharge patient on",
			"pcp name", "dictated by", "dictating for", "provider number", };

	public static String[] BIGRAMS = { "admitted to", "inpatient to",
			"of medical", "department at", "summary name", "transferred to",
			"discharged on", "performed on", "admitted on", "done on",
			"lives in", "please call", "phone number", "hospital in",
			"center in", "information please", "diagnosed in", "to the",
			"emergency department", "electronically signed",
			"appointment with", "number is", "medical center" };
}
