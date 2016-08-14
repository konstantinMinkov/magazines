package com.kpi.magazines.utils.telegram.api.entities.buttons;

import com.google.gson.annotations.SerializedName;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Konstantin on 19.07.2016.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyboardButton {

    private String text;
    @SerializedName("request_contact")
    private boolean requestContact;
    @SerializedName("request_location")
    private boolean requestLocation;
}
