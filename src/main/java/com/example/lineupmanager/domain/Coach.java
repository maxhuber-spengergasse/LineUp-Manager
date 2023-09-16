package com.example.lineupmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import com.example.lineupmanager.service.TeamDTO;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "coach")
public class Coach extends AbstractPersistable<Integer> {

    @Column(unique = true)
    @NotNull
    private String shortname;

    @NotNull
    private String surname;

    @NotNull
    private String lastname;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    public TeamDTO getTeamDTO() { return TeamDTO.fromEntity(this.team); }
}
