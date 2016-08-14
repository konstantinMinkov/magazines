package com.kpi.magazines.utils.telegram.api.entities;

import lombok.Data;

/**
 * Created by Konstantin on 22.07.2016.
 */

@Data
public class InlineQuery {

    private String id;
    private User sender;
    private String query;
    private String offset;
}
