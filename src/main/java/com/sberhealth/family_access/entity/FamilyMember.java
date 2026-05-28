package com.sberhealth.family_access.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "family_member")
@Getter @Setter
@NoArgsConstructor
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_group_id")
    @JsonIgnoreProperties("members")
    private FamilyGroup familyGroup;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private FamilyRole role;
}