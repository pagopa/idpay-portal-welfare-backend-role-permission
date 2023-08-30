package it.gov.pagopa.exception;

import it.gov.pagopa.common.web.exception.ClientExceptionWithBody;
import it.gov.pagopa.controller.consent.PortalConsentController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(RestExceptionHandler.class)
@AutoConfigureMockMvc
class RestExceptionHandlerTest {

    @MockBean
    private PortalConsentController consentController;

    @Autowired
    MockMvc mvc;

    @Test
    void testHandleException() throws Exception {
        Mockito.when(consentController.getPortalConsent("ClientException")).thenThrow(new ClientExceptionWithBody(HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value(),"Something went wrong"));

        mvc.perform(MockMvcRequestBuilders
                        .get("/idpay/consent")
                        .param("userId", "ClientException")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(r ->
                        Assertions.assertEquals(
                                "{\"code\":404,\"message\":\"Something went wrong\"}",
                                r.getResponse().getContentAsString())
                );
    }
}