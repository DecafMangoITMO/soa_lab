package dev.decafmango.service1.controller;

import dev.decafmango.common.model.dto.AverageEmployeeCount;
import dev.decafmango.common.model.dto.OrganizationsCount;
import dev.decafmango.common.model.dto.organization.FullOrganizationRequest;
import dev.decafmango.common.model.dto.organization.OrganizationDto;
import dev.decafmango.common.model.dto.organization.OrganizationsDto;
import dev.decafmango.common.model.dto.organization.PartialOrganizationRequest;
import dev.decafmango.service1.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping(produces = "application/xml")
    public OrganizationsDto getOrganizations(
            @RequestParam(name = "sorting", required = false) String sorting,
            @RequestParam(name = "filtering", required = false) String filtering,
            @RequestParam(name = "page-index", defaultValue = "0") int pageIndex,
            @RequestParam(name = "page-size", defaultValue = "10") int pageSize,
            @RequestParam(name = "full-name-part", required = false) String fullNamePart
    ) {
        log.info("Got request for getting organizations with page index {} and page size {}", pageIndex, pageSize);
        return organizationService.getOrganizations(sorting, filtering, pageIndex, pageSize, fullNamePart);
    }

    @GetMapping(value = "/{id}", produces = "application/xml")
    public OrganizationDto getOrganizationById(@PathVariable int id) {
        log.info("Got request for getting organization with id {}", id);
        return organizationService.getOrganizationById(id);
    }

    @GetMapping(value = "/average/employee-count", produces = "application/xml")
    public AverageEmployeeCount getAverageEmployeeCount() {
        log.info("Got request for getting average employee count");
        return organizationService.getAverageEmployeeCount();
    }

    @GetMapping(value = "/count", produces = "application/xml")
    public OrganizationsCount getOrganizationsCountByAnnualTurnover(@RequestParam(name = "annual-turnover") double annualTurnover) {
        log.info("Got request for getting organizations count by annualTurnover {}", annualTurnover);
        return organizationService.getOrganizationsCountByAnnualTurnover(annualTurnover);
    }

    @PostMapping(consumes = "application/xml", produces = "application/xml")
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationDto createOrganization(@Valid @RequestBody FullOrganizationRequest fullOrganizationRequest) {
        log.info("Got request for creating organization {}", fullOrganizationRequest);
        return organizationService.createOrganization(fullOrganizationRequest);
    }

    @PutMapping(value = "/{id}", consumes = "application/xml", produces = "application/xml")
    public OrganizationDto updateOrganizationFullyById(@PathVariable int id, @Valid @RequestBody FullOrganizationRequest fullOrganizationRequest) {
        log.info("Got request for updating organization fully with id {}: {}", id, fullOrganizationRequest);
        return organizationService.updateOrganizationFullyById(id, fullOrganizationRequest);
    }

    @PatchMapping(value = "/{id}", consumes = "application/xml", produces = "application/xml")
    public OrganizationDto updateOrganizationPartlyById(@PathVariable int id, @RequestBody PartialOrganizationRequest partialOrganizationRequest) {
        log.info("Got request for updating organization partly with if {}: {}", id, partialOrganizationRequest);
        return organizationService.updateOrganizationPartlyById(id, partialOrganizationRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganizationById(@PathVariable int id) {
        log.info("Got request for deleting organization with id {}", id);
        organizationService.deleteOrganizationById(id);
    }

}
