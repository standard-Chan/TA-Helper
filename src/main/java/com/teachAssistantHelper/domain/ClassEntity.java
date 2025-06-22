package com.teachAssistantHelper.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "class") // 'class'는 예약어라 명시 필요
public class ClassEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Academy academy;

    @ManyToOne
    private ClassType classType;

    @Enumerated(EnumType.STRING)
    private DayOfWeek days;

    private LocalTime startTime;
    private LocalTime endTime;
}
