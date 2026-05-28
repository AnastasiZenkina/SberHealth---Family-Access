package com.sberhealth.family_access.service;

import com.sberhealth.family_access.entity.*;
import com.sberhealth.family_access.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyService {
    private final FamilyGroupRepository groupRepo;
    private final FamilyMemberRepository memberRepo;
    private final ProfileRepository profileRepo;
    private final FamilyRoleRepository roleRepo;

    public FamilyService(FamilyGroupRepository groupRepo, FamilyMemberRepository memberRepo,
                         ProfileRepository profileRepo, FamilyRoleRepository roleRepo) {
        this.groupRepo = groupRepo;
        this.memberRepo = memberRepo;
        this.profileRepo = profileRepo;
        this.roleRepo = roleRepo;
    }

    @Transactional
    public FamilyGroup createGroup(String name, Long creatorProfileId) {
        Profile creator = profileRepo.findById(creatorProfileId)
                .orElseThrow(() -> new RuntimeException("Профиль не найден"));
        FamilyRole parentRole = roleRepo.findByName("PARENT")
                .orElseThrow(() -> new RuntimeException("Роль PARENT не найдена"));

        FamilyGroup group = new FamilyGroup();
        group.setName(name);
        group.setCreatedBy(creatorProfileId);
        group = groupRepo.save(group);

        FamilyMember member = new FamilyMember();
        member.setFamilyGroup(group);
        member.setProfile(creator);
        member.setRole(parentRole);
        memberRepo.save(member);

        return group;
    }

    @Transactional
    public void addMember(Long groupId, Long profileId, String roleName, Long adderProfileId) {
        FamilyGroup group = groupRepo.findById(groupId).orElseThrow();
        Profile newMember = profileRepo.findById(profileId).orElseThrow();
        FamilyRole role = roleRepo.findByName(roleName).orElseThrow();

        boolean canAdd = memberRepo.findByFamilyGroupId(groupId).stream()
                .filter(m -> m.getProfile().getId().equals(adderProfileId))
                .anyMatch(m -> m.getRole().isCanOrderForOthers());

        if (!canAdd) throw new RuntimeException("Нет прав добавлять");
        if (memberRepo.existsByFamilyGroupIdAndProfileId(groupId, profileId))
            throw new RuntimeException("Уже в семье");

        FamilyMember member = new FamilyMember();
        member.setFamilyGroup(group);
        member.setProfile(newMember);
        member.setRole(role);
        memberRepo.save(member);
    }

    public List<FamilyMember> getFamilyMembers(Long groupId) {
        return memberRepo.findByFamilyGroupId(groupId);
    }

    public boolean canOrderFor(Long customerProfileId, Long targetProfileId) {
        List<FamilyMember> customerMembers = memberRepo.findAll().stream()
                .filter(m -> m.getProfile().getId().equals(customerProfileId) && m.getRole().isCanOrderForOthers())
                .toList();
        if (customerMembers.isEmpty()) return false;
        for (FamilyMember cm : customerMembers) {
            Long groupId = cm.getFamilyGroup().getId();
            boolean targetOk = memberRepo.findByFamilyGroupId(groupId).stream()
                    .anyMatch(m -> m.getProfile().getId().equals(targetProfileId) && m.getRole().isCanBeOrderedFor());
            if (targetOk) return true;
        }
        return false;
    }

    public List<Profile> getAllowedTargets(Long customerId) {
        List<FamilyMember> customerRoles = memberRepo.findAll().stream()
                .filter(m -> m.getProfile().getId().equals(customerId) && m.getRole().isCanOrderForOthers())
                .toList();

        List<Profile> targets = new ArrayList<>();
        for (FamilyMember cm : customerRoles) {
            Long groupId = cm.getFamilyGroup().getId();
            List<Profile> groupTargets = memberRepo.findByFamilyGroupId(groupId).stream()
                    .filter(m -> m.getRole().isCanBeOrderedFor())
                    .map(FamilyMember::getProfile)
                    .toList();
            targets.addAll(groupTargets);
        }
        return targets.stream().distinct().toList();
    }
}