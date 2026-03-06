package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.DoctorDto;
import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-06T13:29:56+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class ReservationMappingImpl implements ReservationMapping {

    @Override
    public ReservationDto toReservationDto(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setId( reservation.getId() );
        reservationDto.setReservationDescription( reservation.getReservationDescription() );
        reservationDto.setReservationDate( reservation.getReservationDate() );
        reservationDto.setStartTime( reservation.getStartTime() );
        reservationDto.setEndTime( reservation.getEndTime() );
        reservationDto.setDoctor( doctorToDoctorDto( reservation.getDoctor() ) );

        return reservationDto;
    }

    @Override
    public Reservation toEntity(ReservationDto reservationDto) {
        if ( reservationDto == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setId( reservationDto.getId() );
        reservation.setReservationDescription( reservationDto.getReservationDescription() );
        reservation.setReservationDate( reservationDto.getReservationDate() );
        reservation.setStartTime( reservationDto.getStartTime() );
        reservation.setEndTime( reservationDto.getEndTime() );
        reservation.setDoctor( doctorDtoToDoctor( reservationDto.getDoctor() ) );

        return reservation;
    }

    protected DoctorDto doctorToDoctorDto(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setId( doctor.getId() );
        doctorDto.setSpeciality( doctor.getSpeciality() );
        doctorDto.setExperience( String.valueOf( doctor.getExperience() ) );

        return doctorDto;
    }

    protected Doctor doctorDtoToDoctor(DoctorDto doctorDto) {
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
