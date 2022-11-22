package org.gbif.material.model;

import javax.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "protocol")
public class Protocol {
  @Id
  @Column(name = "protocol_id", nullable = false)
  private String id;

  @Column(name = "protocol_type", nullable = false)
  private String protocolType;
}
