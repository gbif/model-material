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
@Table(name = "genetic_sequence_agent_role")
public class GeneticSequenceAgentRole {
  @EmbeddedId private GeneticSequenceAgentRolePK id;

  @MapsId("geneticSequenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_id", nullable = false)
  private DigitalEntity geneticSequence;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "genetic_sequence_agent_role")
  private String geneticSequenceAgentRole;

  @Column(name = "genetic_sequence_agent_role_began")
  private String geneticSequenceAgentRoleBegan;

  @Column(name = "genetic_sequence_agent_role_ended")
  private String geneticSequenceAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class GeneticSequenceAgentRolePK implements Serializable {
    private static final long serialVersionUID = 5496514502910581598L;

    @Column(name = "genetic_sequence_id", nullable = false)
    private String geneticSequenceId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "entity_agent_role_order", nullable = false)
    private Short entityAgentRoleOrder;
  }
}
