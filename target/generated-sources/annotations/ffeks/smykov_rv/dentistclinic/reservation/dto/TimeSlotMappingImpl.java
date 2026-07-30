package ffeks.smykov_rv.dentistclinic.reservation.dto;

import ffeks.smykov_rv.dentistclinic.reservation.buffer.impl.TimeSlotImpl;
import ffeks.smykov_rv.dentistclinic.reservation.dto.mapping.TimeSlotDto;
import java.time.LocalTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-28T23:41:18+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Oracle Corporation)"
)
@Component
public class TimeSlotMappingImpl implements TimeSlotMapping {

    @Override
    public TimeSlotImpl toEntity(TimeSlotDto dto) {
        if ( dto == null ) {
            return null;
        }

        LocalTime startTime = null;
        LocalTime endTime = null;

        startTime = dto.getStartTime();
        endTime = dto.getEndTime();

        boolean available = false;

        TimeSlotImpl timeSlotImpl = new TimeSlotImpl( startTime, endTime, available );

        return timeSlotImpl;
    }

    @Override
    public TimeSlotDto toDto(TimeSlotImpl timeSlot) {
        if ( timeSlot == null ) {
            return null;
        }

        LocalTime startTime = null;
        LocalTime endTime = null;

        startTime = timeSlot.getStartTime();
        endTime = timeSlot.getEndTime();

        TimeSlotDto timeSlotDto = new TimeSlotDto( startTime, endTime );

        return timeSlotDto;
    }
}
