package com.example.sehetak_bethakaa.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.sehetak_bethakaa.entity.Permission.*;

@RequiredArgsConstructor
public enum Role {
        GUEST(Set.of(TG_READ)),

        USER(Set.of(
                        TG_READ,
                        TG_CREATE,
                        TG_UPDATE,
                        TG_DELETE)),

        ADMIN(
                        Set.of(
                                        TG_READ,
                                        TG_CREATE,
                                        TG_UPDATE,
                                        TG_DELETE,
                                        TG_ADMIN)),

        MANAGER(
                        Set.of(
                                        TG_READ,
                                        TG_CREATE,
                                        TG_UPDATE,
                                        TG_DELETE,
                                        TG_MANAGER))

        ;

        @Getter
        private final Set<Permission> permissions;

        public List<SimpleGrantedAuthority> getAuthorities() {
                var authorities = getPermissions()
                                .stream()
                                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                                .collect(Collectors.toList());
                authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
                return authorities;
        }
}