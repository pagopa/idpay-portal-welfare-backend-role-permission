package it.gov.pagopa.service;

import it.gov.pagopa.connector.onetrust.OneTrustRestService;
import it.gov.pagopa.dto.PortalConsentDTO;
import it.gov.pagopa.dto.mapper.PrivacyNotices2PortalConsentDTOMapper;
import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import it.gov.pagopa.dto.onetrust.PrivacyNoticesVersion;
import it.gov.pagopa.exception.ClientException;
import it.gov.pagopa.model.PortalConsent;
import it.gov.pagopa.repository.PortalConsentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PortalConsentServiceImplTest {
    public static final LocalDateTime NOW = LocalDateTime.now();
    private static final String USER_ID = "USER_ID";
    private static final String TOS_ID = "TOS_ID";
    private static final String VERSION_ID = "VERSION_ID";

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
        Optional<PortalConsentDTO> result = portalConsentService.get(USER_ID);

        // Then
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetKo() {
        // Given
        Mockito.when(consentRepositoryMock.findById(USER_ID)).thenThrow(new ClientException(404, "TEST", HttpStatus.NOT_FOUND));

        // When
        Executable executable = () -> portalConsentService.get(USER_ID);

        // Then
        Assertions.assertThrows(ClientException.class, executable);
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
        Optional<PortalConsentDTO> result = portalConsentService.get(USER_ID);

        // Then
        PortalConsentDTO expected = new PortalConsentDTO("VERSION_ID_NEW");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expected, result.get());
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

        PortalConsentDTO input = new PortalConsentDTO(VERSION_ID);

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
    //endregion

}