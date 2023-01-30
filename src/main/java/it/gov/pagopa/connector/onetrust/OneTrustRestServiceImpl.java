package it.gov.pagopa.connector.onetrust;

import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class OneTrustRestServiceImpl implements OneTrustRestService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final OneTrustRestClient oneTrustClient;

    public OneTrustRestServiceImpl(OneTrustRestClient oneTrustClient) {
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

        log.info("[CONSENTS] Calling OneTrust to get privacy notices with id {}", id);
        return oneTrustClient.getPrivacyNotices(id, dateFormatted);
    }
}
