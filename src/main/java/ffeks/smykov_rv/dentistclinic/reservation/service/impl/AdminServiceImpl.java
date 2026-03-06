package ffeks.smykov_rv.dentistclinic.reservation.service.impl;

import ffeks.smykov_rv.dentistclinic.reservation.model.Administrator;
import ffeks.smykov_rv.dentistclinic.reservation.repository.AdminRepository;
import ffeks.smykov_rv.dentistclinic.reservation.service.AdminService;
import org.springframework.stereotype.Service;

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
}
