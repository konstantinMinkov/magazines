package com.kpi.magazines.utils.telegram.api.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by Konstantin on 18.07.2016.
 */

@Data
public class Chat {

    private int id;

    @SerializedName("first_name")
    private String firstName;
    private String type;
}
