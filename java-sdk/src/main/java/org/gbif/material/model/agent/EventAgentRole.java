package org.gbif.material.model.agent;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.Event;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "event_agent_role")
public class EventAgentRole {
  @EmbeddedId private EventAgentRolePK id;

  @MapsId("eventId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "event_agent_role")
  private String eventAgentRole;

  @Column(name = "event_agent_role_began")
  private String eventAgentRoleBegan;

  @Column(name = "event_agent_role_ended")
  private String eventAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class EventAgentRolePK implements Serializable {
    private static final long serialVersionUID = -2821160889861200952L;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "event_agent_role_order", nullable = false)
    private Short eventAgentRoleOrder;
  }
}
