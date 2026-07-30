package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.UserAccountDto;
import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-28T23:41:18+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class UserAccountMapperImpl implements UserAccountMapper {

    @Override
    public UserAccountDto toUserAccountDto(UserAccount userAccount) {
        if ( userAccount == null ) {
            return null;
        }

        UserAccountDto userAccountDto = new UserAccountDto();

        userAccountDto.setId( userAccount.getId() );
        userAccountDto.setFirstName( userAccount.getFirstName() );
        userAccountDto.setLastName( userAccount.getLastName() );
        userAccountDto.setNumber( userAccount.getPhoneNumber() );

        return userAccountDto;
    }

    @Override
    public UserAccount toEntity(UserAccountDto userAccountDto) {
        if ( userAccountDto == null ) {
            return null;
        }

        UserAccount userAccount = new UserAccount();

        userAccount.setId( userAccountDto.getId() );
        userAccount.setFirstName( userAccountDto.getFirstName() );
        userAccount.setLastName( userAccountDto.getLastName() );

        return userAccount;
    }
}
