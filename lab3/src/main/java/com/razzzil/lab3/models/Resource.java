package com.razzzil.lab3.models;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Resource {
    private int id;
    private String name;
    private String url;
    private Set<Role> availableRoles;

    public static Resource getInitialResource(){
        return Resource.builder()
                .name(null)
                .url(null)
                .availableRoles(new HashSet<>())
                .build();
    }

}
