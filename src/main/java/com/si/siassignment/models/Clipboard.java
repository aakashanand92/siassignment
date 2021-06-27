package com.si.siassignment.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
public class Clipboard {

    public static final Integer TEXT_LIMIT = 1000;
    public static final Integer TITLE_LIMIT = 250;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(length = 1000)
    private String textContent;
    private Date expiresAt;
    private Integer exposure;
    private String title;
    private String password;
    private String tinyURL;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;


    public boolean isPrivate() {
        return exposure == Exposure.PRIVATE;
    }

}
