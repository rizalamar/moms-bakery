package com.rizalamar.momsbakery.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "categories")
public class Category extends AbstractAuditingEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CategoryType name;

    private String description;
}
