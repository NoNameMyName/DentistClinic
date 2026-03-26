package ffeks.smykov_rv.dentistclinic.reservation.service.impl;

import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import ffeks.smykov_rv.dentistclinic.reservation.model.Location;
import ffeks.smykov_rv.dentistclinic.reservation.repository.LocationsRepository;
import ffeks.smykov_rv.dentistclinic.reservation.service.AdminService;
import ffeks.smykov_rv.dentistclinic.reservation.service.DoctorService;
import ffeks.smykov_rv.dentistclinic.reservation.service.LocationsService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationServiceImpl  implements LocationsService {
    private final DoctorService doctorService;
    private final AdminService adminService;
    private final LocationsRepository locationsRepository;

    public LocationServiceImpl(DoctorService doctorService, AdminService adminService, LocationsRepository locationsRepository) {
        this.doctorService = doctorService;
        this.adminService = adminService;
        this.locationsRepository = locationsRepository;
    }


    @Override
    public Optional<Doctor> getDoctorByLocation(Long locationId, Long doctorId) {
        if (doctorService.existsDoctorById(doctorId) && locationsRepository.existsById(locationId)) {
            return locationsRepository.findDoctorByLocation(doctorId, locationId);
        }
        else  {
            return Optional.empty();
        }

    }

    @Override
    public boolean isExistById(Long locationId) {
        return locationsRepository.existsById(locationId);
    }

    @Override
    public Optional<Location> getLocationById(Long locationId) {
        return locationsRepository.findById(locationId);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationsRepository.findAllWithDoctorsAndAdministrators();
    }
}
