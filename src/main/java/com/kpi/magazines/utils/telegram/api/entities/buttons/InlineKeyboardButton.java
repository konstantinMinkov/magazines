package com.kpi.magazines.utils.telegram.api.entities.buttons;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Konstantin on 22.07.2016.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InlineKeyboardButton {

    private String text;
    private String url;
    @SerializedName("callback_data")
    private String callbackData;
    @SerializedName("switch_inline_query")
    private String switchInlineQuery;
}
