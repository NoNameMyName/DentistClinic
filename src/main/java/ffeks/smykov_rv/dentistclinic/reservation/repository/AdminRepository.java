package ffeks.smykov_rv.dentistclinic.reservation.repository;

import ffeks.smykov_rv.dentistclinic.reservation.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Administrator, Integer> {
    Optional<Administrator> findAdministratorById(Long id);

    Optional<Administrator> getAdministratorByUserAccountPhoneNumber(String userAccountPhoneNumber);
}
