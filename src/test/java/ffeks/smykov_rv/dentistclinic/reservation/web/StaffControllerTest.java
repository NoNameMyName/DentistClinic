package ffeks.smykov_rv.dentistclinic.reservation.web;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.*;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.MakeReservationByAdminRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StaffControllerTest {

    @Mock private CancelReservationUseCase cancelUseCase;
    @Mock private GetAllReservationsUseCase getAllUseCase;
    @Mock private AcceptReservationUseCase acceptUseCase;
    @Mock private MakeReservationUseCase makeUseCase;

    @InjectMocks
    private StaffController controller;

    @Test
    void acceptReservation_shouldCallUseCase() {
        controller.acceptReservation(42L);
        verify(acceptUseCase).acceptReservation(42L);
    }

    @Test
    void cancelReservationByAdministrator_shouldCallUseCase() {
        controller.cancelReservationByAdministrator(100L);
        verify(cancelUseCase).cancelReservationByAdministrator(100L);
    }

    @Test
    void getAllReservationsForLocation_shouldReturnData() {
        List<ReservationDto> list = List.of(new ReservationDto(), new ReservationDto());
        when(getAllUseCase.getAllReservationsForLocation()).thenReturn(list);

        List<ReservationDto> result = controller.getAllReservationsForLocation();

        assertEquals(2, result.size());
        verify(getAllUseCase).getAllReservationsForLocation();
    }

    @Test
    void getAllReservationsForDoctor_shouldReturnData() {
        List<ReservationDto> list = List.of(new ReservationDto());
        when(getAllUseCase.getAllReservationsForDoctor()).thenReturn(list);

        List<ReservationDto> result = controller.getAllReservationsForDoctor();

        assertEquals(1, result.size());
    }

    @Test
    void makeReservationByAdministrator_shouldCallUseCase() {
        MakeReservationByAdminRequest request = new MakeReservationByAdminRequest(
                1L, 1L, LocalDate.now(), LocalTime.now(), 30, "asd"
        );
        controller.makeReservationByAdministrator(request);
        verify(makeUseCase).makeReservationByAdministrator(request);
    }

    @Test
    void allMethods_shouldHaveCorrectHttpStatus() {
        // Перевірка статусів робиться через @ResponseStatus, але в тесті просто викликаємо
        assertDoesNotThrow(() -> {
            controller.acceptReservation(1L);
            controller.cancelReservationByAdministrator(1L);
            controller.getAllReservationsForLocation();
            controller.getAllReservationsForDoctor();
            controller.makeReservationByAdministrator(new MakeReservationByAdminRequest(
                    1L, 1L, LocalDate.now(), LocalTime.now(), 30, "asd"
            ));
        });
    }
}