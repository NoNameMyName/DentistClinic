package ffeks.smykov_rv.dentistclinic.security.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.concurrent.TimeUnit.HOURS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class JwtAccessTokenServiceUnitTest {
    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private Authentication authentication;

    @Captor
    private ArgumentCaptor<JwtEncoderParameters> encoderParametersCaptor;

    @InjectMocks
    private JwtAccessTokenService jwtAccessTokenService;

    private static final String USERNAME = "patient123";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String SAMPLE_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";

    @Test
    @DisplayName("Повинен згенерувати валідний токен для користувача з ролями")
    void shouldGenerateTokenWithRolesAndCorrectClaims() {
        // given
        UserDetails userDetails = new User(
                USERNAME,
                "password-does-not-matter",
                List.of(
                        new SimpleGrantedAuthority(ROLE_USER),
                        new SimpleGrantedAuthority(ROLE_ADMIN)
                )
        );

        when(authentication.getPrincipal()).thenReturn(userDetails);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn(SAMPLE_TOKEN);
        when(jwtEncoder.encode(any())).thenReturn(jwt);

        // when
        String token = jwtAccessTokenService.generateIdToken(authentication);

        // then
        assertThat(token).isEqualTo(SAMPLE_TOKEN);

        // Перевіряємо, які claims були передані в JwtEncoder
        verify(jwtEncoder).encode(encoderParametersCaptor.capture());
        JwtClaimsSet claims = encoderParametersCaptor.getValue().getClaims();

        assertThat(claims.getSubject()).isEqualTo(USERNAME);
        assertThat(claims.getClaimAsString("scope"))
                .isEqualTo("[" + ROLE_ADMIN + ", " + ROLE_USER + "]");
        // зазвичай через пробіл
        assertThat(claims.getIssuedAt()).isNotNull();
        assertThat(claims.getExpiresAt()).isNotNull();

        Instant now = Instant.now();
        assertThat(claims.getExpiresAt())
                .isAfter(now.plus(13, ChronoUnit.HOURS))
                .isBefore(now.plus(15, ChronoUnit.HOURS)); // ~14 годин
    }

    @Test
    @DisplayName("Повинен кинути виняток коли principal не є UserDetails")
    void shouldThrowWhenPrincipalIsNotUserDetails() {

        // given
        when(authentication.getPrincipal()).thenReturn("string-not-user-details");

        // when + then
        assertThatThrownBy(() -> jwtAccessTokenService.generateIdToken(authentication))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Не вдалося сформувати об`єкт UserDetails");

        verifyNoInteractions(jwtEncoder); // токен не генерувався
    }

    @Test
    @DisplayName("Повинен коректно обробляти користувача без ролей")
    void shouldGenerateTokenForUserWithNoRoles() {
        // given
        UserDetails userDetails = new User(
                USERNAME,
                "pass",
                List.of() // порожній список
        );

        when(authentication.getPrincipal()).thenReturn(userDetails);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn(SAMPLE_TOKEN);
        when(jwtEncoder.encode(any())).thenReturn(jwt);

        // when
        String token = jwtAccessTokenService.generateIdToken(authentication);

        // then
        assertThat(token).isEqualTo(SAMPLE_TOKEN);

        verify(jwtEncoder).encode(encoderParametersCaptor.capture());
        JwtClaimsSet claims = encoderParametersCaptor.getValue().getClaims();

        Instant issuedAt  = claims.getIssuedAt();
        Instant expiresAt = claims.getExpiresAt();

        assertThat(issuedAt).isNotNull();
        assertThat(expiresAt).isNotNull();

        // Перевірка приблизно 14 годин
        assertThat(Duration.between(issuedAt, expiresAt))
                .isEqualTo(Duration.ofHours(14));

        // або з невеликим допуском (якщо є мілісекунди/секунди)
        // assertThat(Duration.between(issuedAt, expiresAt))
        //     .isBetween(Duration.ofHours(13).plusMinutes(59), Duration.ofHours(14).plusMinutes(1));
    }

    @Test
    @DisplayName("Повинен використовувати строкове представлення ролей через пробіл")
    void shouldJoinRolesWithSpaceInScopeClaim() {
        // given
        Set<GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("PATIENT"),
                new SimpleGrantedAuthority("CAN_BOOK")
        );

        UserDetails userDetails = new User(USERNAME, "xxx", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn(SAMPLE_TOKEN);
        when(jwtEncoder.encode(any())).thenReturn(jwt);

        // when
        jwtAccessTokenService.generateIdToken(authentication);

        // then
        verify(jwtEncoder).encode(encoderParametersCaptor.capture());
        String scope = encoderParametersCaptor.getValue().getClaims().getClaimAsString("scope");



        assertThat(scope).isEqualTo("[CAN_BOOK, PATIENT]");
    }

    @Test
    @DisplayName("Не повинен викликати encode якщо principal == null")
    void shouldThrowWhenPrincipalIsNull() {
        when(authentication.getPrincipal()).thenReturn(null);

        assertThatThrownBy(() -> jwtAccessTokenService.generateIdToken(authentication))
                .isInstanceOf(RuntimeException.class);

        verifyNoInteractions(jwtEncoder);
    }
}