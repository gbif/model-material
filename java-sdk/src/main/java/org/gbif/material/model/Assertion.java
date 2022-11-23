package org.gbif.material.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@javax.persistence.Entity
@Table(name = "assertion")
public class Assertion {
  @Id
  @Column(name = "assertion_id", nullable = false)
  private String id;

  @NotNull
  @Column(name = "assertion_target_id", nullable = false)
  private String assertionTargetId;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "assertion_parent_assertion_id")
  private Assertion assertionParentAssertion;

  @NotNull
  @Column(name = "assertion_type", nullable = false)
  private String assertionType;

  @Column(name = "assertion_made_date")
  private String assertionMadeDate;

  @Column(name = "assertion_effective_date")
  private String assertionEffectiveDate;

  @Column(name = "assertion_value")
  private String assertionValue;

  @Column(name = "assertion_value_numeric")
  private BigDecimal assertionValueNumeric;

  @Column(name = "assertion_unit")
  private String assertionUnit;

  @Column(name = "assertion_by_agent_name")
  private String assertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "assertion_by_agent_id")
  private Agent assertionByAgent;

  @Column(name = "assertion_protocol")
  private String assertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "assertion_protocol_id")
  private Protocol assertionProtocol1;

  @Column(name = "assertion_remarks")
  private String assertionRemarks;

  @Column(name = "assertion_target_type", columnDefinition = "common_targets not null")
  @Enumerated(EnumType.STRING)
  private Common.CommonTargetType assertionTargetType;
}
