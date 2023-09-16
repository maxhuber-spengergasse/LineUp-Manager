package com.example.lineupmanager.service;

import com.example.lineupmanager.domain.Player;
import jakarta.validation.constraints.NotNull;

public record PlayerDTO
    (
            @NotNull String shortname,
            @NotNull String surname,
            @NotNull String lastname,
            @NotNull TeamDTO team,
            @NotNull Integer number,
            @NotNull PositionDTO position
    )
{
    public static PlayerDTO fromEntity(Player entity){
        return new PlayerDTO(
                entity.getShortname(),
                entity.getSurname(),
                entity.getLastname(),
                entity.getTeamDTO(),
                entity.getNumber(),
                entity.getPositionDTO()
        );
    }

    public Player toEntity(){
        return Player.builder()
                .shortname(this.shortname)
                .surname(this.surname)
                .lastname(this.lastname)
                .team(this.team.toEntity())
                .number(this.number)
                .position(this.position.toEntity())
                .build();
    }
}
