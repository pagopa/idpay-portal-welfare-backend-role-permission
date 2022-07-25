package it.gov.pagopa.service;

import it.gov.pagopa.repository.PortalConsentRepository;
import it.gov.pagopa.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortalConsentServiceImpl implements PortalConsentService{
    @Autowired
    PortalConsentRepository portalConsentRepository;


}
