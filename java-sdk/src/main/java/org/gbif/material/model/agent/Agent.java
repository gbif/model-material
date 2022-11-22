package org.gbif.material.model.agent;

import javax.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "agent")
public class Agent {
  @Id
  @Column(name = "agent_id", nullable = false)
  private String id;

  @Column(name = "agent_type", nullable = false)
  private String agentType;

  @Column(name = "preferred_agent_name")
  private String preferredAgentName;
}
