package com.kpi.magazines.beans;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by Konstantin Minkov on 19.06.2016.
 */

@Data
@ToString(exclude = "password")
public final class User {

    private int id;
    private String login;
    private String email;
    private String password;
    private int userRoleId;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
}
