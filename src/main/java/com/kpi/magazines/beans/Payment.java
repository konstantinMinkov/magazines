package com.kpi.magazines.beans;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by Konstantin Minkov on 22.06.2016.
 */

@Data
public final class Payment {

    private int id;
    private int userId;
    private float price;
    private LocalDateTime timeCreated;
}
