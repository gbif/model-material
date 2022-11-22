package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.GeneticSequence;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "genetic_sequence_assertion")
public class GeneticSequenceAssertion {
  @Id
  @Column(name = "genetic_sequence_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_id", nullable = false)
  private GeneticSequence geneticSequence;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_parent_assertion_id")
  private GeneticSequenceAssertion geneticSequenceParentAssertion;

  @Column(name = "genetic_sequence_assertion_type", nullable = false)
  private String geneticSequenceAssertionType;

  @Column(name = "genetic_sequence_assertion_made_date")
  private String geneticSequenceAssertionMadeDate;

  @Column(name = "genetic_sequence_assertion_effective_date")
  private String geneticSequenceAssertionEffectiveDate;

  @Column(name = "genetic_sequence_assertion_value")
  private String geneticSequenceAssertionValue;

  @Column(name = "genetic_sequence_assertion_value_numeric")
  private BigDecimal geneticSequenceAssertionValueNumeric;

  @Column(name = "genetic_sequence_assertion_unit")
  private String geneticSequenceAssertionUnit;

  @Column(name = "genetic_sequence_assertion_by_agent_name")
  private String geneticSequenceAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_assertion_by_agent_id")
  private Agent geneticSequenceAssertionByAgent;

  @Column(name = "genetic_sequence_assertion_protocol")
  private String geneticSequenceAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_assertion_protocol_id")
  private Protocol geneticSequenceAssertionProtocol1;

  @Column(name = "genetic_sequence_assertion_remarks")
  private String geneticSequenceAssertionRemarks;
}
