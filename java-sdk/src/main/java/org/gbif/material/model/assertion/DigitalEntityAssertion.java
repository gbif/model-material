package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.DigitalEntity;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "digital_entity_assertion")
public class DigitalEntityAssertion {
  @Id
  @Column(name = "digital_entity_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "digital_entity_id", nullable = false)
  private DigitalEntity digitalEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "digital_entity_parent_assertion_id")
  private DigitalEntityAssertion digitalEntityParentAssertion;

  @Column(name = "digital_entity_assertion_type", nullable = false)
  private String digitalEntityAssertionType;

  @Column(name = "digital_entity_assertion_made_date")
  private String digitalEntityAssertionMadeDate;

  @Column(name = "digital_entity_assertion_effective_date")
  private String digitalEntityAssertionEffectiveDate;

  @Column(name = "digital_entity_assertion_value")
  private String digitalEntityAssertionValue;

  @Column(name = "digital_entity_assertion_value_numeric")
  private BigDecimal digitalEntityAssertionValueNumeric;

  @Column(name = "digital_entity_assertion_unit")
  private String digitalEntityAssertionUnit;

  @Column(name = "digital_entity_assertion_by_agent_name")
  private String digitalEntityAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "digital_entity_assertion_by_agent_id")
  private Agent digitalEntityAssertionByAgent;

  @Column(name = "digital_entity_assertion_protocol")
  private String digitalEntityAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "digital_entity_assertion_protocol_id")
  private Protocol digitalEntityAssertionProtocol1;

  @Column(name = "digital_entity_assertion_remarks")
  private String digitalEntityAssertionRemarks;
}
