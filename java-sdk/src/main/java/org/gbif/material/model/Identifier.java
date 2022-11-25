package org.gbif.material.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@javax.persistence.Entity
@Table(name = "identifier")
public class Identifier {
  @EmbeddedId private IdentifierPK id;

  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  @Embeddable
  public static class IdentifierPK implements Serializable {
    private static final long serialVersionUID = -7969562749957046648L;

    @NotNull
    @Column(name = "identifier_target_id", nullable = false)
    private String identifierTargetId;

    @Column(name = "identifier_target_type", columnDefinition = "common_targets not null")
    @Enumerated(EnumType.STRING)
    private Common.CommonTargetType identifierTargetType;

    @Column(name = "identifier_type", nullable = false)
    private String identifierType;

    @NotNull
    @Column(name = "identifier_value", nullable = false)
    private String identifierValue;
  }
}
