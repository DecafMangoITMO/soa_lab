package dev.decafmango.service1.service;

import dev.decafmango.common.exception.ApplicationExceptionBuilder;
import dev.decafmango.common.mapper.spring.OrganizationMapper;
import dev.decafmango.common.model.Organization;
import dev.decafmango.common.model.dto.AverageEmployeeCount;
import dev.decafmango.common.model.dto.OrganizationsCount;
import dev.decafmango.common.model.dto.organization.FullOrganizationRequest;
import dev.decafmango.common.model.dto.organization.OrganizationDto;
import dev.decafmango.common.model.dto.organization.OrganizationsDto;
import dev.decafmango.common.model.dto.organization.PartialOrganizationRequest;
import dev.decafmango.service1.repository.OrganizationRepository;
import dev.decafmango.service1.util.FilteringParser;
import dev.decafmango.service1.util.SortingParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    public OrganizationsDto getOrganizations(String sorting, String filtering, int pageIndex, int pageSize, String fullNamePart) {
        Sort sort = SortingParser.parseSortingParam(sorting);
        Specification<Organization> specification = FilteringParser.parseFilteringParam(filtering);
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        Page<Organization> organizationsPage = organizationRepository.findAll(specification, pageable);

        List<OrganizationDto> organizations = organizationsPage.getContent().stream().map(organizationMapper::toDto).toList();
        if (fullNamePart != null && !fullNamePart.isBlank()) {
            organizations = organizations.stream()
                    .filter(organizationDto -> organizationDto.getName().contains(fullNamePart))
                    .toList();
        }

        return new OrganizationsDto(organizations);
    }

    public OrganizationDto getOrganizationById(int id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> ApplicationExceptionBuilder.notFound("Организация с идентификатором %d не найдена".formatted(id)));
        return organizationMapper.toDto(organization);
    }

    public AverageEmployeeCount getAverageEmployeeCount() {
        return new AverageEmployeeCount(organizationRepository.averageEmployeeCount());
    }

    public OrganizationsCount getOrganizationsCountByAnnualTurnover(double annualTurnover) {
        return new OrganizationsCount(organizationRepository.countAllByAnnualTurnover(annualTurnover));
    }

    public OrganizationDto createOrganization(FullOrganizationRequest fullOrganizationRequest) {
        Organization organization = organizationMapper.toModel(fullOrganizationRequest);

        return organizationMapper.toDto(organizationRepository.save(organization));
    }

    @Transactional
    public OrganizationDto updateOrganizationFullyById(int id, FullOrganizationRequest fullOrganizationRequest) {
        Organization oldOrg = organizationRepository.findById(id).orElseThrow(() -> ApplicationExceptionBuilder.notFound("Организация с идентификатором %d не найдена".formatted(id)));

        Organization newOrg = organizationMapper.toModel(fullOrganizationRequest);
        newOrg.setId(oldOrg.getId());
        newOrg.setCreationDate(oldOrg.getCreationDate());

        return organizationMapper.toDto(organizationRepository.save(newOrg));
    }

    @Transactional
    public OrganizationDto updateOrganizationPartlyById(int id, PartialOrganizationRequest partialOrganizationRequest) {
        Organization oldOrg = organizationRepository.findById(id).orElseThrow(() -> ApplicationExceptionBuilder.notFound("Организация с идентификатором %d не найдена".formatted(id)));

        Organization newOrg = organizationMapper.toModel(oldOrg, partialOrganizationRequest);
        return organizationMapper.toDto(organizationRepository.save(newOrg));
    }

    @Transactional
    public void deleteOrganizationById(int id) {
        organizationRepository.findById(id).orElseThrow(() -> ApplicationExceptionBuilder.notFound("Организация с идентификатором %d не найдена".formatted(id)));
        organizationRepository.deleteById((long) id);
    }

}
