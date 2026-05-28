package com.sberhealth.family_access.controller;

import com.sberhealth.family_access.entity.FamilyGroup;
import com.sberhealth.family_access.entity.FamilyMember;
import com.sberhealth.family_access.entity.Profile;
import com.sberhealth.family_access.service.FamilyService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/family")
public class FamilyController {
    private final FamilyService service;
    public FamilyController(FamilyService service) { this.service = service; }

    @PostMapping("/groups")
    public FamilyGroup createGroup(@RequestParam String name, @RequestParam Long creatorId) {
        return service.createGroup(name, creatorId);
    }

    @PostMapping("/groups/{groupId}/members")
    public String addMember(@PathVariable Long groupId,
                            @RequestParam Long profileId,
                            @RequestParam String roleName,
                            @RequestParam Long adderId) {
        service.addMember(groupId, profileId, roleName, adderId);
        return "Участник добавлен";
    }

    @GetMapping("/groups/{groupId}/members")
    public List<FamilyMember> getMembers(@PathVariable Long groupId) {
        return service.getFamilyMembers(groupId);
    }

    @GetMapping("/check")
    public boolean checkOrder(@RequestParam Long customerId, @RequestParam Long targetId) {
        return service.canOrderFor(customerId, targetId);
    }

    @GetMapping("/allowed-targets")
    public List<Profile> getAllowedTargets(@RequestParam Long customerId) {
        return service.getAllowedTargets(customerId);
    }
}