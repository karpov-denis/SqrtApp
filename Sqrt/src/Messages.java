import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$
	private static final String BUNDLE_NAME_RU = "messagesRu"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static final ResourceBundle RESOURCE_BUNDLE_RU = ResourceBundle.getBundle(BUNDLE_NAME_RU);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	public static String getStringLang(String lang, String key)
	{
		if (Objects.equals(lang, "en"))
			return getString(key);
		if(Objects.equals(lang, "ru"))
			return getStringRu(key);
		else
			return "CACHE_ERROR";
	}
	public static String getStringRu(String key) {
		try {
			return RESOURCE_BUNDLE_RU.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
