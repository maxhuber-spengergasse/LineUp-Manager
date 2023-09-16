package com.example.lineupmanager.domain;

import com.example.lineupmanager.service.CoachDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import com.example.lineupmanager.service.LeagueDTO;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "team")
public class Team extends AbstractPersistable<Integer> {

    @Column(unique = true)
    @NotNull
    private String name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "team")
    private Coach coach;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> players;

    @ManyToOne(cascade = CascadeType.ALL)
    private League league;

    @NotNull
    private Integer foundingYear;

    @NotNull
    private String country;

    @NotNull
    private String city;

    public LeagueDTO getLeagueDTO() { return LeagueDTO.fromEntity(this.league); }
}
