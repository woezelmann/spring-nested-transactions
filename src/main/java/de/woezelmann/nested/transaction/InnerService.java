package de.woezelmann.nested.transaction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@Repository
public class InnerService {

		private static final String QUERY_WITH_CALLID = "ExternalServiceCalls.findByCallId";

		@PersistenceContext
		private EntityManager em;

		@Transactional(REQUIRES_NEW)
		public ExternalServiceCall save(ExternalServiceCall externalServiceCall) {
				em.persist(externalServiceCall);
				return externalServiceCall;
		}

		@Transactional(REQUIRES_NEW)
		public ExternalServiceCall update(ExternalServiceCall externalServiceCall) {
				em.merge(externalServiceCall);
				return externalServiceCall;
		}

		public Optional<ExternalServiceCall> load(String callId) {
				List<ExternalServiceCall> externalServiceCalls = em.createNamedQuery(QUERY_WITH_CALLID, ExternalServiceCall.class)
						.setParameter("callId", callId)
						.getResultList();
				if (externalServiceCalls.isEmpty()) {
						return Optional.empty();
				}
				return Optional.of(externalServiceCalls.get(0));
		}
}
