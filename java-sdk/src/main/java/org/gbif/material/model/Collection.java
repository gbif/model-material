package org.gbif.material.model;

import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "collection")
public class Collection {
  @Id
  @Column(name = "collection_id", nullable = false)
  private String id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "collection_id", nullable = false)
  private Entity entity;

  @Column(name = "collection_type")
  private String collectionType;

  @Column(name = "collection_code")
  private String collectionCode;

  @Column(name = "institution_code")
  private String institutionCode;

  @Column(name = "grscicoll_id", nullable = false)
  private UUID grscicollId;
}
