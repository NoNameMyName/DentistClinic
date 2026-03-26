package ffeks.smykov_rv.dentistclinic.reservation.web;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.ReservationDto;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.CreateReservationUseCase;
import ffeks.smykov_rv.dentistclinic.reservation.usecase.GetAllReservationsUseCase;
import ffeks.smykov_rv.dentistclinic.reservation.web.model.ReservationRequest;
import ffeks.smykov_rv.dentistclinic.security.web.model.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DemoControllerUnitTest {
    MockMvc mockMvc;
    List<ReservationDto> reservationDtoList;
    LocalDate ldn;
    LocalTime ltn;
//    ReservationRequest  reservationRequest;
    @Mock
    CreateReservationUseCase createReservationUseCase;
    @Mock
    GetAllReservationsUseCase getAllReservationsUseCase;
    @InjectMocks
    DemoController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        reservationDtoList = new ArrayList<>();
        ReservationDto reservationDto = new ReservationDto();
        reservationDtoList.add(reservationDto);

        ldn = LocalDate.now();
        ltn = LocalTime.now();
    }

    @Test
    void shouldReturnListOfAllReservations() throws Exception{
        when(getAllReservationsUseCase.getAllReservations())
                .thenReturn(reservationDtoList);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reservation/all_reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(getAllReservationsUseCase, Mockito.times(1))
                .getAllReservations();
        verify(createReservationUseCase, Mockito.never())
                .createReservation(Mockito.any(ReservationRequest.class));
        verifyNoMoreInteractions(getAllReservationsUseCase);
        verifyNoMoreInteractions(createReservationUseCase);

    }

    @Test
    void shouldReturnEmptyListOfAllReservations() throws Exception{
        reservationDtoList.clear();
        when(getAllReservationsUseCase.getAllReservations())
                .thenReturn(reservationDtoList);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reservation/all_reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(getAllReservationsUseCase, Mockito.times(1))
                .getAllReservations();
        verify(createReservationUseCase, Mockito.never())
                .createReservation(Mockito.any(ReservationRequest.class));
        verifyNoMoreInteractions(getAllReservationsUseCase);
        verifyNoMoreInteractions(createReservationUseCase);

    }

    @Test
    void shouldReturnStatusCreatedForMakeReservation() throws Exception {

        ReservationRequest reservationRequest = new ReservationRequest(
                "1",
                LocalDate.of(2018, Month.APRIL, 1),
                LocalTime.of(12, 00),
                LocalTime.of(13, 00),
                1L,
                1L,
                2L
        );

        doNothing()
                .when(createReservationUseCase)
                .createReservation(reservationRequest);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/reservation/make_reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(reservationRequest))
        ).andExpect(status().isCreated());

        verify(createReservationUseCase, Mockito.times(1))
                .createReservation(any(ReservationRequest.class));
        verify(getAllReservationsUseCase, Mockito.never())
                .getAllReservations();
        verifyNoMoreInteractions(getAllReservationsUseCase);
        verifyNoMoreInteractions(createReservationUseCase);

    }

    @Test
    void shouldReturn4xxStatusWhenInvalidReservationRequest() throws Exception {
        ReservationRequest reservationRequest = new ReservationRequest(
                "",
                LocalDate.of(2018, Month.APRIL,1),
                LocalTime.of(12, 00),
                LocalTime.of(13, 00),
                1L,
                1L,
                2L
        );


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/reservation/make_reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(reservationRequest))
        ).andExpect(status().is4xxClientError()).andReturn();

        Exception exception = mvcResult.getResolvedException();
        assertNotNull(exception);
        assertInstanceOf(MethodArgumentNotValidException.class, exception);

        verify(createReservationUseCase, Mockito.never())
                .createReservation(any(ReservationRequest.class));
        verify(getAllReservationsUseCase, Mockito.never())
                .getAllReservations();
        verifyNoMoreInteractions(getAllReservationsUseCase);
        verifyNoMoreInteractions(createReservationUseCase);
    }
}