package ffeks.smykov_rv.dentistclinic.security.service;

import ffeks.smykov_rv.dentistclinic.security.model.UserRole;

import java.util.Optional;

public interface UserRoleService {
    Optional<UserRole> findUserRole();
}
