package ffeks.smykov_rv.dentistclinic.reservation.service.impl;

import ffeks.smykov_rv.dentistclinic.reservation.model.Administrator;
import ffeks.smykov_rv.dentistclinic.reservation.repository.AdminRepository;
import ffeks.smykov_rv.dentistclinic.reservation.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Administrator findAdministratorById(Long id) {
        if (adminRepository.findAdministratorById(id).isPresent()) {
            return adminRepository.findAdministratorById(id).get();
        }
        else{
            throw new RuntimeException("Administrator not found");
        }
    }

    @Override
    public List<Administrator> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Administrator findAdministratorByPhoneNumber(String phoneNumber) {
        log.error("find administrator by phone number {}", phoneNumber);
        Optional<Administrator> administrator = adminRepository.getAdministratorByUserAccountPhoneNumber(phoneNumber);
        log.error("administrator {}", administrator);
        if (administrator.isPresent()) {
            return administrator.get();
        }
        else {
            throw new RuntimeException("Administrator not found");
        }
    }
}
