package ffeks.smykov_rv.dentistclinic.reservation.dto.mapping;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
public class DoctorDto {
    Long id;
    String firstName;
    String lastName;
    String speciality;
    String experience;
}
