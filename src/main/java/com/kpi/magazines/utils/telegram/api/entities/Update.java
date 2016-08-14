package com.kpi.magazines.utils.telegram.api.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by Konstantin on 18.07.2016.
 */
@Data
public class Update {

    @SerializedName("update_id")
    private int id;
    private Message message;

    @SerializedName("inline_query")
    private InlineQuery inlineQuery;
}
