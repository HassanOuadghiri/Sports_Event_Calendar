package com.sports.events.team;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "official_name")
    private String officialName;

    @Column(name = "slug")
    private String slug;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "team_country_code")
    private String teamCountryCode;

    @Column(name = "stage_position")
    private Integer stagePosition;
}
