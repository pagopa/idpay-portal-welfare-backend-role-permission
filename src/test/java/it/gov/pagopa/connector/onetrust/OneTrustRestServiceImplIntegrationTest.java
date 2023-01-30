/*package it.gov.pagopa.connector.onetrust;

import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OneTrustRestServiceImplIntegrationTest {
    private static final String EXPECTED_VERSION_ID = "625ba071-61b0-485f-81a0-a2245777b400";

    @Autowired
    private OneTrustRestService service;

    @Test
    void test() {
        String tosId = "TOSID_OK";

        PrivacyNoticesDTO result = service.getPrivacyNotices(tosId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(EXPECTED_VERSION_ID, result.getVersion().getId());
    }
}*/
