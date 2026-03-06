package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.DoctorDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface DoctorMapping {
    DoctorDto toDoctorDto(Doctor doctor);
    Doctor toEntity(DoctorDto doctorDto);

}
