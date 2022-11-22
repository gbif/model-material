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
import org.gbif.material.model.GeologicalContext;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "geological_context_agent_role")
public class GeologicalContextAgentRole {
  @EmbeddedId private GeologicalContextAgentRolePK id;

  @MapsId("geologicalContextId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "geological_context_id", nullable = false)
  private GeologicalContext geologicalContext;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "geological_context_agent_role")
  private String geologicalContextAgentRole;

  @Column(name = "geological_context_agent_role_began")
  private String geologicalContextAgentRoleBegan;

  @Column(name = "geological_context_agent_role_ended")
  private String geologicalContextAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class GeologicalContextAgentRolePK implements Serializable {
    private static final long serialVersionUID = 5118324147953561425L;

    @Column(name = "geological_context_id", nullable = false)
    private String geologicalContextId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "entity_agent_role_order", nullable = false)
    private Short entityAgentRoleOrder;
  }
}
