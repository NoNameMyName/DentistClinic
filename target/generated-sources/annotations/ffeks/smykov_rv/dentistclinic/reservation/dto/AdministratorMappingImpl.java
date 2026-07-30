package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.AdministratorDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Administrator;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-28T23:41:18+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class AdministratorMappingImpl implements AdministratorMapping {

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public AdministratorDto toAdministratorDto(Administrator administrator) {
        if ( administrator == null ) {
            return null;
        }

        AdministratorDto administratorDto = new AdministratorDto();

        administratorDto.setUserAccountDto( userAccountMapper.toUserAccountDto( administrator.getUserAccount() ) );
        administratorDto.setId( administrator.getId() );

        return administratorDto;
    }

    @Override
    public Administrator toEntity(AdministratorDto administratorDto) {
        if ( administratorDto == null ) {
            return null;
        }

        Administrator administrator = new Administrator();

        administrator.setId( administratorDto.getId() );
        administrator.setUserAccount( administratorDto.getUserAccount() );

        return administrator;
    }
}
