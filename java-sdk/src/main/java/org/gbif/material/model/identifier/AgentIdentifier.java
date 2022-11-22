package org.gbif.material.model.identifier;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "agent_identifier")
public class AgentIdentifier {
  @EmbeddedId private AgentIdentifierPK id;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class AgentIdentifierPK implements Serializable {
    private static final long serialVersionUID = -4073554803016683730L;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "agent_identifier", nullable = false)
    private String agentIdentifier;

    @Column(name = "agent_identifier_type", nullable = false)
    private String agentIdentifierType;
  }
}
