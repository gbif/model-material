package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.EntityRelationship;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "entity_relationship_assertion")
public class EntityRelationshipAssertion {
  @Id
  @Column(name = "entity_relationship_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_relationship_id", nullable = false)
  private EntityRelationship entityRelationship;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_relationship_parent_assertion_id")
  private EntityRelationshipAssertion entityRelationshipParentAssertion;

  @Column(name = "entity_relationship_assertion_type", nullable = false)
  private String entityRelationshipAssertionType;

  @Column(name = "entity_relationship_assertion_made_date")
  private String entityRelationshipAssertionMadeDate;

  @Column(name = "entity_relationship_assertion_effective_date")
  private String entityRelationshipAssertionEffectiveDate;

  @Column(name = "entity_relationship_assertion_value")
  private String entityRelationshipAssertionValue;

  @Column(name = "entity_relationship_assertion_value_numeric")
  private BigDecimal entityRelationshipAssertionValueNumeric;

  @Column(name = "entity_relationship_assertion_unit")
  private String entityRelationshipAssertionUnit;

  @Column(name = "entity_relationship_assertion_by_agent_name")
  private String entityRelationshipAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_relationship_assertion_by_agent_id")
  private Agent entityRelationshipAssertionByAgent;

  @Column(name = "entity_relationship_assertion_protocol")
  private String entityRelationshipAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_relationship_assertion_protocol_id")
  private Protocol entityRelationshipAssertionProtocol1;

  @Column(name = "entity_relationship_assertion_remarks")
  private String entityRelationshipAssertionRemarks;
}
