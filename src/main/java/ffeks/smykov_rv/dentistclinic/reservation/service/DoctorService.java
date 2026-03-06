package ffeks.smykov_rv.dentistclinic.reservation.service;

import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;

public interface DoctorService {
    Doctor findDoctorById(Long id);
    boolean existsDoctorById(Long id);
}
