package org.gbif.material.model.agent;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gbif.material.model.EntityRelationship;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "entity_relationship_agent_role")
public class EntityRelationshipAgentRole {
  @EmbeddedId private EntityRelationshipAgentRolePK id;

  @MapsId("entityRelationshipId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_relationship_id", nullable = false)
  private EntityRelationship entityRelationship;

  @MapsId("agentId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "agent_id", nullable = false)
  private Agent agent;

  @Column(name = "agent_name")
  private String agentName;

  @Column(name = "entity_relationship_agent_role")
  private String entityRelationshipAgentRole;

  @Column(name = "entity_relationship_agent_role_began")
  private String entityRelationshipAgentRoleBegan;

  @Column(name = "entity_relationship_agent_role_ended")
  private String entityRelationshipAgentRoleEnded;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class EntityRelationshipAgentRolePK implements Serializable {

    private static final long serialVersionUID = 8884400923141303757L;

    @Column(name = "entity_relationship_id", nullable = false)
    private String entityRelationshipId;

    @Column(name = "agent_id", nullable = false)
    private String agentId;

    @Column(name = "entity_agent_role_order", nullable = false)
    private Short entityAgentRoleOrder;
  }
}
