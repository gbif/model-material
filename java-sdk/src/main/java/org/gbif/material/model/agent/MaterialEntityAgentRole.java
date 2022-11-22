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
import org.gbif.material.model.MaterialEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "material_entity_agent_role")
public class MaterialEntityAgentRole {
  @EmbeddedId private MaterialEntityAgentRolePK id;

  @MapsId("materialEntityId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_id", nullable = false)
  private MaterialEntity materialEntity;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "material_entity_agent_role")
  private String materialEntityAgentRole;

  @Column(name = "material_entity_agent_role_began")
  private String materialEntityAgentRoleBegan;

  @Column(name = "material_entity_agent_role_ended")
  private String materialEntityAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class MaterialEntityAgentRolePK implements Serializable {
    private static final long serialVersionUID = 4157312087799481057L;

    @Column(name = "material_entity_id", nullable = false)
    private String materialEntityId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "material_entity_agent_role_order", nullable = false)
    private Short materialEntityAgentRoleOrder;
  }
}
