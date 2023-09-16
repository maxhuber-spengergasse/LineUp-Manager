package com.example.lineupmanager.domain;

import com.example.lineupmanager.service.CoachDTO;
import com.example.lineupmanager.service.PlayerDTO;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LineUp {

    private CoachDTO coach;

    private List<PlayerDTO> players;
}
