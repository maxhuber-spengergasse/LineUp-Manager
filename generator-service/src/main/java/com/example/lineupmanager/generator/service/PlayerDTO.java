package com.example.lineupmanager.generator.service;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PlayerDTO (
        @NotNull String shortname,
        @NotNull String surname,
        @NotNull String lastname,
        @NotNull String team,
        @NotNull Integer number,
        @NotNull String position
)
{
    public PlayerDTO (String shortname, String surname, String lastname, String team, Integer number, String position){
        this.shortname = shortname;
        this.surname = surname;
        this.lastname = lastname;
        this.team = team;
        this.number = number;
        this.position = position;
    }
}
