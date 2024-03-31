package com.example.salesmanagementsystem.dto.reporting;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class DateRangeRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
