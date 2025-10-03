package dev.decafmango.service2.controller;

import dev.decafmango.service2.mapper.OrganizationDtoMapper;
import dev.decafmango.service2.service.OrganizationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/v1")
public class OrganizationResource {

    @Inject
    private OrganizationService organizationService;

    @Inject
    private OrganizationDtoMapper organizationDtoMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(OrganizationResource.class);

    @GET
    @Path("/hello")
    public String hello() {
        LOGGER.info("hello");
        return "hello";
    }

    @POST
    @Path("/merge/{id1}/{id2}/new-name/new-address")
    @Produces(MediaType.APPLICATION_XML)
    public String merge(@PathParam("id1") int id1, @PathParam("id2") int id2) throws Exception {
        LOGGER.info("merge {} {}", id1, id2);
        return organizationDtoMapper.generateXmlBody(organizationService.merge(id1, id2));
    }

    @POST
    @Path("/hire/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public String hire(@PathParam("id") int id) throws Exception {
        LOGGER.info("hire {}", id);
        return organizationDtoMapper.generateXmlBody(organizationService.hire(id));
    }

}