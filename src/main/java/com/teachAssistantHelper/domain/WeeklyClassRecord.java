package com.teachAssistantHelper.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WeeklyClassRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private ClassEntity classEntity;

    private int weekNo;
    private boolean attended;
    private int testScore;
    private int homeworkScore;
    private String note;

    @Embedded
    private Auditable auditable;
}
