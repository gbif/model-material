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
@Table(name = "organism_assertion")
public class OrganismAssertion {
  @Id
  @Column(name = "organism_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "organism_id", nullable = false)
  private org.gbif.material.model.Entity organism;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "organism_parent_assertion_id")
  private OrganismAssertion organismParentAssertion;

  @Column(name = "organism_assertion_type", nullable = false)
  private String organismAssertionType;

  @Column(name = "organism_assertion_made_date")
  private String organismAssertionMadeDate;

  @Column(name = "organism_assertion_effective_date")
  private String organismAssertionEffectiveDate;

  @Column(name = "organism_assertion_value")
  private String organismAssertionValue;

  @Column(name = "organism_assertion_value_numeric")
  private BigDecimal organismAssertionValueNumeric;

  @Column(name = "organism_assertion_unit")
  private String organismAssertionUnit;

  @Column(name = "organism_assertion_by_agent_name")
  private String organismAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "organism_assertion_by_agent_id")
  private Agent organismAssertionByAgent;

  @Column(name = "organism_assertion_protocol")
  private String organismAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "organism_assertion_protocol_id")
  private Protocol organismAssertionProtocol1;

  @Column(name = "organism_assertion_remarks")
  private String organismAssertionRemarks;
}
