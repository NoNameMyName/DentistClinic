package ffeks.smykov_rv.dentistclinic.reservation.usecase;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.LocationDto;

import java.util.List;

public interface GetAllLocationsUseCase {
    List<LocationDto> getAllLocations();
}
