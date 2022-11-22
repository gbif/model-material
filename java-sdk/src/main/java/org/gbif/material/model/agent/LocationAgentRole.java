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
import org.gbif.material.model.Location;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "location_agent_role")
public class LocationAgentRole {
  @EmbeddedId private LocationAgentRolePK id;

  @MapsId("locationId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_id", nullable = false)
  private Location location;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "location_agent_role")
  private String locationAgentRole;

  @Column(name = "location_agent_role_began")
  private String locationAgentRoleBegan;

  @Column(name = "location_agent_role_ended")
  private String locationAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class LocationAgentRolePK implements Serializable {
    private static final long serialVersionUID = -397597477936103534L;

    @Column(name = "location_id", nullable = false)
    private String locationId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "entity_agent_role_order", nullable = false)
    private Short entityAgentRoleOrder;
  }
}
