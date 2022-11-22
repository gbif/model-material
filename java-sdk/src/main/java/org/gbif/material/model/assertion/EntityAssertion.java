package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "entity_assertion")
public class EntityAssertion {
  @Id
  @Column(name = "entity_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_id", nullable = false)
  private org.gbif.material.model.Entity entity;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entityparent_assertion_id")
  private EntityAssertion entityparentAssertion;

  @Column(name = "entity_assertion_type", nullable = false)
  private String entityAssertionType;

  @Column(name = "entity_assertion_made_date")
  private String entityAssertionMadeDate;

  @Column(name = "entity_assertion_effective_date")
  private String entityAssertionEffectiveDate;

  @Column(name = "entity_assertion_value")
  private String entityAssertionValue;

  @Column(name = "entity_assertion_value_numeric")
  private BigDecimal entityAssertionValueNumeric;

  @Column(name = "entity_assertion_unit")
  private String entityAssertionUnit;

  @Column(name = "entity_assertion_by_agent_name")
  private String entityAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_assertion_by_agent_id")
  private Agent entityAssertionByAgent;

  @Column(name = "entity_assertion_protocol")
  private String entityAssertionProtocol;

  @Column(name = "entity_assertion_protocol_id")
  private String entityAssertionProtocolId;

  @Column(name = "entity_assertion_remarks")
  private String entityAssertionRemarks;
}
