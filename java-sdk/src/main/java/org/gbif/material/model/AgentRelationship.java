package org.gbif.material.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "agent_relationship")
public class AgentRelationship {
  @EmbeddedId private AgentRelationshipPK id;

  @MapsId("subjectAgentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "subject_agent_id", nullable = false)
  private Agent subjectAgent;

  @MapsId("objectAgentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "object_agent_id", nullable = false)
  private Agent objectAgent;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class AgentRelationshipPK implements Serializable {
    private static final long serialVersionUID = -5835527215188130129L;

    @Column(name = "subject_agent_id", nullable = false)
    private String subjectAgentId;

    @Column(name = "relationship_to", nullable = false)
    private String relationshipTo;

    @Column(name = "object_agent_id", nullable = false)
    private String objectAgentId;
  }
}
