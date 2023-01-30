package it.gov.pagopa.controller.consent;

import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.service.PortalConsentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@WebMvcTest(PortalConsentControllerImpl.class)
class PortalConsentControllerImplTest {

    //region String constants
    private static final String BASE_URL = "/idpay/consent/{userId}";
    private static final String USER_ID = "USER_ID";
    private static final String VERSION_ID = "VERSION_ID";
    //endregion

    @MockBean
    private PortalConsentService service;
    @Autowired
    private MockMvc mvc;

    @Test
    void testGetSuccess() throws Exception {
        Mockito.when(service.get(USER_ID)).thenReturn(Optional.empty());

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL, USER_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andReturn();
    }

    @Test
    void testGetOkNewVersion() throws Exception {
        PortalConsentDTO consent = new PortalConsentDTO(VERSION_ID);
        Mockito.when(service.get(USER_ID)).thenReturn(Optional.of(consent));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL, USER_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String consentString = "{\"versionId\":\"%s\"}".formatted(VERSION_ID);
        Assertions.assertEquals(consentString, result.getResponse().getContentAsString());
    }

    @Test
    void testSaveOk() throws Exception {
        String consentString = "{\"versionId\":\"%s\"}".formatted(VERSION_ID);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL, USER_ID)
                        .content(consentString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}