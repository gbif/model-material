package org.gbif.material.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@javax.persistence.Entity
@Table(name = "agent_role")
public class AgentRole {
  @EmbeddedId private AgentRolePK id;

  @Column(name = "agent_role_agent_name")
  private String agentRoleAgentName;

  @Column(name = "agent_role_role")
  private String agentRoleRole;

  @Column(name = "agent_role_began")
  private String agentRoleBegan;

  @Column(name = "agent_role_ended")
  private String agentRoleEnded;

  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Embeddable
  public static class AgentRolePK implements Serializable {
    private static final long serialVersionUID = -4084292552083673995L;

    @NotNull
    @Column(name = "agent_role_target_id", nullable = false)
    private String agentRoleTargetId;

    @Column(name = "agent_role_target_type", columnDefinition = "common_targets not null")
    @Enumerated(EnumType.STRING)
    private Common.CommonTargetType agentRoleTargetType;

    @NotNull
    @Column(name = "agent_role_agent_id", nullable = false)
    private String agentRoleAgentId;

    @NotNull
    @Column(name = "agent_role_order", nullable = false)
    private Short agentRoleOrder;
  }
}
