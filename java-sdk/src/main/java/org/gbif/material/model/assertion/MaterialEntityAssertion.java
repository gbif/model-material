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
@Table(name = "material_entity_assertion")
public class MaterialEntityAssertion {
  @Id
  @Column(name = "material_entity_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_id", nullable = false)
  private MaterialEntity materialEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_parent_assertion_id")
  private MaterialEntityAssertion materialEntityParentAssertion;

  @Column(name = "material_entity_assertion_type", nullable = false)
  private String materialEntityAssertionType;

  @Column(name = "material_entity_assertion_made_date")
  private String materialEntityAssertionMadeDate;

  @Column(name = "material_entity_assertion_effective_date")
  private String materialEntityAssertionEffectiveDate;

  @Column(name = "material_entity_assertion_value")
  private String materialEntityAssertionValue;

  @Column(name = "material_entity_assertion_value_numeric")
  private BigDecimal materialEntityAssertionValueNumeric;

  @Column(name = "material_entity_assertion_unit")
  private String materialEntityAssertionUnit;

  @Column(name = "material_entity_assertion_by_agent_name")
  private String materialEntityAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_assertion_by_agent_id")
  private Agent materialEntityAssertionByAgent;

  @Column(name = "material_entity_assertion_protocol")
  private String materialEntityAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_assertion_protocol_id")
  private Protocol materialEntityAssertionProtocol1;

  @Column(name = "material_entity_assertion_remarks")
  private String materialEntityAssertionRemarks;
}
