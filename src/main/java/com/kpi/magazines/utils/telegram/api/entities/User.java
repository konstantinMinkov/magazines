package com.kpi.magazines.utils.telegram.api.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by Konstantin on 18.07.2016.
 */

@Data
public class User {

    @SerializedName("message_id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
}
