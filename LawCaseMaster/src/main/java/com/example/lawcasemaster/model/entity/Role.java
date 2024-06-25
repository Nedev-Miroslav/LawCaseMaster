package com.example.lawcasemaster.model.entity;

import com.example.lawcasemaster.model.enums.RoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Role extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType name) {
        this.roleType = name;
    }
}
