package com.kpi.magazines.dao.orm;

import com.kpi.magazines.dao.orm.annotations.Autogenerated;
import com.kpi.magazines.dao.orm.annotations.Column;
import com.kpi.magazines.dao.orm.annotations.Entity;
import com.kpi.magazines.dao.orm.annotations.Id;

/**
 * Created by Konstantin Minkov on 22.06.2016.
 */
@Entity(table = "user_role")
public class UserRole {

    @Id
    @Column("id")
    @Autogenerated
    private int id;

    @Column("role")
    private String role;

    public UserRole() {
    }

    public UserRole(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (id != userRole.id) return false;
        return role != null ? role.equals(userRole.role) : userRole.role == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
