package com.sberhealth.family_access.repository;

import com.sberhealth.family_access.entity.FamilyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Long> {
}