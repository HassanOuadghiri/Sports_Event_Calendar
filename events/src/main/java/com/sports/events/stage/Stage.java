package com.sports.events.stage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "stage")
public class Stage {

    @Id
    @NotBlank(message = "Stage ID is required")
    @Column(name = "id")
    private String id;

    @NotBlank(message = "Stage name is required")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Ordering is required")
    @Min(value = 1, message = "Ordering must be at least 1")
    @Column(name = "ordering")
    private Integer ordering;

}
