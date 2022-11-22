package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.*;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "occurrence_assertion")
public class OccurrenceAssertion {
  @Id
  @Column(name = "occurrence_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_id", nullable = false)
  private Occurrence occurrence;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_parent_assertion_id")
  private OccurrenceAssertion occurrenceParentAssertion;

  @Column(name = "occurrence_assertion_type", nullable = false)
  private String occurrenceAssertionType;

  @Column(name = "occurrence_assertion_made_date")
  private String occurrenceAssertionMadeDate;

  @Column(name = "occurrence_assertion_effective_date")
  private String occurrenceAssertionEffectiveDate;

  @Column(name = "occurrence_assertion_value")
  private String occurrenceAssertionValue;

  @Column(name = "occurrence_assertion_value_numeric")
  private BigDecimal occurrenceAssertionValueNumeric;

  @Column(name = "occurrence_assertion_unit")
  private String occurrenceAssertionUnit;

  @Column(name = "occurrence_assertion_by_agent_name")
  private String occurrenceAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_assertion_by_agent_id")
  private Agent occurrenceAssertionByAgent;

  @Column(name = "occurrence_assertion_protocol")
  private String occurrenceAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_assertion_protocol_id")
  private Protocol occurrenceAssertionProtocol1;

  @Column(name = "occurrence_assertion_remarks")
  private String occurrenceAssertionRemarks;
}
