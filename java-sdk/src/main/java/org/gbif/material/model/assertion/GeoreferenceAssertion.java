package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.Georeference;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "georeference_assertion")
public class GeoreferenceAssertion {
  @Id
  @Column(name = "georeference_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "georeference_id", nullable = false)
  private Georeference georeference;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "georeference_parent_assertion_id")
  private GeoreferenceAssertion georeferenceParentAssertion;

  @Column(name = "georeference_assertion_type", nullable = false)
  private String georeferenceAssertionType;

  @Column(name = "georeference_assertion_made_date")
  private String georeferenceAssertionMadeDate;

  @Column(name = "georeference_assertion_effective_date")
  private String georeferenceAssertionEffectiveDate;

  @Column(name = "georeference_assertion_value")
  private String georeferenceAssertionValue;

  @Column(name = "georeference_assertion_value_numeric")
  private BigDecimal georeferenceAssertionValueNumeric;

  @Column(name = "georeference_assertion_unit")
  private String georeferenceAssertionUnit;

  @Column(name = "georeference_assertion_by_agent_name")
  private String georeferenceAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "georeference_assertion_by_agent_id")
  private Agent georeferenceAssertionByAgent;

  @Column(name = "georeference_assertion_protocol")
  private String georeferenceAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "georeference_assertion_protocol_id")
  private Protocol georeferenceAssertionProtocol1;

  @Column(name = "georeference_assertion_remarks")
  private String georeferenceAssertionRemarks;
}
