package ffeks.smykov_rv.dentistclinic.reservation.dto.mapping;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@Setter
public class ReservationDto {
    int id;
    String reservationDescription;
    LocalDate reservationDate;
    LocalTime startTime;
    LocalTime endTime;
    DoctorDto doctor;
}
