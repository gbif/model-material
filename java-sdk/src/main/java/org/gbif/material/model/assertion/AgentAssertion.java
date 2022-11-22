package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "agent_assertion")
public class AgentAssertion {
  @Id
  @Column(name = "agent_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_parent_assertion_id")
  private AgentAssertion agentParentAssertion;

  @Column(name = "agent_assertion_type", nullable = false)
  private String agentAssertionType;

  @Column(name = "agent_assertion_made_date")
  private String agentAssertionMadeDate;

  @Column(name = "agent_assertion_effective_date")
  private String agentAssertionEffectiveDate;

  @Column(name = "agent_assertion_value")
  private String agentAssertionValue;

  @Column(name = "agent_assertion_value_numeric")
  private BigDecimal agentAssertionValueNumeric;

  @Column(name = "agent_assertion_unit")
  private String agentAssertionUnit;

  @Column(name = "agent_assertion_by_agent_name")
  private String agentAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_assertion_by_agent_id")
  private Agent agentAssertionByAgent;

  @Column(name = "agent_assertion_protocol")
  private String agentAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_assertion_protocol_id")
  private Protocol agentAssertionProtocol1;

  @Column(name = "agent_assertion_remarks")
  private String agentAssertionRemarks;
}
