package com.example.lineupmanager.coach.service;

import com.example.lineupmanager.coach.domain.Coach;
import jakarta.validation.constraints.NotNull;

public record CoachDTO
    (
            @NotNull  String shortname,
            @NotNull String surname,
            @NotNull String lastname,
            @NotNull String team
    )
{

    public static CoachDTO fromEntity(Coach entity){
        return new CoachDTO(
                entity.getShortname(),
                entity.getSurname(),
                entity.getLastname(),
                entity.getTeam()
        );
    }

    public Coach toEntity(){
        return Coach.builder()
                .shortname(this.shortname)
                .surname(this.surname)
                .lastname(this.lastname)
                .team(this.team)
                .build();
    }
}
