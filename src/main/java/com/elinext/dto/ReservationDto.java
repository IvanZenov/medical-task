package com.elinext.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private Timestamp startDate;
    private Timestamp endDate;
    private boolean active;
    private int roomNumber;
    private String roomType;
    private String username;
    private Long userId;
    private String manipulationName;

}
