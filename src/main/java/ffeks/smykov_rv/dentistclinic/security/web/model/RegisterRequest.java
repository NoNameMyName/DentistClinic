package ffeks.smykov_rv.dentistclinic.security.web.model;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String password,

        @NotBlank
        String phoneNumber
) {
}
