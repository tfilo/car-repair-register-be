package sk.tope.car_repair_register.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tope.car_repair_register.api.service.RepairLogApiService;
import sk.tope.car_repair_register.api.service.so.*;

@Tag(name = "repairLog")
@RestController
@RequestMapping(value = "/repair-log")
public class RepairLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepairLogController.class);

    private final RepairLogApiService repairLogApiService;

    public RepairLogController(RepairLogApiService repairLogApiService) {
        this.repairLogApiService = repairLogApiService;
    }

    @Operation(
            description = "Get repair log by id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<RepairLogSo> getRepairLogById(@PathVariable("id") Long id) {
        LOGGER.debug("getRepairLogById({})", id);
        return new ResponseEntity<>(repairLogApiService.get(id), HttpStatus.OK);
    }


    @Operation(
            description = "Find repair logs by query string, vehicleId and pageable.",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page", content = @Content(schema = @Schema(type = "integer"))),
                    @Parameter(in = ParameterIn.QUERY, name = "size", content = @Content(schema = @Schema(type = "integer"))),
                    @Parameter(in = ParameterIn.QUERY, name = "sort",
                            content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))
                    )
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = {
                    @Content(schema = @Schema(implementation = RepairLogPagedModel.class))
            }),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @GetMapping
    public ResponseEntity<Page<RepairLogSo>> findRepairLogs(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "vehicleId", required = false) Long vehicleId, @Parameter(hidden = true) Pageable pageable) {
        LOGGER.debug("findRepairLogs({},{},{})", query, vehicleId, pageable);
        return new ResponseEntity<>(repairLogApiService.find(query, vehicleId, pageable), HttpStatus.OK);
    }

    @Operation(
            description = "Create new repair log."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "409", description = "conflict", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @PostMapping
    public ResponseEntity<RepairLogSo> createRepairLog(@Valid @RequestBody RepairLogCreateSo repairLogCreateSo) {
        LOGGER.debug("createRepairLog({})", repairLogCreateSo);
        return new ResponseEntity<>(repairLogApiService.create(repairLogCreateSo), HttpStatus.CREATED);
    }

    @Operation(
            description = "Update existing repair log."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "409", description = "conflict", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<RepairLogSo> updateRepairLog(@PathVariable("id") Long id, @Valid @RequestBody RepairLogUpdateSo repairLogUpdateSo) {
        LOGGER.debug("updateRepairLog({},{})", id, repairLogUpdateSo);
        return new ResponseEntity<>(repairLogApiService.update(id, repairLogUpdateSo), HttpStatus.OK);
    }

    @Operation(
            description = "Delete repair log with all its records."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "unauthorized", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "403", description = "forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "404", description = "not found", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
            @ApiResponse(responseCode = "500", description = "internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorMessageSo.class))
            }),
    })
    @DeleteMapping(value = "/{id}")
    public void deleteRepairLogById(@PathVariable("id") Long id) {
        LOGGER.debug("deleteRepairLogById({})", id);
        repairLogApiService.delete(id);
    }
}
