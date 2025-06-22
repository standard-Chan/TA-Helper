package com.teachAssistantHelper.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WeeklyExtraClassRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private ExtraClass extraClass;

    private int weekNo;
    private String reason;
    private boolean attended;

    private LocalTime attendedTime;
    private LocalTime exitTime;
    private int testScore;

    @Embedded
    private Auditable auditable;
}

