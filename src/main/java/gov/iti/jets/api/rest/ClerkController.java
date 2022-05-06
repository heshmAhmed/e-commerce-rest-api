package gov.iti.jets.api.rest;

import java.util.Optional;
import gov.iti.jets.api.dtos.ErrorMessage;
import gov.iti.jets.api.dtos.PostClerkRequest;
import gov.iti.jets.service.ClerkService;
import gov.iti.jets.service.dtos.ClerkDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("clerks")
public class ClerkController {
    private final ClerkService clerkService = ClerkService.INSTANCE;

    @GET
    public Response getAllClerks(){
        return Response.ok().entity(clerkService.getAllClerks()).build();
    } 

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClerk(@PathParam("id") Long id) {
        Optional<ClerkDto> clerkDtoOptional = clerkService.getClerk(id);
        if(clerkDtoOptional.isEmpty()){
            ErrorMessage errorMessage = new ErrorMessage("Resource not found",404, "Probably wrong id");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.ok().entity(clerkDtoOptional.get()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createClerk(PostClerkRequest clerk){
        ClerkDto clerkDto = new ClerkDto(clerk.getName(), clerk.getEmail(), clerk.getHireDate(), clerk.getPassword());
        Optional<ClerkDto> clerkDtoOptional = clerkService.insertClerk(clerkDto);
        if(clerkDtoOptional.isEmpty()){
            ErrorMessage errorMessage = new ErrorMessage("Resource could not be created",406, "Probably duplicate email");
            Response response = Response.status(Status.NOT_ACCEPTABLE).entity(errorMessage).build();
            throw new NotAcceptableException(response);
        }
        return Response.status(Status.CREATED).entity(clerkDtoOptional.get()).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteClerk(@PathParam("id") Long clerkId) {
        boolean deleted = clerkService.deleteClerk(clerkId);
        if(!deleted){
            ErrorMessage errorMessage = new ErrorMessage("Resource not found", 404, "Probably wrong id");
            Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
            throw new NotFoundException(response);
        }
        return Response.status(Status.OK).build();
    }
}
