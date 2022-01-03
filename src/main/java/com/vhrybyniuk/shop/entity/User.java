package com.vhrybyniuk.shop.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private String user_name;
    private String email;
    private String password;


    public enum ROLE {
        USER, ADMIN
    }
}
