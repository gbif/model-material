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
import org.gbif.material.model.Georeference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "georeference_agent_role")
public class GeoreferenceAgentRole {
  @EmbeddedId private GeoreferenceAgentRolePK id;

  @MapsId("georeferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "georeference_id", nullable = false)
  private Georeference georeference;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "georeference_agent_role")
  private String georeferenceAgentRole;

  @Column(name = "georefrence_agent_role_began")
  private String georefrenceAgentRoleBegan;

  @Column(name = "georeference_agent_role_ended")
  private String georeferenceAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class GeoreferenceAgentRolePK implements Serializable {
    private static final long serialVersionUID = -2634765228384945884L;

    @Column(name = "georeference_id", nullable = false)
    private String georeferenceId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "entity_agent_role_order", nullable = false)
    private Short entityAgentRoleOrder;
  }
}
