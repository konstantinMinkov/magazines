package com.kpi.magazines.tags;

import lombok.extern.log4j.Log4j2;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;


/**
 * Created by Konstantin Minkov on 20.07.2016.
 *
 * Tag that displays LocalDateTime due to locale.
 */

@Log4j2
public class DateTimeFormatterTag extends TagSupport {

    private String languageTag;
    private LocalDateTime dateTime;

    public void setLanguageTag(String languageTag) {
        this.languageTag = languageTag;
    }

    public void setDatetime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int doStartTag() {
        final DateTimeFormatter formatter;
        final Locale locale = Locale.forLanguageTag(languageTag);
        if (locale == null) {
            return SKIP_BODY;
        }
        formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)
                .withLocale(locale);
        try {
            pageContext.getOut().write(dateTime.format(formatter));
        } catch (IOException e) {
            log.catching(e);
        }
        return SKIP_BODY;
    }
}
