package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.UserAccountDto;
import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "number", source = "phoneNumber")
    UserAccountDto toUserAccountDto(UserAccount userAccount);
    UserAccount toEntity(UserAccountDto userAccountDto);
}
