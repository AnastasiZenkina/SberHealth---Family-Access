package com.sberhealth.family_access.repository;

import com.sberhealth.family_access.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    List<FamilyMember> findByFamilyGroupId(Long groupId);
    boolean existsByFamilyGroupIdAndProfileId(Long groupId, Long profileId);
}