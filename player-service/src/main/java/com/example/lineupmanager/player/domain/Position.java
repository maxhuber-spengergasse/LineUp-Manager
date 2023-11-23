package com.example.lineupmanager.player.domain;

import com.example.lineupmanager.player.domain.Player;
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
public class Position extends AbstractPersistable<Integer>{

    @NotNull
    private String fieldPosition; // e.g. GK, Defender, Midfielder, Attacker

    @NotNull
    private String detail; // e.g. CB, RB, LB, CDM, CM, CAM, ST, LW, RW

    @OneToMany
    private List<Player> players;
}
