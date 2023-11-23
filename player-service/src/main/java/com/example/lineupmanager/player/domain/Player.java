package com.example.lineupmanager.player.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import com.example.lineupmanager.player.service.PositionDTO;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Player extends AbstractPersistable<Integer>{

    @Column(unique = true)
    @NotNull
    private String shortname;

    @NotNull
    private String surname;

    @NotNull
    private String lastname;

    private String team;

    @NotNull
    private Integer number;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Position position;

    public PositionDTO getPositionDTO() { return PositionDTO.fromEntity(this.position); }

}
