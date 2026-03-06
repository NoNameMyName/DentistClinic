package ffeks.smykov_rv.dentistclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DentistClinicApplication {

    public static void main(String[] args) {
        System.setProperty("user.timezone", "UTC");
        SpringApplication.run(DentistClinicApplication.class, args);
    }


}
