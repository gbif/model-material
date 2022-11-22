package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.Event;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "event_assertion")
public class EventAssertion {
  @Id
  @Column(name = "event_assertion_id", nullable = false)
  private String eventAssertionId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "event_parent_assertion_id")
  private EventAssertion eventParentAssertion;

  @Column(name = "event_assertion_type", nullable = false)
  private String eventAssertionType;

  @Column(name = "event_assertion_made_date")
  private String eventAssertionMadeDate;

  @Column(name = "event_assertion_effective_date")
  private String eventAssertionEffectiveDate;

  @Column(name = "event_assertion_value")
  private String eventAssertionValue;

  @Column(name = "event_assertion_value_numeric")
  private BigDecimal eventAssertionValueNumeric;

  @Column(name = "event_assertion_unit")
  private String eventAssertionUnit;

  @Column(name = "event_assertion_by_agent_name")
  private String eventAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "event_assertion_by_agent_id")
  private Agent eventAssertionByAgent;

  @Column(name = "event_assertion_protocol")
  private String eventAssertionProtocol;

  @Column(name = "event_assertion_protocol_id")
  private String eventAssertionProtocolId;

  @Column(name = "event_assertion_remarks")
  private String eventAssertionRemarks;
}
