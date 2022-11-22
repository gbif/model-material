package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.Reference;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "reference_assertion")
public class ReferenceAssertion {
  @Id
  @Column(name = "reference_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "reference_id", nullable = false)
  private Reference reference;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "reference_parent_assertion_id")
  private ReferenceAssertion referenceParentAssertion;

  @Column(name = "reference_assertion_type", nullable = false)
  private String referenceAssertionType;

  @Column(name = "reference_assertion_made_date")
  private String referenceAssertionMadeDate;

  @Column(name = "reference_assertion_effective_date")
  private String referenceAssertionEffectiveDate;

  @Column(name = "reference_assertion_value")
  private String referenceAssertionValue;

  @Column(name = "reference_assertion_value_numeric")
  private BigDecimal referenceAssertionValueNumeric;

  @Column(name = "reference_assertion_unit")
  private String referenceAssertionUnit;

  @Column(name = "reference_assertion_by_agent_name")
  private String referenceAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "reference_assertion_by_agent_id")
  private Agent referenceAssertionByAgent;

  @Column(name = "reference_assertion_protocol")
  private String referenceAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "reference_assertion_protocol_id")
  private Protocol referenceAssertionProtocol1;

  @Column(name = "reference_assertion_remarks")
  private String referenceAssertionRemarks;
}
