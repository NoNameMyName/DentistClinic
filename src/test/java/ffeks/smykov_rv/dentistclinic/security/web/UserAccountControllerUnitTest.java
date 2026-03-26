package ffeks.smykov_rv.dentistclinic.security.web;

import ffeks.smykov_rv.dentistclinic.security.usecase.RegisterUserAccountUseCase;
import ffeks.smykov_rv.dentistclinic.security.web.model.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserAccountControllerUnitTest {

    MockMvc mockMvc;

    @Mock
    RegisterUserAccountUseCase registerUserAccountUseCase;

    @InjectMocks
    private UserAccountController controller;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnStatusCreated() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(
                "Lyoha",
                "Aryonhan",
                "1234567890",
                "099999999"
        );

        doNothing()
                .when(registerUserAccountUseCase)
                .registerUserAccount(registerRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/accounts/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsBytes(registerRequest))
                ).andExpect(status().isCreated());

        verify(registerUserAccountUseCase, times(1))
                .registerUserAccount(any());
        verify(registerUserAccountUseCase, times(1))
                .registerUserAccount(registerRequest);
        verifyNoMoreInteractions(registerUserAccountUseCase);


    }

    @Test
    void shouldReturn4xxStatusWhenInvalidRegisterRequest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(
                "Lyoha",
                "Aryonhan",
                "1234567890",
                ""
        );

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/v1/accounts/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(registerRequest))
                )
                .andExpect(status().is4xxClientError())
                .andReturn();

        Exception exception = mvcResult.getResolvedException();

        assertNotNull(exception);
        assertInstanceOf(MethodArgumentNotValidException.class, exception);

        verify(registerUserAccountUseCase, never()).registerUserAccount(any());
    }
}