package com.example.sehetak_bethakaa.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    TG_READ("tg:read"),
    TG_UPDATE("tg:update"),
    TG_CREATE("tg:create"),
    TG_DELETE("tg:delete"),
    TG_ADMIN("tg:admin"),
    TG_MANAGER("tg:manager"),
    ;

    @Getter
    private final String permission;
}