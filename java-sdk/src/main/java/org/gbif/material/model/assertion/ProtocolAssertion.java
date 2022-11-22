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
@Table(name = "protocol_assertion")
public class ProtocolAssertion {
  @Id
  @Column(name = "protocol_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "protocol_id", nullable = false)
  private Protocol protocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "protocol_parent_assertion_id")
  private ProtocolAssertion protocolParentAssertion;

  @Column(name = "protocol_assertion_type", nullable = false)
  private String protocolAssertionType;

  @Column(name = "protocol_assertion_made_date")
  private String protocolAssertionMadeDate;

  @Column(name = "protocol_assertion_effective_date")
  private String protocolAssertionEffectiveDate;

  @Column(name = "protocol_assertion_value")
  private String protocolAssertionValue;

  @Column(name = "protocol_assertion_value_numeric")
  private BigDecimal protocolAssertionValueNumeric;

  @Column(name = "protocol_assertion_unit")
  private String protocolAssertionUnit;

  @Column(name = "protocol_assertion_by_agent_name")
  private String protocolAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "protocol_assertion_by_agent_id")
  private Agent protocolAssertionByAgent;

  @Column(name = "protocol_assertion_protocol")
  private String protocolAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "protocol_assertion_protocol_id")
  private Protocol protocolAssertionProtocol1;

  @Column(name = "protocol_assertion_remarks")
  private String protocolAssertionRemarks;
}
