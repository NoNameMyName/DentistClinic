package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.AdministratorDto;
import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.DoctorDto;
import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.LocationDto;
import ffeks.smykov_rv.dentistclinic.reservation.model.Administrator;
import ffeks.smykov_rv.dentistclinic.reservation.model.Doctor;
import ffeks.smykov_rv.dentistclinic.reservation.model.Location;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-28T23:41:18+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class LocationMappingImpl implements LocationMapping {

    @Autowired
    private DoctorMapping doctorMapping;
    @Autowired
    private AdministratorMapping administratorMapping;

    @Override
    public LocationDto toLocationDto(Location location) {
        if ( location == null ) {
            return null;
        }

        LocationDto locationDto = new LocationDto();

        locationDto.setId( location.getId() );
        locationDto.setLocationAddress( location.getLocationAddress() );
        locationDto.setWorkTimeStart( location.getWorkTimeStart() );
        locationDto.setWorkTimeEnd( location.getWorkTimeEnd() );
        locationDto.setCity( location.getCity() );
        locationDto.setDistrict( location.getDistrict() );
        locationDto.setPhoneNumber( location.getPhoneNumber() );
        locationDto.setDoctors( doctorSetToDoctorDtoSet( location.getDoctors() ) );
        locationDto.setAdministrators( administratorSetToAdministratorDtoSet( location.getAdministrators() ) );

        return locationDto;
    }

    @Override
    public Location toEntity(LocationDto locationDto) {
        if ( locationDto == null ) {
            return null;
        }

        Location location = new Location();

        location.setId( locationDto.getId() );
        location.setLocationAddress( locationDto.getLocationAddress() );
        location.setPhoneNumber( locationDto.getPhoneNumber() );
        location.setCity( locationDto.getCity() );
        location.setDistrict( locationDto.getDistrict() );
        location.setWorkTimeStart( locationDto.getWorkTimeStart() );
        location.setWorkTimeEnd( locationDto.getWorkTimeEnd() );
        location.setDoctors( doctorDtoSetToDoctorSet( locationDto.getDoctors() ) );
        location.setAdministrators( administratorDtoSetToAdministratorSet( locationDto.getAdministrators() ) );

        return location;
    }

    protected Set<DoctorDto> doctorSetToDoctorDtoSet(Set<Doctor> set) {
        if ( set == null ) {
            return null;
        }

        Set<DoctorDto> set1 = new LinkedHashSet<DoctorDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Doctor doctor : set ) {
            set1.add( doctorMapping.toDoctorDto( doctor ) );
        }

        return set1;
    }

    protected Set<AdministratorDto> administratorSetToAdministratorDtoSet(Set<Administrator> set) {
        if ( set == null ) {
            return null;
        }

        Set<AdministratorDto> set1 = new LinkedHashSet<AdministratorDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Administrator administrator : set ) {
            set1.add( administratorMapping.toAdministratorDto( administrator ) );
        }

        return set1;
    }

    protected Set<Doctor> doctorDtoSetToDoctorSet(Set<DoctorDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Doctor> set1 = new LinkedHashSet<Doctor>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( DoctorDto doctorDto : set ) {
            set1.add( doctorMapping.toEntity( doctorDto ) );
        }

        return set1;
    }

    protected Set<Administrator> administratorDtoSetToAdministratorSet(Set<AdministratorDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Administrator> set1 = new LinkedHashSet<Administrator>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AdministratorDto administratorDto : set ) {
            set1.add( administratorMapping.toEntity( administratorDto ) );
        }

        return set1;
    }
}
