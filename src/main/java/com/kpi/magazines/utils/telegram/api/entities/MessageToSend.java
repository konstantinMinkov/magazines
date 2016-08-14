package com.kpi.magazines.utils.telegram.api.entities;

import com.google.gson.annotations.SerializedName;
import com.kpi.magazines.utils.telegram.api.entities.keyboards.ReplyMarkup;
import lombok.Data;

/**
 * Created by Konstantin on 18.07.2016.
 */

@Data
public class MessageToSend {

    @SerializedName("chat_id")
    private int chatId;
    private String text;

    @SerializedName("parse_mode")
    private String parseMode;

    @SerializedName("reply_markup")
    private ReplyMarkup markupReply;

    @SerializedName("disable_web_page_preview")
    private boolean disableWebPagePreview;
}
