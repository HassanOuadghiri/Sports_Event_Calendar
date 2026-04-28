package com.sports.events.result;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Home goals is required")
    @Min(value = 0, message = "Home goals cannot be negative")
    @Column(name = "home_goals")
    private Integer homeGoals;

    @NotNull(message = "Away goals is required")
    @Min(value = 0, message = "Away goals cannot be negative")
    @Column(name = "away_goals")
    private Integer awayGoals;

    @Column(name = "winner")
    private String winner;

    @Column(name = "message")
    private String message;

    @Min(value = 0, message = "Goals cannot be negative")
    @Column(name = "goals")
    private Integer goals;

    @Min(value = 0, message = "Yellow cards cannot be negative")
    @Column(name = "yellow_cards")
    private Integer yellowCards;

    @Min(value = 0, message = "Second yellow cards cannot be negative")
    @Column(name = "second_yellow_cards")
    private Integer secondYellowCards;

    @Min(value = 0, message = "Direct red cards cannot be negative")
    @Column(name = "direct_red_cards")
    private Integer directRedCards;
}
