package com.example.lineupmanager.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class League extends AbstractPersistable<Integer>{

    @NotNull
    private String name;

    @NotNull
    private Integer division;

    @NotNull
    private String country;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Team> teams;

    @NotNull
    private Integer maxNumberTeams;
}
