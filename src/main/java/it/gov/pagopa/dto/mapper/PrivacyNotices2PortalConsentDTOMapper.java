package it.gov.pagopa.dto.mapper;

import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import org.springframework.stereotype.Service;

@Service
public class PrivacyNotices2PortalConsentDTOMapper {

    public PortalConsentDTO apply(PrivacyNoticesDTO privacyNotices, boolean firstAcceptance) {
        return PortalConsentDTO.builder()
                .versionId(privacyNotices.getVersion().getId())
                .firstAcceptance(firstAcceptance)
                .build();
    }
}
