package com.example.lawcasemaster.init;

import com.example.lawcasemaster.model.entity.Role;
import com.example.lawcasemaster.model.entity.UserEntity;
import com.example.lawcasemaster.model.enums.RoleType;
import com.example.lawcasemaster.repository.RoleRepository;
import com.example.lawcasemaster.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class initRolesAndAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public initRolesAndAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        long countRole = this.roleRepository.count();

        if (countRole != 0) {
            return;
        }
        Role admin = new Role();
        admin.setRoleType(RoleType.ADMIN);
        roleRepository.saveAndFlush(admin);
        Role lawyer = new Role();
        lawyer.setRoleType(RoleType.LAWYER);
        roleRepository.saveAndFlush(lawyer);


        long count = this.userRepository.count();


        if (count != 0) {
            return;
        }

        Role role = roleRepository.findByRoleType(RoleType.ADMIN);
        UserEntity user = new UserEntity();
        user.setUsername("Admin");
        user.setPassword(passwordEncoder.encode("adminspassword"));
        user.getRoles().add(role);
        user.setEmail("lawcasemaster@gmail.com");
        user.setPhoneNumber("0000000000");
        userRepository.save(user);

    }
}
