package com.example.lineupmanager.service;

import com.example.lineupmanager.domain.Team;
import jakarta.validation.constraints.NotNull;

public record TeamDTO
    (
            @NotNull String name,
            @NotNull LeagueDTO league,
            @NotNull Integer foundingYear,
            @NotNull String country,
            @NotNull String city
    )
{
    public static TeamDTO fromEntity(Team entity){
        return new TeamDTO(
                entity.getName(),
                entity.getLeagueDTO(),
                entity.getFoundingYear(),
                entity.getCountry(),
                entity.getCity()
        );
    }

    public Team toEntity(){
        return Team.builder()
                .name(this.name)
                .league(this.league.toEntity())
                .foundingYear(this.foundingYear)
                .country(this.country)
                .city(this.city)
                .build();
    }
}
