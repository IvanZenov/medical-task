package com.elinext.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {
    private Long id;
    private int roomNumber;
    private String type;

    public Room (int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
    }

}
