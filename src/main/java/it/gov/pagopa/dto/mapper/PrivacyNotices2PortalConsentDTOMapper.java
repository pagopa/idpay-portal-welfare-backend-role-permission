package it.gov.pagopa.dto.mapper;

import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PrivacyNotices2PortalConsentDTOMapper implements Function<PrivacyNoticesDTO, PortalConsentDTO> {

    @Override
    public PortalConsentDTO apply(PrivacyNoticesDTO privacyNotices) {
        return PortalConsentDTO.builder()
                .versionId(privacyNotices.getVersion().getId())
                .build();
    }
}
