package it.gov.pagopa.connector.onetrust;

import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import it.gov.pagopa.dto.onetrust.PrivacyNoticesVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OneTrustRestServiceImplTest {

    private static final String TOS_ID = "TOS_ID";
    public static final String VERSION_ID = "VERSION_ID";

    @Mock
    private OneTrustRestClient restClient;
    private OneTrustRestService service;

    @BeforeEach
    void init() {
        service = new OneTrustRestServiceImpl(restClient);
    }

    @Test
    void test() {
        PrivacyNoticesDTO response = PrivacyNoticesDTO.builder()
                .version(
                        PrivacyNoticesVersion.builder()
                                .id(VERSION_ID).build()
                )
                .build();
        Mockito.when(restClient.getPrivacyNotices(Mockito.eq(TOS_ID), Mockito.anyString())).thenReturn(response);

        PrivacyNoticesDTO result = service.getPrivacyNotices(TOS_ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(response.getVersion().getId(), result.getVersion().getId());
    }
}