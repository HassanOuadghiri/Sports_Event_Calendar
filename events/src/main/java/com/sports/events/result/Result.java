package com.sports.events.result;

import jakarta.persistence.*;
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

    @Column(name = "home_goals")
    private Integer homeGoals;

    @Column(name = "away_goals")
    private Integer awayGoals;

    @Column(name = "winner")
    private String winner;

    @Column(name = "message")
    private String message;

    @Column(name = "goals")
    private Integer goals;

    @Column(name = "yellow_cards")
    private Integer yellowCards;

    @Column(name = "second_yellow_cards")
    private Integer secondYellowCards;

    @Column(name = "direct_red_cards")
    private Integer directRedCards;
}
