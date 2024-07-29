package org.example.controller;


import org.example.aggregator.AggregatorService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/aggregator")
public class AggregatorController {

    AggregatorService serviceAgregator = AggregatorService.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById() {
        return Response.ok(serviceAgregator.aggregareData()).build();
    }

}
