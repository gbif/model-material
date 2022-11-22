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
import org.gbif.material.model.MaterialGroup;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "material_group_agent_role")
public class MaterialGroupAgentRole {
  @EmbeddedId private MaterialGroupAgentRolePK id;

  @MapsId("materialGroupId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_group_id", nullable = false)
  private MaterialGroup materialGroup;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "material_group_agent_role")
  private String materialGroupAgentRole;

  @Column(name = "material_group_agent_role_began")
  private String materialGroupAgentRoleBegan;

  @Column(name = "material_group_agent_role_ended")
  private String materialGroupAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class MaterialGroupAgentRolePK implements Serializable {
    private static final long serialVersionUID = -8850128116511005921L;

    @Column(name = "material_group_id", nullable = false)
    private String materialGroupId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "material_group_agent_role_order", nullable = false)
    private Short materialGroupAgentRoleOrder;
  }
}
