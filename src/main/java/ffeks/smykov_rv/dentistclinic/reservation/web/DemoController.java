package ffeks.smykov_rv.dentistclinic.reservation.web;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.LocationDto;
import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Location;
import ffeks.smykov_rv.dentistclinic.reservation.service.LocationsService;
import ffeks.smykov_rv.dentistclinic.reservation.service.ReservationService;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.CreateReservationUseCase;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.GetAllLocationsUseCase;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.GetAllReservationsUseCase;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.ReservationRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class DemoController {

    private final CreateReservationUseCase  createReservationUseCase;
    private final GetAllReservationsUseCase getAllReservationsUseCase;
    private final GetAllLocationsUseCase getAllLocationsUseCase;
    private final LocationsService locationsService;

    public DemoController(CreateReservationUseCase createReservation, GetAllReservationsUseCase getAllReservations, GetAllLocationsUseCase getAllLocationsUseCase, LocationsService locationsService) {

        this.createReservationUseCase = createReservation;
        this.getAllReservationsUseCase = getAllReservations;
        this.getAllLocationsUseCase = getAllLocationsUseCase;
        this.locationsService = locationsService;
    }

    @GetMapping("/all_reservations")
    public List<ReservationDto> allReservations() {
        return getAllReservationsUseCase.getAllReservations();
    }

    @PostMapping("/make_reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        createReservationUseCase.createReservation(reservationRequest);
    }

    @GetMapping("/get_all_locations")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDto> getAllLocations() {
        return getAllLocationsUseCase.getAllLocations();

//        return locationsService.getAllLocations();
    }

    @GetMapping("/basic-auth")
    public String basicAuth() {
        return "Basic Auth";
    }

    @GetMapping("/user-auth")
    public String userAuth() {
        return "User Auth";
    }

    @GetMapping("/admin-auth")
    public String adminAuth() {
        return "Admin Auth";
    }


}
