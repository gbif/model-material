package org.gbif.material.model.identifier;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.*;
import org.gbif.material.model.Collection;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "collection_identifier")
public class CollectionIdentifier {
  @EmbeddedId private CollectionIdentifierPK id;

  @MapsId("collectionId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "collection_id", nullable = false)
  private Collection collection;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class CollectionIdentifierPK implements Serializable {
    private static final long serialVersionUID = -8917161812449101506L;

    @Column(name = "collection_id", nullable = false)
    private String collectionId;

    @Column(name = "collection_identifier", nullable = false)
    private String collectionIdentifier;

    @Column(name = "collection_identifier_type", nullable = false)
    private String collectionIdentifierType;
  }
}
