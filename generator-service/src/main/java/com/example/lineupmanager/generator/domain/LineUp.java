package com.example.lineupmanager.generator.domain;

import com.example.lineupmanager.generator.service.CoachDTO;
import com.example.lineupmanager.generator.service.PlayerDTO;
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
