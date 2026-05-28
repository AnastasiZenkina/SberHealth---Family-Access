package com.sberhealth.family_access.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "family_role")
@Getter @Setter
@NoArgsConstructor
public class FamilyRole {
    @Id
    private Integer id;
    private String name;
    private boolean canOrderForOthers;
    private boolean canBeOrderedFor;
}