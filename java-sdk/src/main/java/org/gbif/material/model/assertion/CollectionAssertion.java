package org.gbif.material.model.assertion;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.gbif.material.model.Collection;
import org.gbif.material.model.Protocol;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "collection_assertion")
public class CollectionAssertion {
  @Id
  @Column(name = "collection_assertion_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "collection_id", nullable = false)
  private Collection collection;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "collection_parent_assertion_id")
  private CollectionAssertion collectionParentAssertion;

  @Column(name = "collection_assertion_type", nullable = false)
  private String collectionAssertionType;

  @Column(name = "collection_assertion_made_date")
  private String collectionAssertionMadeDate;

  @Column(name = "collection_assertion_effective_date")
  private String collectionAssertionEffectiveDate;

  @Column(name = "collection_assertion_value")
  private String collectionAssertionValue;

  @Column(name = "collection_assertion_value_numeric")
  private BigDecimal collectionAssertionValueNumeric;

  @Column(name = "collection_assertion_unit")
  private String collectionAssertionUnit;

  @Column(name = "collection_assertion_by_collection_name")
  private String collectionAssertionByCollectionName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "collection_assertion_by_collection_id")
  private Collection collectionAssertionByCollection;

  @Column(name = "collection_assertion_protocol")
  private String collectionAssertionProtocol;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "collection_assertion_protocol_id")
  private Protocol collectionAssertionProtocol1;

  @Column(name = "collection_assertion_remarks")
  private String collectionAssertionRemarks;
}
