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
import org.gbif.material.model.DigitalEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "digital_entity_agent_role")
public class DigitalEntityAgentRole {
  @EmbeddedId private DigitalEntityAgentRolePK id;

  @MapsId("digitalEntityId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "digital_entity_id", nullable = false)
  private DigitalEntity digitalEntity;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "digital_entity_agent_role")
  private String digitalEntityAgentRole;

  @Column(name = "digital_entity_agent_role_began")
  private String digitalEntityAgentRoleBegan;

  @Column(name = "digital_entity_agent_role_ended")
  private String digitalEntityAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class DigitalEntityAgentRolePK implements Serializable {

    private static final long serialVersionUID = 902552978902496046L;

    @Column(name = "digital_entity_id", nullable = false)
    private String digitalEntityId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "entity_agent_role_order", nullable = false)
    private Short entityAgentRoleOrder;
  }
}
