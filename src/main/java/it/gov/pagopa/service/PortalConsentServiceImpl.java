package it.gov.pagopa.service;

import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.dto.mapper.PortalConsentDTO2ModelMapper;
import it.gov.pagopa.model.PortalConsent;
import it.gov.pagopa.repository.PortalConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortalConsentServiceImpl implements PortalConsentService{

    @Autowired
    PortalConsentRepository portalConsentRepository;

    @Autowired
    PortalConsentDTO2ModelMapper dtoMapper;

    @Override
    public void save(PortalConsentDTO consentDto) {
        PortalConsent consent = dtoMapper.apply(consentDto);
        portalConsentRepository.save(consent);
    }
}
