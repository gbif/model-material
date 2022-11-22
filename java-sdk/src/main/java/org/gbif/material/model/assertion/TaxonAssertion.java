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
import org.gbif.material.model.agent.Agent;
import org.gbif.material.model.identification.Taxon;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "taxon_assertion")
public class TaxonAssertion {
  @Id
  @Column(name = "taxon_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_id", nullable = false)
  private Taxon taxon;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_parent_assertion_id")
  private TaxonAssertion taxonParentAssertion;

  @Column(name = "taxon_assertion_type", nullable = false)
  private String taxonAssertionType;

  @Column(name = "taxon_assertion_made_date")
  private String taxonAssertionMadeDate;

  @Column(name = "taxon_assertion_effective_date")
  private String taxonAssertionEffectiveDate;

  @Column(name = "taxon_assertion_value")
  private String taxonAssertionValue;

  @Column(name = "taxon_assertion_value_numeric")
  private BigDecimal taxonAssertionValueNumeric;

  @Column(name = "taxon_assertion_unit")
  private String taxonAssertionUnit;

  @Column(name = "taxon_assertion_by_agent_name")
  private String taxonAssertionByAgentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_assertion_by_agent_id")
  private Agent taxonAssertionByAgent;

  @Column(name = "taxon_assertion_protocol")
  private String taxonAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_assertion_protocol_id")
  private Protocol taxonAssertionProtocol1;

  @Column(name = "taxon_assertion_remarks")
  private String taxonAssertionRemarks;
}
