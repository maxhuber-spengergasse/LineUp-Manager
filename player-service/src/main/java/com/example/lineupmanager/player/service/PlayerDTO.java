package com.example.lineupmanager.player.service;

import com.example.lineupmanager.player.domain.Player;
import jakarta.validation.constraints.NotNull;

public record PlayerDTO
    (
            @NotNull String shortname,
            @NotNull String surname,
            @NotNull String lastname,
            @NotNull String team,
            @NotNull Integer number,
            @NotNull PositionDTO position
    )
{
    public static PlayerDTO fromEntity(Player entity){
        return new PlayerDTO(
                entity.getShortname(),
                entity.getSurname(),
                entity.getLastname(),
                entity.getTeam(),
                entity.getNumber(),
                entity.getPositionDTO()
        );
    }

    public Player toEntity(){
        return Player.builder()
                .shortname(this.shortname)
                .surname(this.surname)
                .lastname(this.lastname)
                .team(this.team)
                .number(this.number)
                .position(this.position.toEntity())
                .build();
    }
}
