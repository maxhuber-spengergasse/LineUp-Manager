package com.example.lineupmanager.player.service;

import com.example.lineupmanager.player.domain.Position;
import jakarta.validation.constraints.NotNull;

public record PositionDTO
    (
            @NotNull String fieldPosition,
            @NotNull String detail
    )
{
    public static PositionDTO fromEntity(Position entity){
        return new PositionDTO(
                entity.getFieldPosition(),
                entity.getDetail()
        );
    }

    public Position toEntity(){
        return Position.builder()
                .fieldPosition(this.fieldPosition)
                .detail(this.detail)
                .build();
    }
}
