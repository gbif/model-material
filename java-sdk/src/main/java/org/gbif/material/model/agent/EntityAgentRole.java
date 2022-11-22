package org.gbif.material.model.agent;

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
@Table(name = "entity_agent_role")
public class EntityAgentRole {
  @EmbeddedId private EntityAgentRolePK id;

  @MapsId("entityId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_id", nullable = false)
  private org.gbif.material.model.Entity entity;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "entity_agent_role")
  private String entityAgentRole;

  @Column(name = "entity_agent_role_began")
  private String entityAgentRoleBegan;

  @Column(name = "entity_agent_role_ended")
  private String entityAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class EntityAgentRolePK implements Serializable {

    private static final long serialVersionUID = -4661148407609703011L;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "entity_agent_role_order", nullable = false)
    private Short entityAgentRoleOrder;
  }
}
