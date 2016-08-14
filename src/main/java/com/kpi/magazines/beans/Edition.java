package com.kpi.magazines.beans;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by Konstantin Minkov on 22.06.2016.
 */

@Data
public final class Edition {

    private int id;
    private String name;
    private int categoryId;
    private String description;
    private float price;
    private int editionsPerMonth;
    private String driveId;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
}
