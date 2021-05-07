package ctcorp.test;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/svc/tag")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {

    @Inject
    TagRepository tagRepo;

    @GET
    public Response getPosts(){
        List<TblTags> tags = tagRepo.listAll();
        return Response.ok(tags).build();
    }

    @POST
    @Transactional
    public Response createPost(TblTags tblTags){
        tagRepo.persist(tblTags);
        if(tagRepo.isPersistent(tblTags)){
            return Response.created(URI.create("/svc/tag")).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id){
        return tagRepo.findByIdOptional(id)
            .map(post -> Response.ok(post).build())
            .orElse(Response.status(Status.NOT_FOUND).build());

    }
    
}
