package org.gbif.material.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Table(name = "citation")
public class Citation {
  @EmbeddedId private CitationPK id;

  @MapsId("citationReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "citation_reference_id", nullable = false)
  private Reference citationReference;

  @Column(name = "citation_type")
  private String citationType;

  @Column(name = "citation_page_number")
  private String citationPageNumber;

  @Column(name = "citation_remarks")
  private String citationRemarks;

  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Embeddable
  public static class CitationPK implements Serializable {
    private static final long serialVersionUID = -8802579618854726277L;

    @NotNull
    @Column(name = "citation_target_id", nullable = false)
    private String citationTargetId;

    @Column(name = "citation_target_type", columnDefinition = "common_targets not null")
    @Enumerated(EnumType.STRING)
    private Common.CommonTargetType citationTargetType;

    @NotNull
    @Column(name = "citation_reference_id", nullable = false)
    private String citationReferenceId;
  }
}
