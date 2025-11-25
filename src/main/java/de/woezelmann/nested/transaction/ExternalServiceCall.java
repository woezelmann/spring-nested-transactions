package de.woezelmann.nested.transaction;

import jakarta.persistence.*;

@Entity(name = "externalservicecalls")
@Table(name = "externalservicecalls")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
		@NamedQuery(name = "ExternalServiceCalls.findByCallId",
				query = "SELECT e FROM externalservicecalls e WHERE e.callId = :callId")
})
public class ExternalServiceCall {

		@Id
		private String callId;

		@Basic
		@Column(nullable = false)
		private String externalCallId;

		public ExternalServiceCall() {
		}

		public ExternalServiceCall(String callId, String externalCallId) {
				this.callId = callId;
				this.externalCallId = externalCallId;
		}

		public void setExternalCallId(String externalCallId) {
				this.externalCallId = externalCallId;
		}
}
