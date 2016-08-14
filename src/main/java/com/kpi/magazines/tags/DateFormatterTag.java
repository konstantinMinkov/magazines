package com.kpi.magazines.tags;

import lombok.extern.log4j.Log4j2;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Created by Konstantin Minkov on 20.07.2016.
 *
 * Tag that displays LocalDate due to locale.
 */

@Log4j2
public class DateFormatterTag extends TagSupport {

    private String languageTag;
    private LocalDate date;

    public void setLanguageTag(String languageTag) {
        this.languageTag = languageTag;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int doStartTag() {
        final DateTimeFormatter formatter;
        final Locale locale = Locale.forLanguageTag(languageTag);
        if (locale == null) {
            return SKIP_BODY;
        }
        formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(locale);
        try {
            pageContext.getOut().write(date.format(formatter));
        } catch (IOException e) {
            log.catching(e);
        }
        return SKIP_BODY;
    }
}
