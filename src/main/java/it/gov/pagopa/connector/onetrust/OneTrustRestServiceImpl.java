package it.gov.pagopa.connector.onetrust;

import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class OneTrustRestServiceImpl implements OneTrustRestService {

    @Autowired
    OneTrustRestClient oneTrustClient;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss");
        String dateFormatted = date.format(formatter);

        log.info("[CONSENTS] Calling OneTrust to get privacy notices with id {}", id);
        return oneTrustClient.getPrivacyNotices(id, dateFormatted);
    }
}
