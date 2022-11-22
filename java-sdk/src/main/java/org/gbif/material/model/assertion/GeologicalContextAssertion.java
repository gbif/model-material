package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.GeologicalContext;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "geological_context_assertion")
public class GeologicalContextAssertion {
  @Id
  @Column(name = "geological_context_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "geological_context_id", nullable = false)
  private GeologicalContext geologicalContext;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "geological_context_parent_assertion_id")
  private GeologicalContextAssertion geologicalContextParentAssertion;

  @Column(name = "geological_context_assertion_type", nullable = false)
  private String geologicalContextAssertionType;

  @Column(name = "geological_context_assertion_made_date")
  private String geologicalContextAssertionMadeDate;

  @Column(name = "geological_context_assertion_effective_date")
  private String geologicalContextAssertionEffectiveDate;

  @Column(name = "geological_context_assertion_value")
  private String geologicalContextAssertionValue;

  @Column(name = "geological_context_assertion_value_numeric")
  private BigDecimal geologicalContextAssertionValueNumeric;

  @Column(name = "geological_context_assertion_unit")
  private String geologicalContextAssertionUnit;

  @Column(name = "geological_context_assertion_by_agent_name")
  private String geologicalContextAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "geological_context_assertion_by_agent_id")
  private Agent geologicalContextAssertionByAgent;

  @Column(name = "geological_context_assertion_protocol")
  private String geologicalContextAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "geological_context_assertion_protocol_id")
  private Protocol geologicalContextAssertionProtocol1;

  @Column(name = "geological_context_assertion_remarks")
  private String geologicalContextAssertionRemarks;
}
