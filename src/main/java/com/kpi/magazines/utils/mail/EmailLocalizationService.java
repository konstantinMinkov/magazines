package com.kpi.magazines.utils.mail;

import com.kpi.magazines.utils.Localizations;
import org.apache.velocity.VelocityContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Konstantin Minkov on 31.07.2016.
 *
 * Class, that transforms ResourceBundle localizations into VelocityContext and stores it for the next usages.
 */
public class EmailLocalizationService {

    private static final String BUNDLE_NAME = "email";
    private static final Map<String, VelocityContext> contexts;

    static {
        contexts = new HashMap<>();
        contexts.put(Localizations.RU, transform(Localizations.RU));
        contexts.put(Localizations.EN, transform(Localizations.EN));
    }

    public static VelocityContext getContext(String languageTag) {
        final VelocityContext context;
        if (languageTag == null) return contexts.get(Localizations.EN);
        context = contexts.get(languageTag);
        return context != null ? context : contexts.get(Localizations.RU);
    }

    private static VelocityContext transform(String languageTag) {
        final VelocityContext velocityContext = new VelocityContext();
        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME,
                Locale.forLanguageTag(languageTag));
        for (String key : bundle.keySet()) {
            velocityContext.internalPut(key, bundle.getString(key));
        }
        return velocityContext;
    }
}
