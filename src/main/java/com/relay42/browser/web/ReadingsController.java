package com.relay42.browser.web;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.relay42.browser.service.ReadingRequestException;
import com.relay42.browser.service.ReadingsService;
import com.relay42.generated.ReadingRequest;
import com.relay42.generated.ReadingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/readings")
@Validated
public class ReadingsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadingsController.class);
    private final ReadingsService readingsService;

    public ReadingsController(ReadingsService readingsService) {
        this.readingsService = readingsService;
    }

    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity<ReadingResponse> activity(@RequestBody @Valid ReadingRequest readingRequest) throws ReadingRequestException {
        return ResponseEntity.ok(readingsService.getReadings(readingRequest));
    }

    @ExceptionHandler({InvalidDefinitionException.class, ReadingRequestException.class})
    public ResponseEntity handleAllExceptions(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
