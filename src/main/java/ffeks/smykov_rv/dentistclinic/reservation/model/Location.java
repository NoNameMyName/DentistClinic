package ffeks.smykov_rv.dentistclinic.reservation.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "reservations", name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_address")
    private String locationAddress;

    @Column(name = "work_time_start")
    private LocalTime workTimeStart;

    @Column(name = "work_time_end")
    private LocalTime workTimeEnd;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Doctor> doctors = new HashSet<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Administrator> administrators = new HashSet<>();

}
