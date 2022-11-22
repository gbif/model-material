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
import org.gbif.material.model.Organism;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "organism_agent_role")
public class OrganismAgentRole {
  @EmbeddedId private OrganismAgentRolePK id;

  @MapsId("organismId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "organism_id", nullable = false)
  private Organism organism;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "organism_agent_role")
  private String organismAgentRole;

  @Column(name = "organism_agent_role_began")
  private String organismAgentRoleBegan;

  @Column(name = "organism_agent_role_ended")
  private String organismAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class OrganismAgentRolePK implements Serializable {
    private static final long serialVersionUID = 6305692607910383145L;

    @Column(name = "organism_id", nullable = false)
    private String organismId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "organism_agent_role_order", nullable = false)
    private Short organismAgentRoleOrder;
  }
}
