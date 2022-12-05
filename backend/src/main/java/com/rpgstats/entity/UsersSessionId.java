package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class UsersSessionId implements Serializable {
  private static final long serialVersionUID = -2782719994177207599L;

  @NotNull
  @Column(name = "user_id", nullable = false)
  private Integer userId;

  @NotNull
  @Column(name = "session_id", nullable = false)
  private Integer sessionId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    UsersSessionId entity = (UsersSessionId) o;
    return Objects.equals(this.sessionId, entity.sessionId)
        && Objects.equals(this.userId, entity.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sessionId, userId);
  }
}
