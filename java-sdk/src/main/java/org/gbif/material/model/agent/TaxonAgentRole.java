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
import org.gbif.material.model.identification.Taxon;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "taxon_agent_role")
public class TaxonAgentRole {
  @EmbeddedId private TaxonAgentRolePK id;

  @MapsId("taxonId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_id", nullable = false)
  private Taxon taxon;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "taxon_agent_role")
  private String taxonAgentRole;

  @Column(name = "taxon_agent_role_began")
  private String taxonAgentRoleBegan;

  @Column(name = "taxon_agent_role_ended")
  private String taxonAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class TaxonAgentRolePK implements Serializable {
    private static final long serialVersionUID = 5046054656495944232L;

    @Column(name = "taxon_id", nullable = false)
    private String taxonId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "taxon_agent_role_order", nullable = false)
    private Short taxonAgentRoleOrder;
  }
}
