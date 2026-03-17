package ffeks.smykov_rv.dentistclinic.reservation.dto.mapping;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserAccountDto {
    Long id;
    String firstName;
    String lastName;
    String number;
}
