package com.example.lineupmanager.generator.service;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CoachDTO (
        @NotNull String shortname,
        @NotNull String surname,
        @NotNull String lastname,
        @NotNull String team
)
{
    public CoachDTO (String shortname, String surname, String lastname, String team){
        this.shortname = shortname;
        this.surname = surname;
        this.lastname = lastname;
        this.team = team;
    }
}
