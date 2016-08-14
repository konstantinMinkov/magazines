package com.kpi.magazines.utils.validation.validators;

import com.kpi.magazines.utils.validation.ErrorsHolder;
import com.sun.istack.internal.NotNull;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Konstantin Minkov on 22.07.2016.
 */

@Log4j2
public class EditionModificationValidator extends AbstractValidator {

    protected EditionModificationValidator(String languageTag) {
        super(languageTag);
    }

    protected EditionModificationValidator(@NotNull Locale locale) {
        super(locale);
    }

    @Override
    protected void validate(HttpServletRequest request) {
        final String categoryNameNew = request.getParameter("categoryNew");
        final String categoryNameSelect = request.getParameter("categorySelect");
        final String name = request.getParameter("name");
        final float price;
        final int editionsPerMonth;
        if (name == null || name.length() == 0) {
            errors.setError("general", errorsHolder.getError(ErrorsHolder.NULL_NAME));
            return;
        }
        if ((categoryNameSelect == null || categoryNameSelect.length() == 0)
                && (categoryNameNew == null || categoryNameNew.length() == 0)) {
            errors.setError("general", errorsHolder.getError(ErrorsHolder.NULL_CATEGORY));
            return;
        }
        try {
            price = Float.parseFloat(request.getParameter("price"));
            if (price < 0) throw new IllegalArgumentException();
        } catch (Exception e) {
            errors.setError("general", errorsHolder.getError(ErrorsHolder.INVALID_PRICE));
            return;
        }
        try {
            editionsPerMonth = Integer.parseInt(request.getParameter("editionsPerMonth"));
            if (editionsPerMonth < 1 || editionsPerMonth > 4) throw new IllegalArgumentException();
        } catch (Exception e) {
            errors.setError("general", errorsHolder.getError(ErrorsHolder.INVALID_EDITIONS_PER_MONTH));
            return;
        }
        try {
//            System.out.println(request.getPart("image").getSubmittedFileName());
            Object object = request.getPart("image");
            if (request.getPart("image").getSize() <= 0) {
                errors.setError("general", errorsHolder.getError(ErrorsHolder.NULL_IMAGE));
            }
        } catch (IOException | ServletException e) {
            log.catching(e);
        }
    }
}
