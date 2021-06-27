package com.si.siassignment.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClipboardStats {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer clipboardId;
    private Integer readCount;
    private Integer editCount;

    public ClipboardStats(Integer clipboardId) {
        this.clipboardId = clipboardId;
        readCount = 0;
        editCount = 0;
    }
}
