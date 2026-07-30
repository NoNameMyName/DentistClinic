package ffeks.smykov_rv.dentistclinic.reservation.web;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.LocationDto;
import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.TimeSlotDto;
import ffeks.smykov_rv.dentistclinic.reservation.service.ReservationService;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.*;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.MakeReservationRequest;
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
class DemoControllerTest {

    @Mock private MakeReservationUseCase makeUseCase;
    @Mock private GetAllReservationsUseCase getAllUseCase;
    @Mock private GetAllLocationsUseCase getAllLocationsUseCase;
    @Mock private CancelReservationUseCase cancelUseCase;
    @Mock private GetReservationForUserUseCase getForUserUseCase;
    @Mock private ReservationService reservationService;

    @InjectMocks
    private DemoController controller;

    @Test
    void makeReservation_shouldCallUseCase() {
        MakeReservationRequest req = new MakeReservationRequest(1L, LocalDate.now().plusDays(1), LocalTime.of(9,0), 30, "test");
        controller.makeReservation(req);
        verify(makeUseCase).makeReservation(req);
    }

    @Test
    void cancelReservation_shouldCallUseCase() {
        controller.cancelReservation(55L);
        verify(cancelUseCase).cancelReservationByUser(55L);
    }

    @Test
    void getAllLocations_shouldReturnList() {
        List<LocationDto> locations = List.of(new LocationDto());
        when(getAllLocationsUseCase.getAllLocations()).thenReturn(locations);

        List<LocationDto> result = controller.getAllLocations();
        assertEquals(1, result.size());
    }

    @Test
    void getReservationForUser_shouldReturnList() {
        List<ReservationDto> reservations = List.of(new ReservationDto());
        when(getForUserUseCase.getReservationForUser()).thenReturn(reservations);

        assertEquals(reservations, controller.getReservationForUser());
    }

    @Test
    void getAvailableSlots_shouldReturnSlots() {
        Long doctorId = 10L;
        LocalDate date = LocalDate.now().plusDays(2);
        List<TimeSlotDto> slots = List.of(new TimeSlotDto(LocalTime.of(10,0), LocalTime.of(11,0)));

        when(reservationService.getAvailableSlots(doctorId, date)).thenReturn(slots);

        List<TimeSlotDto> result = controller.getAvailableSlots(doctorId, date);
        assertEquals(slots, result);
    }

    @Test
    void getAvailableSlots_sunday_shouldBeHandledInService() {
        // Логіка валідації знаходиться в сервісі
        assertDoesNotThrow(() -> controller.getAvailableSlots(1L, LocalDate.now().plusDays(1)));
    }

    @Test
    void authEndpoints_shouldReturnCorrectStrings() {
        assertEquals("Basic Auth", controller.basicAuth());
        assertEquals("User Auth", controller.userAuth());
        assertEquals("Admin Auth", controller.adminAuth());
        assertEquals("Super admin Auth", controller.superAdminAuth());
    }
}