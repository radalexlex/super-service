package org.tuvarna;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminInnerResource {

    private static final Logger log = LoggerFactory.getLogger(AdminInnerResource.class);

    @RestClient
    AdminCreationClient client;

    @Inject
    JwtService jwtService;

    @POST
    @Path("/create")
    @PermitAll
    public Response createAdmin(RegistrationDto dto) throws Exception {
        log.error(dto.toString());
        try {
            return client.createSuperUser(
                    "JwtToken=" + jwtService.jwtGenerator(),
                    new RegistrationDto(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
