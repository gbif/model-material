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
@Table(name = "material_group_assertion")
public class MaterialGroupAssertion {
  @Id
  @Column(name = "material_group_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_group_id", nullable = false)
  private MaterialGroup materialGroup;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_group_parent_assertion_id")
  private MaterialGroupAssertion materialGroupParentAssertion;

  @Column(name = "material_group_assertion_type", nullable = false)
  private String materialGroupAssertionType;

  @Column(name = "material_group_assertion_made_date")
  private String materialGroupAssertionMadeDate;

  @Column(name = "material_group_assertion_effective_date")
  private String materialGroupAssertionEffectiveDate;

  @Column(name = "material_group_assertion_value")
  private String materialGroupAssertionValue;

  @Column(name = "material_group_assertion_value_numeric")
  private BigDecimal materialGroupAssertionValueNumeric;

  @Column(name = "material_group_assertion_unit")
  private String materialGroupAssertionUnit;

  @Column(name = "material_group_assertion_by_agent_name")
  private String materialGroupAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_group_assertion_by_agent_id")
  private Agent materialGroupAssertionByAgent;

  @Column(name = "material_group_assertion_protocol")
  private String materialGroupAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_group_assertion_protocol_id")
  private Protocol materialGroupAssertionProtocol1;

  @Column(name = "material_group_assertion_remarks")
  private String materialGroupAssertionRemarks;
}
