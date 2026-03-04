package ffeks.smykov_rv.dentistclinic.security.repository;

import ffeks.smykov_rv.dentistclinic.security.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByAuthority(String authority);
}
