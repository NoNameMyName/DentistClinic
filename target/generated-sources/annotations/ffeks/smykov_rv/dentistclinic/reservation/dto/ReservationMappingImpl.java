package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-28T23:41:18+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class ReservationMappingImpl implements ReservationMapping {

    @Autowired
    private DoctorMapping doctorMapping;
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private LocationMapping locationMapping;

    @Override
    public ReservationDto toReservationDto(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setUserAccountDto( userAccountMapper.toUserAccountDto( reservation.getUserAccount() ) );
        reservationDto.setLocation( locationMapping.toLocationDto( reservation.getLocation() ) );
        reservationDto.setDoctor( doctorMapping.toDoctorDto( reservation.getDoctor() ) );
        reservationDto.setAccepted( reservation.isAccepted() );
        reservationDto.setCancelled( reservation.isCanceled() );
        reservationDto.setId( reservation.getId() );
        reservationDto.setReservationDescription( reservation.getReservationDescription() );
        reservationDto.setReservationDate( reservation.getReservationDate() );
        reservationDto.setStartTime( reservation.getStartTime() );
        reservationDto.setEndTime( reservation.getEndTime() );

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
        reservation.setLocation( locationMapping.toEntity( reservationDto.getLocation() ) );
        reservation.setDoctor( doctorMapping.toEntity( reservationDto.getDoctor() ) );
        reservation.setAccepted( reservationDto.isAccepted() );

        return reservation;
    }
}
