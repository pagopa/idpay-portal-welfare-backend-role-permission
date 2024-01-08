package it.gov.pagopa.role.permission.onetrust;

import it.gov.pagopa.role.permission.dto.onetrust.PrivacyNoticesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class OneTrustRestServiceImpl implements OneTrustRestService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final String BEARER = "Bearer ";

    private final String bearerToken;
    private final OneTrustRestClient oneTrustClient;

    public OneTrustRestServiceImpl(
            @Value("${app.rest-client.one-trust.service.privacy-notices.apiKey}") String bearerToken,
            OneTrustRestClient oneTrustClient) {
        this.bearerToken = BEARER + bearerToken;
        this.oneTrustClient = oneTrustClient;
    }

    /**
     * Calls OneTrust privacy notices' service passing {@link LocalDateTime#now()} as parameter
     */
    @Override
    public PrivacyNoticesDTO getPrivacyNotices(String id) {
        return this.getPrivacyNotices(id, LocalDateTime.now());
    }

    /**
     * Calls OneTrust privacy notices' service to retrieve the versions less than the given date-time and the highest version
     */
    @Override
    public PrivacyNoticesDTO getPrivacyNotices(String id, LocalDateTime date) {
        String dateFormatted = date.format(DATE_FORMATTER);

        log.info("[CONSENTS] Calling OneTrust to get current active version of privacy notices");
        return oneTrustClient.getPrivacyNotices(id, dateFormatted, bearerToken);
    }
}
