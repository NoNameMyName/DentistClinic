package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.DoctorDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-28T23:41:18+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class DoctorMappingImpl implements DoctorMapping {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public DoctorDto toDoctorDto(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setUserAccountDto( userAccountMapper.toUserAccountDto( doctor.getUserAccount() ) );
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
        doctor.setUserAccount( doctorDto.getUserAccount() );
        doctor.setSpeciality( doctorDto.getSpeciality() );
        if ( doctorDto.getExperience() != null ) {
            doctor.setExperience( Integer.parseInt( doctorDto.getExperience() ) );
        }

        return doctor;
    }
}
