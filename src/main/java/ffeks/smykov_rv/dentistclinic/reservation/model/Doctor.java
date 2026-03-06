package ffeks.smykov_rv.dentistclinic.reservation.model;

import ffeks.smykov_rv.dentistclinic.security.model.UserAccount;
import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "reservations", name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @Column(name = "speciality", nullable = false)
    private String speciality;

    @Column(name = "experience", nullable = false)
    private int experience;
}
