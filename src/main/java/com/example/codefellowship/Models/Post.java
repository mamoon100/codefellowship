package com.example.codefellowship.Models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "post")
@Entity
public class Post {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    @CreationTimestamp
    private Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "application_user_id")
    private ApplicationUser applicationUser;

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public Long getId() {
        return id;
    }

    public Post() {
    }

    public Post(String body, Timestamp createdAt, ApplicationUser applicationUser) {
        this.body = body;
        this.createdAt = createdAt;
        this.applicationUser = applicationUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }
}
