package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.agent.Agent;
import org.gbif.material.model.identification.Identification;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "identification_assertion")
public class IdentificationAssertion {
  @Id
  @Column(name = "identification_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "identification_id", nullable = false)
  private Identification identification;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "identification_parent_assertion_id")
  private IdentificationAssertion identificationParentAssertion;

  @Column(name = "identification_assertion_type", nullable = false)
  private String identificationAssertionType;

  @Column(name = "identification_assertion_made_date")
  private String identificationAssertionMadeDate;

  @Column(name = "identification_assertion_effective_date")
  private String identificationAssertionEffectiveDate;

  @Column(name = "identification_assertion_value")
  private String identificationAssertionValue;

  @Column(name = "identification_assertion_value_numeric")
  private BigDecimal identificationAssertionValueNumeric;

  @Column(name = "identification_assertion_unit")
  private String identificationAssertionUnit;

  @Column(name = "identification_assertion_by_agent_name")
  private String identificationAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "identification_assertion_by_agent_id")
  private Agent identificationAssertionByAgent;

  @Column(name = "identification_assertion_protocol")
  private String identificationAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "identification_assertion_protocol_id")
  private Protocol identificationAssertionProtocol1;

  @Column(name = "identification_assertion_remarks")
  private String identificationAssertionRemarks;
}
