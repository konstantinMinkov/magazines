package com.kpi.magazines.beans;

import lombok.*;

import java.time.LocalDate;

/**
 * Created by Konstantin Minkov on 22.06.2016.
 */

@Data
public final class Subscription {

    private int id;
    private int userId;
    private int editionId;
    private LocalDate dateCreated;
    private LocalDate dateEnding;
}
