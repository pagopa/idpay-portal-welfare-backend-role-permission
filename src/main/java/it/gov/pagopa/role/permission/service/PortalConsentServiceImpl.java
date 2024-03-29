package it.gov.pagopa.role.permission.service;

import it.gov.pagopa.role.permission.exception.VersionNotMatchedException;
import it.gov.pagopa.role.permission.connector.onetrust.OneTrustRestService;
import it.gov.pagopa.role.permission.dto.PortalConsentDTO;
import it.gov.pagopa.role.permission.dto.mapper.PrivacyNotices2PortalConsentDTOMapper;
import it.gov.pagopa.role.permission.dto.onetrust.PrivacyNoticesDTO;
import it.gov.pagopa.role.permission.model.PortalConsent;
import it.gov.pagopa.role.permission.repository.PortalConsentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PortalConsentServiceImpl implements PortalConsentService {

    private final PortalConsentRepository portalConsentRepository;

    private final OneTrustRestService oneTrustRestService;

    private final PrivacyNotices2PortalConsentDTOMapper privacyNotices2PortalConsentDTOMapper;

    private final String tosId;

    private static final PortalConsentDTO EMPTY_PORTAL_CONSENT_DTO = new PortalConsentDTO();

    public PortalConsentServiceImpl(
            PortalConsentRepository portalConsentRepository,
            OneTrustRestService oneTrustRestService,
            PrivacyNotices2PortalConsentDTOMapper privacyNotices2PortalConsentDTOMapper,
            @Value("${app.rest-client.one-trust.service.privacy-notices.tosId}") String tosId) {
        this.portalConsentRepository = portalConsentRepository;
        this.oneTrustRestService = oneTrustRestService;
        this.privacyNotices2PortalConsentDTOMapper = privacyNotices2PortalConsentDTOMapper;
        this.tosId = tosId;
    }

    @Override
    public PortalConsentDTO get(String userId) {

        log.info("[CONSENTS] Fetching possible previously accepted consent");
        Optional<PortalConsent> optional = portalConsentRepository.findById(userId);

        PrivacyNoticesDTO privacyNotices = oneTrustRestService.getPrivacyNotices(tosId);

        if (optional.isPresent()) {
            PortalConsent consent = optional.get();
            if (consent.getVersionId().equals(privacyNotices.getVersion().getId())) {
                log.info("[CONSENTS] same version verified already accepted");
                return EMPTY_PORTAL_CONSENT_DTO;
            }
            log.info("[CONSENTS] new version found. User should accept again");
            return privacyNotices2PortalConsentDTOMapper.apply(privacyNotices, false);
        } else {
            return privacyNotices2PortalConsentDTOMapper.apply(privacyNotices, true);
        }
    }

    @Override
    public void save(String userId, PortalConsentDTO consentDTO) {
        PrivacyNoticesDTO privacyNotices = oneTrustRestService.getPrivacyNotices(tosId);

        if (!consentDTO.getVersionId().equals(privacyNotices.getVersion().getId())) {
            throw new VersionNotMatchedException("[CONSENTS] Received version id does not match the active one");
        } else {
            PortalConsent consent = new PortalConsent(
                    userId,
                    consentDTO.getVersionId()
            );

            log.info("[CONSENTS] Saving accepted privacy notice");
            portalConsentRepository.save(consent);
        }
    }
}
