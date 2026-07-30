package ffeks.smykov_rv.dentistclinic.security.usecase;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.UserAccountDto;
import ffeks.smykov_rv.dentistclinic.security.web.model.UserAccountInfoRequest;

public interface GetUserAccountInfoUseCase {
    UserAccountDto getUserAccountInfo();
    UserAccountDto getUserAccountInfo(UserAccountInfoRequest request);
}
