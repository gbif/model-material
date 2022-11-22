package org.gbif.material.model.citation;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.gbif.material.model.Reference;
import org.gbif.material.model.agent.Agent;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "agent_citation")
public class AgentCitation {
  @EmbeddedId private AgentCitationFK id;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @MapsId("agentReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_reference_id", nullable = false)
  private Reference agentReference;

  @Column(name = "agent_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String agentCitationType;

  @Column(name = "agent_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String agentCitationPageNumber;

  @Column(name = "agent_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String agentCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class AgentCitationFK implements Serializable {
    private static final long serialVersionUID = 3417723002321268612L;

    @NotNull
    @Column(name = "agent_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String agentId;

    @NotNull
    @Column(name = "agent_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String agentReferenceId;
  }
}
