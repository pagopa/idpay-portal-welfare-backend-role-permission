package it.gov.pagopa.service;

import it.gov.pagopa.common.web.exception.ClientException;
import it.gov.pagopa.role.permission.exception.VersionNotMatchedException;
import it.gov.pagopa.role.permission.onetrust.OneTrustRestService;
import it.gov.pagopa.role.permission.dto.PortalConsentDTO;
import it.gov.pagopa.role.permission.dto.mapper.PrivacyNotices2PortalConsentDTOMapper;
import it.gov.pagopa.role.permission.dto.onetrust.PrivacyNoticesDTO;
import it.gov.pagopa.role.permission.dto.onetrust.PrivacyNoticesVersion;
import it.gov.pagopa.role.permission.model.PortalConsent;
import it.gov.pagopa.role.permission.repository.PortalConsentRepository;
import it.gov.pagopa.role.permission.service.PortalConsentService;
import it.gov.pagopa.role.permission.service.PortalConsentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PortalConsentServiceImplTest {
    public static final LocalDateTime NOW = LocalDateTime.now();
    private static final String USER_ID = "USER_ID";
    private static final String TOS_ID = "TOS_ID";
    private static final String VERSION_ID = "VERSION_ID";

    private static final PortalConsentDTO EMPTY_CONSENT_DTO = new PortalConsentDTO();

    @Mock
    private PortalConsentRepository consentRepositoryMock;
    @Mock
    private OneTrustRestService oneTrustRestServiceMock;
    private final PrivacyNotices2PortalConsentDTOMapper consentMapper = new PrivacyNotices2PortalConsentDTOMapper();

    private PortalConsentService portalConsentService;

    @BeforeEach
    void init() {
        portalConsentService = new PortalConsentServiceImpl(consentRepositoryMock, oneTrustRestServiceMock, consentMapper, TOS_ID);
    }

    //region get
    @Test
    void testGetOK() {
        // Given
        PortalConsent portalConsent = PortalConsent.builder()
                .userId(USER_ID)
                .acceptanceDate(NOW)
                .versionId(VERSION_ID)
                .build();
        Mockito.when(consentRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(portalConsent));

        PrivacyNoticesVersion version = PrivacyNoticesVersion.builder()
                .id(VERSION_ID)
                .publishedDate(NOW.minusWeeks(1))
                .build();
        PrivacyNoticesDTO privacyNotices = PrivacyNoticesDTO.builder()
                .id(TOS_ID)
                .version(version)
                .build();
        Mockito.when(oneTrustRestServiceMock.getPrivacyNotices(TOS_ID)).thenReturn(privacyNotices);

        // When
        PortalConsentDTO result = portalConsentService.get(USER_ID);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(EMPTY_CONSENT_DTO, result);
    }

    @Test
    void testGetFirstAcceptance() {
        // Given
        Mockito.when(consentRepositoryMock.findById(USER_ID)).thenReturn(Optional.empty());

        PrivacyNoticesVersion version = PrivacyNoticesVersion.builder()
                .id(VERSION_ID)
                .publishedDate(NOW.minusWeeks(1))
                .build();
        PrivacyNoticesDTO privacyNotices = PrivacyNoticesDTO.builder()
                .id(TOS_ID)
                .version(version)
                .build();
        Mockito.when(oneTrustRestServiceMock.getPrivacyNotices(TOS_ID)).thenReturn(privacyNotices);

        // When
        PortalConsentDTO result = portalConsentService.get(USER_ID);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getFirstAcceptance());
        Assertions.assertEquals(VERSION_ID, result.getVersionId());
    }

    @Test
    void testGetNewVersion() {
        // Given
        PortalConsent portalConsent = PortalConsent.builder()
                .userId(USER_ID)
                .acceptanceDate(NOW.minusWeeks(1))
                .versionId(VERSION_ID)
                .build();
        Mockito.when(consentRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(portalConsent));

        PrivacyNoticesVersion version = PrivacyNoticesVersion.builder()
                .id(VERSION_ID.concat("_NEW"))
                .publishedDate(NOW.minusDays(1))
                .build();
        PrivacyNoticesDTO privacyNotices = PrivacyNoticesDTO.builder()
                .id(TOS_ID)
                .version(version)
                .build();
        Mockito.when(oneTrustRestServiceMock.getPrivacyNotices(TOS_ID)).thenReturn(privacyNotices);

        // When
        PortalConsentDTO result = portalConsentService.get(USER_ID);

        // Then
        PortalConsentDTO expected = new PortalConsentDTO("VERSION_ID_NEW", false);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected, result);
    }
    //endregion

    //region save
    @Test
    void testSave() {
        // Given
        PrivacyNoticesVersion version = PrivacyNoticesVersion.builder()
                .id(VERSION_ID)
                .publishedDate(NOW.minusWeeks(1))
                .build();
        PrivacyNoticesDTO privacyNotices = PrivacyNoticesDTO.builder()
                .id(TOS_ID)
                .version(version)
                .build();
        Mockito.when(oneTrustRestServiceMock.getPrivacyNotices(TOS_ID)).thenReturn(privacyNotices);

        Mockito.when(consentRepositoryMock.save(Mockito.any())).thenAnswer(i -> {
            PortalConsent argument = i.getArgument(0);
            argument.setAcceptanceDate(NOW);

            return argument;
        });

        PortalConsentDTO input = PortalConsentDTO.builder()
                .versionId(VERSION_ID)
                .build();

        // When
        portalConsentService.save(USER_ID, input);

        // Then
        PortalConsent expectedConsent = PortalConsent.builder()
                .userId(USER_ID)
                .versionId(VERSION_ID)
                .acceptanceDate(NOW)
                .build();

        Mockito.verify(consentRepositoryMock).save(expectedConsent);
    }

    @Test
    void testSaveKo() {
        // Given
        PrivacyNoticesVersion version = PrivacyNoticesVersion.builder()
                .id(VERSION_ID + "NEW")
                .publishedDate(NOW.minusDays(1))
                .build();
        PrivacyNoticesDTO privacyNotices = PrivacyNoticesDTO.builder()
                .id(TOS_ID)
                .version(version)
                .build();
        Mockito.when(oneTrustRestServiceMock.getPrivacyNotices(TOS_ID)).thenReturn(privacyNotices);


        PortalConsentDTO input = PortalConsentDTO.builder()
                .versionId(VERSION_ID)
                .build();

        // When
        Executable executable = () -> portalConsentService.save(USER_ID, input);

        // Then
        Assertions.assertThrows(VersionNotMatchedException.class, executable);
        Mockito.verify(consentRepositoryMock, Mockito.never()).save(Mockito.any());
    }
    //endregion

}