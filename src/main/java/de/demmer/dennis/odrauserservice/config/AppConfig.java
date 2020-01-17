package de.demmer.dennis.odrauserservice.config;

import de.demmer.dennis.odrauserservice.exception.AppException;
import de.demmer.dennis.odrauserservice.model.Role;
import de.demmer.dennis.odrauserservice.model.RoleName;
import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.repository.RoleRepository;
import de.demmer.dennis.odrauserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class AppConfig {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value(value = "${app.admin.pw}")
    String pw;


    @EventListener(ApplicationReadyEvent.class)
    public void handleContextRefresh() {


        if(!roleRepository.findByName(RoleName.ROLE_ADMIN).isPresent()){
            Role adminRole = new Role();
            adminRole.setName(RoleName.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }

        if(!roleRepository.findByName(RoleName.ROLE_USER).isPresent()){
            Role userRole = new Role();
            userRole.setName(RoleName.ROLE_USER);
            roleRepository.save(userRole);
        }

        if(!userRepository.findByUsername("admin").isPresent()) {
            // Creating admin account
            User user = new User("admin", "admin",
                    "admin@admin.com", pw);

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));

            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException("Admin Role not set."));

            Set<Role> roleSet = new HashSet<>();
            roleSet.add(userRole);
            roleSet.add(adminRole);

            user.setRoles(roleSet);

            User result = userRepository.save(user);
        }


    }
}
