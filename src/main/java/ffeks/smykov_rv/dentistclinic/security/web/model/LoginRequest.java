package ffeks.smykov_rv.dentistclinic.security.web.model;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String password,

        @NotBlank
        String phoneNumber
) {
}
