package com.spring.myfood.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ranking {

    public Ranking(String title, String type, int score) {
        this.title = title;
        this.type = type;
        this.score = score;
    }

    @Id
    private String id;

    @Setter
    @Field
    private String title;

    @Setter
    @Field
    private int score = 1;

    @Setter
    @Field
    private String type;

    @JsonIgnore
    @CreatedDate
    private Date createdAt;

    @JsonIgnore
    @LastModifiedDate
    private Date updatedAt;

    @Override
    public String toString() {
        return "Ranking{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", score=" + score +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}