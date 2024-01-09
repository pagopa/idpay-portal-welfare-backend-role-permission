package it.gov.pagopa.role.permission.connector.onetrust;

import it.gov.pagopa.role.permission.dto.onetrust.PrivacyNoticesDTO;

import java.time.LocalDateTime;

public interface OneTrustRestService {

    PrivacyNoticesDTO getPrivacyNotices(String id);
    PrivacyNoticesDTO getPrivacyNotices(String id, LocalDateTime date);
}
