package it.gov.pagopa.connector.onetrust;

import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;

import java.time.LocalDateTime;

public interface OneTrustRestService {

    PrivacyNoticesDTO getPrivacyNotices(String id);
    PrivacyNoticesDTO getPrivacyNotices(String id, LocalDateTime date);
}
