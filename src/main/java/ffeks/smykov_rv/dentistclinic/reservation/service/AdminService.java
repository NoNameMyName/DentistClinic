package ffeks.smykov_rv.dentistclinic.reservation.service;

import ffeks.smykov_rv.dentistclinic.reservation.model.Administrator;

public interface AdminService {
    Administrator findAdministratorById(Long id);
}
