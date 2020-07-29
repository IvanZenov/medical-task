package com.elinext.model;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
    private Long id;
    private Date startDate;
    private Date endDate;
    private boolean active;
    private Long roomId;
    private Long userId;
    private Long manipulationId;

    public Reservation(Date startDate, Date endDate, boolean active, Long roomId, Long userId, Long manipulationId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.roomId = roomId;
        this.userId = userId;
        this.manipulationId = manipulationId;
    }
}
