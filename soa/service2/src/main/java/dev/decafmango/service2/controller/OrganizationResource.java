package dev.decafmango.service2.controller;

import dev.decafmango.service2.mapper.OrganizationDtoMapper;
import dev.decafmango.service2.service.OrganizationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1")
public class OrganizationResource {

    @Inject
    private OrganizationService organizationService;

    @Inject
    private OrganizationDtoMapper organizationDtoMapper;

    @GET
    @Path("/hello")
    public String hello() {
        return "hello";
    }

    @POST
    @Path("/merge/{id1}/{id2}/new-name/new-address")
    @Produces(MediaType.APPLICATION_XML)
    public String merge(@PathParam("id1") int id1, @PathParam("id2") int id2) throws Exception {
        return organizationDtoMapper.generateXmlBody(organizationService.merge(id1, id2));
    }

    @PUT
    @Path("/hire/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public String hire(@PathParam("id") int id) throws Exception {
        return organizationDtoMapper.generateXmlBody(organizationService.hire(id));
    }

}