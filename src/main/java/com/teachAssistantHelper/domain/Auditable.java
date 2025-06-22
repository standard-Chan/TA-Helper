package com.teachAssistantHelper.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Auditable {

    @ManyToOne
    private Staff createdBy;

    @ManyToOne
    private Staff updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

