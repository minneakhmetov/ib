package com.razzzil.lab4.models;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Role {
    GUEST, STUDENT, TEACHER, MANAGER, ADMIN;

    public static Set<Role> mapToSet(String[] roles){
        Set<Role> result = new HashSet<>();
        for (String temp : roles)
            result.add(mapToRole(temp));
        return result;
    }

    public static Role mapToRole(String role){
        for (Role temp: values())
            if (temp.name().equals(role))
                return temp;
        throw new IllegalArgumentException("Incorrect Role");
    }
}
