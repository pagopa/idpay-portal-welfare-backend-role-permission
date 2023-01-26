package it.gov.pagopa.service;

import it.gov.pagopa.connector.onetrust.OneTrustRestService;
import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.dto.mapper.PrivacyNotices2PortalConsentDTOMapper;
import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import it.gov.pagopa.model.PortalConsent;
import it.gov.pagopa.repository.PortalConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PortalConsentServiceImpl implements PortalConsentService{

    @Autowired
    PortalConsentRepository portalConsentRepository;

    @Autowired
    OneTrustRestService oneTrustRestService;

    @Autowired
    PrivacyNotices2PortalConsentDTOMapper privacyNotices2PortalConsentDTOMapper;

    @Value("${onetrust.privacy-notices.tos.id}")
    private String tosId;

    @Override
    public PortalConsentDTO get(String userId) {

        Optional<PortalConsent> optional = portalConsentRepository.findById(userId);
        if (optional.isPresent()) {
            PortalConsent consentEntity = optional.get();
            PrivacyNoticesDTO privacyNotices = oneTrustRestService.getPrivacyNotices(tosId);

            return consentEntity.getVersionId().equals(privacyNotices.getVersion().getId())
                    ? new PortalConsentDTO()
                    : privacyNotices2PortalConsentDTOMapper.apply(privacyNotices);
        } else {
            return null;
        }
    }

    @Override
    public void save(String userId, PortalConsentDTO consentDTO) {
        LocalDateTime now = LocalDateTime.now();
        PrivacyNoticesDTO privacyNotices = oneTrustRestService.getPrivacyNotices(tosId, now);

        if (!consentDTO.getVersionId().equals(privacyNotices.getVersion().getId())) {
            // TODO error
        } else {
            PortalConsent consent = new PortalConsent(
                    userId,
                    now,
                    consentDTO.getVersionId()
            );
            portalConsentRepository.save(consent);
        }
    }
}
