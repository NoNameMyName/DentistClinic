package ffeks.smykov_rv.dentistclinic.security.web;

import ffeks.smykov_rv.dentistclinic.security.usecase.AuthentificationUseCase;
import ffeks.smykov_rv.dentistclinic.security.web.model.AccessToken;
import ffeks.smykov_rv.dentistclinic.security.web.model.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthentificationControllerTest {

    @Mock
    private AuthentificationUseCase authentificationUseCase;

    @InjectMocks
    private AuthentificationController controller;

    @Test
    void getToken_shouldReturnAccessToken() {
        LoginRequest request = new LoginRequest("user", "password");
        AccessToken expected = new AccessToken("jwt-token-123");

        when(authentificationUseCase.authenticate(request)).thenReturn(expected);

        AccessToken result = controller.getToken(request);

        assertEquals(expected, result);
        verify(authentificationUseCase).authenticate(request);
    }

    @Test
    void getUserRole_noAuthentication_shouldThrowNullPointerException() {
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(context);

        assertThrows(NullPointerException.class, controller::getUserRole);
    }

    @Test
    void getUserRole_emptyAuthorities_shouldReturnEmptyList() {
        Authentication auth = mock(Authentication.class);
        when(auth.getAuthorities()).thenReturn(List.of());

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        assertTrue(controller.getUserRole().isEmpty());
    }

    @Test
    void getUserRole_onlyScopes_shouldReturnEmptyList() {
        GrantedAuthority scope = () -> "SCOPE_write";

        Authentication auth = mock(Authentication.class);
        // Використовуємо thenAnswer — найстабільніше рішення для generics
        when(auth.getAuthorities()).thenAnswer(invocation -> List.of(scope));

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        assertTrue(controller.getUserRole().isEmpty());
    }

    @Test
    void getToken_shouldAcceptInvalidRequest_dueToValidation() {
        LoginRequest invalidRequest = new LoginRequest("", "");

        when(authentificationUseCase.authenticate(any(LoginRequest.class)))
                .thenReturn(new AccessToken("token"));

        assertDoesNotThrow(() -> controller.getToken(invalidRequest));
    }

    @Test
    void getUserRole_shouldHandleNullAuthorities() {
        Authentication auth = mock(Authentication.class);
        when(auth.getAuthorities()).thenReturn(null);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        assertThrows(NullPointerException.class, controller::getUserRole);
    }
}