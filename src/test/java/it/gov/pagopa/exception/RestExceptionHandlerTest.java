package it.gov.pagopa.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.gov.pagopa.BaseIntegrationTest;
import it.gov.pagopa.controller.consent.PortalConsentController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

class RestExceptionHandlerTest extends BaseIntegrationTest {

    @MockBean
    private PortalConsentController consentController;

    @Autowired
    MockMvc mvc;

    @Test
    void testHandleException() throws Exception {
        Mockito.when(consentController.getPortalConsent("ClientException")).thenThrow(new ClientException(HttpStatus.NOT_FOUND));

        mvc.perform(MockMvcRequestBuilders
                .get("/idpay/consent")
                .param("uid", "ClientException")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}