package com.kpi.magazines.utils.telegram.api.entities.keyboards;

import com.google.gson.annotations.SerializedName;
import com.kpi.magazines.utils.telegram.api.entities.buttons.KeyboardButton;
import lombok.Data;

/**
 * Created by Konstantin on 19.07.2016.
 */

@Data
public class ReplyKeyboardMarkup implements ReplyMarkup {

    private KeyboardButton[][] keyboard;

    @SerializedName("resize_keyboard")
    private boolean resizeKeyboard;

    @SerializedName("one_time_keyboard")
    private boolean oneTimeKeyboard;
    private boolean selective;
}
