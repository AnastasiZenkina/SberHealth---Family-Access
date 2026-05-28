package com.sberhealth.family_access.repository;

import com.sberhealth.family_access.entity.FamilyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FamilyRoleRepository extends JpaRepository<FamilyRole, Integer> {
    Optional<FamilyRole> findByName(String name);
}