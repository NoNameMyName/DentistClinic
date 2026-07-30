package ffeks.smykov_rv.dentistclinic.security.web.model;

public record UserAccountInfoRequest(
        String phoneNumber,
        String firstName,
        String lastName
) {
}
