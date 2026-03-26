package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DoctorMapping.class, AdministratorMapping.class, UserAccountMapper.class, LocationMapping.class})
public interface ReservationMapping {

    @Mapping(target = "userAccountDto", source = "userAccount") // Add this for direct UserAccount -> UserAccountDto
    @Mapping(target = "location", source = "location")
    @Mapping(target = "doctor", source = "doctor")
    @Mapping(target = "accepted", source = "accepted") // If spelling differs in JSON/entity
    @Mapping(target = "cancelled", source = "canceled") // Handle spelling (canceled vs cancelled)
    ReservationDto toReservationDto(Reservation reservation);
    Reservation toEntity(ReservationDto reservationDto);
}
