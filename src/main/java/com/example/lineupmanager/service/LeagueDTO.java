package com.example.lineupmanager.service;

import com.example.lineupmanager.domain.League;
import jakarta.validation.constraints.NotNull;

public record LeagueDTO
    (
    @NotNull String name,
    @NotNull Integer division,
    @NotNull String country,
    @NotNull Integer maxNumberTeams
    )
{
    public static LeagueDTO fromEntity(League entity){
        return new LeagueDTO(
                entity.getName(),
                entity.getDivision(),
                entity.getCountry(),
                entity.getMaxNumberTeams()
        );
    }

    public League toEntity(){
        return League.builder()
                .name(this.name)
                .division(this.division)
                .country(this.country)
                .maxNumberTeams(this.maxNumberTeams)
                .build();
    }
}
