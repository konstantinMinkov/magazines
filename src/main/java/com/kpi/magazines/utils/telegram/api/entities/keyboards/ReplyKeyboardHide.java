package com.kpi.magazines.utils.telegram.api.entities.keyboards;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * Created by Konstantin on 22.07.2016.
 */

@ToString
@EqualsAndHashCode
public class ReplyKeyboardHide implements ReplyMarkup {

    @SerializedName("hide_keyboard")
    private final boolean hideKeyboard = true;

    @Getter
    @Setter
    private boolean selective;
}
