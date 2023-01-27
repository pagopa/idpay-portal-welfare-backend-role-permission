package it.gov.pagopa.service;

import it.gov.pagopa.connector.onetrust.OneTrustRestService;
import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.dto.mapper.PrivacyNotices2PortalConsentDTOMapper;
import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import it.gov.pagopa.exception.ClientException;
import it.gov.pagopa.model.PortalConsent;
import it.gov.pagopa.repository.PortalConsentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PortalConsentServiceImpl implements PortalConsentService {

    private final PortalConsentRepository portalConsentRepository;

    private final OneTrustRestService oneTrustRestService;

    private final PrivacyNotices2PortalConsentDTOMapper privacyNotices2PortalConsentDTOMapper;

    private final String tosId;

    public PortalConsentServiceImpl(
            PortalConsentRepository portalConsentRepository,
            OneTrustRestService oneTrustRestService,
            PrivacyNotices2PortalConsentDTOMapper privacyNotices2PortalConsentDTOMapper,
            @Value("${onetrust.privacy-notices.tos.id}") String tosId) {
        this.portalConsentRepository = portalConsentRepository;
        this.oneTrustRestService = oneTrustRestService;
        this.privacyNotices2PortalConsentDTOMapper = privacyNotices2PortalConsentDTOMapper;
        this.tosId = tosId;
    }

    @Override
    public Optional<PortalConsentDTO> get(String userId) {

        log.info("[CONSENTS] Fetching possible previous consent by user {}", userId);
        PortalConsent consent = portalConsentRepository.findById(userId)
                .orElseThrow(() -> new ClientException(
                        HttpStatus.NOT_FOUND.value(),
                        "Previously accepted consent by user %s does not exist".formatted(userId),
                        HttpStatus.NOT_FOUND)
                );

        PrivacyNoticesDTO privacyNotices = oneTrustRestService.getPrivacyNotices(tosId);

        return consent.getVersionId().equals(privacyNotices.getVersion().getId())
                    ? Optional.empty()
                    : Optional.of(privacyNotices2PortalConsentDTOMapper.apply(privacyNotices));
    }

    @Override
    public void save(String userId, PortalConsentDTO consentDTO) {
        PrivacyNoticesDTO privacyNotices = oneTrustRestService.getPrivacyNotices(tosId);

        if (!consentDTO.getVersionId().equals(privacyNotices.getVersion().getId())) {
            // TODO error
        } else {
            PortalConsent consent = new PortalConsent(
                    userId,
                    consentDTO.getVersionId()
            );

            log.info("[CONSENTS] Saving privacy notice with version {} accepted by user {}",
                    consent.getVersionId(),
                    consent.getUserId());
            portalConsentRepository.save(consent);
        }
    }
}
