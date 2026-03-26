package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.AdministratorDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Administrator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserAccountMapper.class})
public interface AdministratorMapping {
    @Mapping(target = "userAccountDto", source = "userAccount") // Maps userAccount -> userAccountDto using the nested mapper
    @Mapping(target = "userAccount", ignore = true)
//    @Mapping(target = "location", ignore = true)
    AdministratorDto toAdministratorDto(Administrator administrator);
    Administrator toEntity(AdministratorDto administratorDto);
}
