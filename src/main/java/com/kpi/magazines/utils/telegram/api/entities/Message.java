package com.kpi.magazines.utils.telegram.api.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by Konstantin on 18.07.2016.
 */

@Data
public class Message {

    @SerializedName("message_id")
    private int id;
    private Chat chat;
    private User from;
    private int date;
    private String text;
}
