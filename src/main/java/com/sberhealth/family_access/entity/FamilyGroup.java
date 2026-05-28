package com.sberhealth.family_access.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "family_group")
@Getter @Setter
@NoArgsConstructor
public class FamilyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long createdBy;

    @OneToMany(mappedBy = "familyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("familyGroup")
    private List<FamilyMember> members = new ArrayList<>();
}