package org.gbif.material.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "protocol_citation")
public class ProtocolCitation {
  @EmbeddedId private ProtocolCitationPK id;

  @MapsId("protocolId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "protocol_id", nullable = false)
  private Protocol protocol;

  @MapsId("protocolReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "protocol_reference_id", nullable = false)
  private Reference protocolReference;

  @Column(name = "protocol_citation_type")
  private String protocolCitationType;

  @Column(name = "protocol_citation_page_number")
  private String protocolCitationPageNumber;

  @Column(name = "protocol_citation_remarks")
  private String protocolCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class ProtocolCitationPK implements Serializable {
    private static final long serialVersionUID = -5531468224667630571L;

    @Column(name = "protocol_id", nullable = false)
    private String protocolId;

    @Column(name = "protocol_reference_id", nullable = false)
    private String protocolReferenceId;
  }
}
