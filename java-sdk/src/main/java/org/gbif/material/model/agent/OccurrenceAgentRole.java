package org.gbif.material.model.agent;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gbif.material.model.Occurrence;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "occurrence_agent_role")
public class OccurrenceAgentRole {
  @EmbeddedId private OccurrenceAgentRolePK id;

  @MapsId("occurrenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_id", nullable = false)
  private Occurrence occurrence;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "occurrence_agent_role")
  private String occurrenceAgentRole;

  @Column(name = "occurrence_agent_role_began")
  private String occurrenceAgentRoleBegan;

  @Column(name = "ccurrence_agent_role_ended")
  private String ccurrenceAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class OccurrenceAgentRolePK implements Serializable {
    private static final long serialVersionUID = 4226454021762068703L;

    @Column(name = "occurrence_id", nullable = false)
    private String occurrenceId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "occurrence_agent_role_order", nullable = false)
    private Short occurrenceAgentRoleOrder;
  }
}
