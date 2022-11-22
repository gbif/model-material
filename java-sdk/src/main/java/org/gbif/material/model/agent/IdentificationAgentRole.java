package org.gbif.material.model.agent;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.identification.Identification;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "identification_agent_role")
public class IdentificationAgentRole {
  @EmbeddedId private IdentificationAgentRolePK id;

  @MapsId("identificationId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "identification_id", nullable = false)
  private Identification identification;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "identification_agent_role")
  private String identificationAgentRole;

  @Column(name = "identification_agent_role_began")
  private String identificationAgentRoleBegan;

  @Column(name = "identification_agent_role_ended")
  private String identificationAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class IdentificationAgentRolePK implements Serializable {

    private static final long serialVersionUID = 3598560980570940904L;

    @Column(name = "identification_id", nullable = false)
    private String identificationId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "identification_agent_role_order", nullable = false)
    private Short identificationAgentRoleOrder;
  }
}
