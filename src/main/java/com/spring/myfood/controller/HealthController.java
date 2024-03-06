package com.spring.myfood.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/health")
@Tag(name = "Health", description = "Health endpoint")
public class HealthController {
    @GetMapping
    @Operation(summary = "Check if MyFood is running")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Api is ok", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "Api is not ok", content = @Content) })
    public String health() {
        return "Is MyFood running? I'm thumbs up!";
    }
}
