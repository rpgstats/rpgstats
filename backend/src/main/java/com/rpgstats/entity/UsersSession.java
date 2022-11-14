package com.rpgstats.entity;

import javax.persistence.*;

@Entity
@Table(name = "users__sessions")
public class UsersSession {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private UsersSessionId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("sessionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    public UsersSessionId getId() {
        return id;
    }

    public void setId(UsersSessionId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}