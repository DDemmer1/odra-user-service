package de.demmer.dennis.odrauserservice.repository;

import de.demmer.dennis.odrauserservice.model.Role;
import de.demmer.dennis.odrauserservice.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}