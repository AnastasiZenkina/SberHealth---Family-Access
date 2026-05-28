package com.sberhealth.family_access.repository;

import com.sberhealth.family_access.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}