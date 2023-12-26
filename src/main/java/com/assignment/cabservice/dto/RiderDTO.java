package com.assignment.cabservice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderDTO extends UserDTO {

//    @NotNull(message = "Last ride cannot be null") //for UI
//    private String lastRideId;

    private int ridesTaken;
}
