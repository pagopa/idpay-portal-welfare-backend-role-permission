package it.gov.pagopa.role.permission.dto.mapper;

import it.gov.pagopa.role.permission.dto.PortalConsentDTO;
import it.gov.pagopa.role.permission.dto.onetrust.PrivacyNoticesDTO;
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
