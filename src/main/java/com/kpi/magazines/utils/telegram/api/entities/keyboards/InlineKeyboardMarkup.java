package com.kpi.magazines.utils.telegram.api.entities.keyboards;

import com.google.gson.annotations.SerializedName;
import com.kpi.magazines.utils.telegram.api.entities.buttons.InlineKeyboardButton;
import lombok.Data;

/**
 * Created by Konstantin on 22.07.2016.
 */

@Data
public class InlineKeyboardMarkup implements ReplyMarkup {

    @SerializedName("inline_keyboard")
    private InlineKeyboardButton[][] inlineKeyboardButtons;
}
