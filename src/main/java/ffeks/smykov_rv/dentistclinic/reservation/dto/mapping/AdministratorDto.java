package ffeks.smykov_rv.dentistclinic.reservation.dto.mapping;
import com.fasterxml.jackson.annotation.JsonInclude;
import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class AdministratorDto {
    Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    UserAccount userAccount;
    UserAccountDto userAccountDto;
}
