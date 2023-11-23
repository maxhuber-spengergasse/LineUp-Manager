package com.example.lineupmanager.coach.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

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

    private String team;
}
