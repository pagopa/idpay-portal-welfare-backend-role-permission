package it.gov.pagopa.dto.mapper;

import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.model.PortalConsent;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PortalConsentDTO2ModelMapper implements Function<PortalConsentDTO, PortalConsent> {

    @Override
    public PortalConsent apply(PortalConsentDTO portalConsentDTO) {
        return PortalConsent.builder()
                .userId(portalConsentDTO.getUserId())
                .acceptDate(portalConsentDTO.getAcceptDate())
                .versionId(portalConsentDTO.getVersionId())
                .build();
    }
}
