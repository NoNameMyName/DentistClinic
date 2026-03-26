package ffeks.smykov_rv.dentistclinic.reservation.web.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import ffeks.smykov_rv.dentistclinic.reservation.model.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(

        @NotBlank
        String reservationDescription,

        @NotNull
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate reservationDate,

        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,

        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime,

        @NotNull
        Long locationId,

        @NotNull
        Long doctorId,

        @NotNull
        Long userAccount


) {

}
