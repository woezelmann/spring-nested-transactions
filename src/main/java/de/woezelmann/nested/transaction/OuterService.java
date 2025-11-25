package de.woezelmann.nested.transaction;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OuterService {

		@Autowired
		private InnerService innerService;

		@Transactional
		public void runWithTransaction() {
				String callId = UUID.randomUUID().toString();
				String externalCallId = UUID.randomUUID().toString();

				innerService.save(new ExternalServiceCall(callId, externalCallId));

				innerService.load(callId)
						.ifPresent(externalServiceCall -> {
								externalServiceCall.setExternalCallId(UUID.randomUUID().toString());
								innerService.update(externalServiceCall);
						});

				innerService.load(callId)
						.ifPresent(externalServiceCall -> {
								externalServiceCall.setExternalCallId(UUID.randomUUID().toString());
								innerService.update(externalServiceCall);
						});
		}
}
