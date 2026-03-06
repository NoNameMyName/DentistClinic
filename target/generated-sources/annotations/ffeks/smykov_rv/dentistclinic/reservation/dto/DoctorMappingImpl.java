package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.DoctorDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-06T13:29:56+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class DoctorMappingImpl implements DoctorMapping {

    @Override
    public DoctorDto toDoctorDto(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setId( doctor.getId() );
        doctorDto.setSpeciality( doctor.getSpeciality() );
        doctorDto.setExperience( String.valueOf( doctor.getExperience() ) );

        return doctorDto;
    }

    @Override
    public Doctor toEntity(DoctorDto doctorDto) {
        if ( doctorDto == null ) {
            return null;
        }

        Doctor doctor = new Doctor();

        doctor.setId( doctorDto.getId() );
        doctor.setSpeciality( doctorDto.getSpeciality() );
        if ( doctorDto.getExperience() != null ) {
            doctor.setExperience( Integer.parseInt( doctorDto.getExperience() ) );
        }

        return doctor;
    }
}
