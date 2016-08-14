package com.kpi.magazines.utils.validation.validators;

/**
 * Created by Konstantin Minkov on 21.07.2016.
 */
public class ValidatorProvider {

    public static Validator getLogInValidator(String languageTag) {
        return new LogInValidator(languageTag);
    }

    public static Validator getSignUpValidator(String languageTag) {
        return new SignUpValidator(languageTag);
    }

    public static Validator getEditionModificationValidator(String languageTag) {
        return new EditionModificationValidator(languageTag);
    }
}
