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
import org.gbif.material.model.Protocol;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "protocol_agent_role")
public class ProtocolAgentRole {
  @EmbeddedId private ProtocolAgentRolePK id;

  @MapsId("protocolId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "protocol_id", nullable = false)
  private Protocol protocol;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "protocol_agent_role")
  private String protocolAgentRole;

  @Column(name = "protocol_agent_role_began")
  private String protocolAgentRoleBegan;

  @Column(name = "protocol_agent_role_ended")
  private String protocolAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class ProtocolAgentRolePK implements Serializable {
    private static final long serialVersionUID = 2433822131217069191L;

    @Column(name = "protocol_id", nullable = false)
    private String protocolId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "protocol_agent_role_order", nullable = false)
    private Short protocolAgentRoleOrder;
  }
}
