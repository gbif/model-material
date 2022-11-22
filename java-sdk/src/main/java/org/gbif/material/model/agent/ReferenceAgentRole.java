package org.gbif.material.model.agent;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.Reference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "reference_agent_role")
public class ReferenceAgentRole {
  @EmbeddedId private ReferenceAgentRolePK id;

  @MapsId("referenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "reference_id", nullable = false)
  private Reference reference;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "reference_agent_role")
  private String referenceAgentRole;

  @Column(name = "reference_agent_role_began")
  private String referenceAgentRoleBegan;

  @Column(name = "reference_agent_role_ended")
  private String referenceAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class ReferenceAgentRolePK implements Serializable {

    private static final long serialVersionUID = -5354035696061273641L;

    @Column(name = "reference_id", nullable = false)
    private String referenceId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "reference_agent_role_order", nullable = false)
    private Short referenceAgentRoleOrder;
  }
}
