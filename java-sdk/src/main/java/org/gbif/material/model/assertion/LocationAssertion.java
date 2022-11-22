package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.Location;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "location_assertion")
public class LocationAssertion {
  @Id
  @Column(name = "location_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_assertion_id", nullable = false)
  private Location locationAssertion;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_parent_assertion_id")
  private LocationAssertion locationParentAssertion;

  @Column(name = "location_assertion_type", nullable = false)
  private String locationAssertionType;

  @Column(name = "location_assertion_made_date")
  private String locationAssertionMadeDate;

  @Column(name = "location_assertion_effective_date")
  private String locationAssertionEffectiveDate;

  @Column(name = "location_assertion_value")
  private String locationAssertionValue;

  @Column(name = "location_assertion_value_numeric")
  private BigDecimal locationAssertionValueNumeric;

  @Column(name = "location_assertion_unit")
  private String locationAssertionUnit;

  @Column(name = "location_assertion_by_agent_name")
  private String locationAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_assertion_by_agent_id")
  private Agent locationAssertionByAgent;

  @Column(name = "location_assertion_protocol")
  private String locationAssertionProtocol;

  @Column(name = "location_assertion_protocol_id")
  private String locationAssertionProtocolId;

  @Column(name = "location_assertion_remarks")
  private String locationAssertionRemarks;
}
