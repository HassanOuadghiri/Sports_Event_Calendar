package com.sports.events.team;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

        @NotBlank(message = "Team name is required")
        @Column(name = "name")
        private String name;

        @Column(name = "official_name")
        private String officialName;

        @Column(name = "slug")
        private String slug;

        @Size(max = 5, message = "Abbreviation must be at most 5 characters")
        @Column(name = "abbreviation")
        private String abbreviation;

        @Size(max = 3, message = "Country code must be at most 3 characters")
        @Column(name = "team_country_code")
        private String teamCountryCode;

        @Column(name = "stage_position")
        private Integer stagePosition;
}
