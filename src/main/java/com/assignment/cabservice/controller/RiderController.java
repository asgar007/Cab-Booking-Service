package com.assignment.cabservice.controller;

import com.assignment.cabservice.dto.RiderDTO;
import com.assignment.cabservice.response.SuccessResponse;
import com.assignment.cabservice.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;
    @PostMapping
    public ResponseEntity<SuccessResponse> createRider(@NotNull @RequestBody @Valid RiderDTO riderDTO){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.CREATED.toString())
                        .message("Rider created successfully")
                        .data(riderService.createRider(riderDTO)).build());
    }


}
