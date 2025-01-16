package org.example.expert.domain.log.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import lombok.Setter;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.user.entity.User;

@Entity
@Table(name = "log")
@Getter
@Setter
public class Log extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private boolean passOrNot = false;

  public Log() {
  }

  public Log(Long id, User user, boolean passOrNot) {
    this.Id = id;
    this.user = user;
    this.passOrNot = passOrNot;
  }

  public Log(User user, boolean passOrNot) {
    this.user = user;
    this.passOrNot = passOrNot;
  }

  public Log(User user) {
    this.user = user;

  }

  public static Log updateLog(Long id, User user, boolean passOrNot) {
    return new Log(id,user,passOrNot);
  }
}
