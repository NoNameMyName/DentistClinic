package ffeks.smykov_rv.dentistclinic.reservation.web;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.service.ReservationService;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.CreateReservationUseCase;
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

    public DemoController(ReservationService reservationService, CreateReservationUseCase createReservation, GetAllReservationsUseCase getAllReservations) {

        this.createReservationUseCase = createReservation;
        this.getAllReservationsUseCase = getAllReservations;
    }

    @GetMapping("/all_reservations")
    public List<ReservationDto> allReservations() {
        return getAllReservationsUseCase.getAllReservations();
    }

    @PostMapping("/make_reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public void maleReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        createReservationUseCase.createReservation(reservationRequest);
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
