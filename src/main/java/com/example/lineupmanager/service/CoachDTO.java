package com.example.lineupmanager.service;

import com.example.lineupmanager.domain.Coach;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CoachDTO
    (
            @NotNull  String shortname,
            @NotNull String surname,
            @NotNull String lastname,
            @NotNull TeamDTO team
    )
{

    public static CoachDTO fromEntity(Coach entity){
        return new CoachDTO(
                entity.getShortname(),
                entity.getSurname(),
                entity.getLastname(),
                entity.getTeamDTO()
        );
    }

    public Coach toEntity(){
        return Coach.builder()
                .shortname(this.shortname)
                .surname(this.surname)
                .lastname(this.lastname)
                .team(this.team.toEntity())
                .build();
    }
}
