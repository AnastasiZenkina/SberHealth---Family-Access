package com.sberhealth.family_access;

import com.sberhealth.family_access.entity.FamilyRole;
import com.sberhealth.family_access.repository.FamilyRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final FamilyRoleRepository roleRepo;
    public DataLoader(FamilyRoleRepository roleRepo) { this.roleRepo = roleRepo; }

    @Override
    public void run(String... args) {
        if (roleRepo.count() == 0) {
            FamilyRole parent = new FamilyRole();
            parent.setId(1); parent.setName("PARENT");
            parent.setCanOrderForOthers(true); parent.setCanBeOrderedFor(false);
            roleRepo.save(parent);

            FamilyRole child = new FamilyRole();
            child.setId(2); child.setName("CHILD");
            child.setCanOrderForOthers(false); child.setCanBeOrderedFor(true);
            roleRepo.save(child);

            FamilyRole guardian = new FamilyRole();
            guardian.setId(3); guardian.setName("GUARDIAN");
            guardian.setCanOrderForOthers(true); guardian.setCanBeOrderedFor(true);
            roleRepo.save(guardian);

            System.out.println("Роли добавлены");
        }
    }
}