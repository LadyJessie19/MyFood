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
@RequestMapping("/")
@Tag(name = "Main - Home and Health", description = "Home and Health endpoints")
public class MainController {

    @Operation(summary = "This is the landing route to the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Home is ok", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "Error - Not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) })
    @GetMapping
    public String home() {
        return "Hi! This is the MyFood API. Check out the docs at https://app-myfood-production.up.railway.app/swagger-ui/index.html#/";
    }

    @Operation(summary = "Check if MyFood is running")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Api is ok", content = {
                    @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "Api is not ok", content = @Content) })
    @GetMapping("/health")
    public String health() {
        return "Is MyFood running? I'm thumbs up!";
    }
}
