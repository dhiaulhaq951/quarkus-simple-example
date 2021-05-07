package ctcorp.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/svc/post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Inject
    PostRepository postRepo;

    @Inject
    TagRepository tagRepo;

    @GET
    public Response getPosts(){
        List<TblPost> posts = postRepo.listAll();
        return Response.ok(posts).build();
    }

    @POST
    @Transactional
    public Response createPost(TblPost tblPost) throws RestException{

        List<TblTags> tagExists = new ArrayList<>();
        List<TblTags> tagNews = new ArrayList<>();
        List<TblPost> listpost = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        listpost.add(tblPost);
        if(tblPost.getTags() != null && !tblPost.getTags().isEmpty() && tblPost.getTags().size() > 0){
            for(TblTags tags : tblPost.getTags()){
                Optional<TblTags> tg = tagRepo.findByIdOptional(tags.getId());
                if(tg.isPresent()){
                    tagExists.add(tg.get());
                }
                else{
                    tagNews.add(tags);
                }
            }
        }

        if(!tagExists.isEmpty() && tagExists.size() > 0){
            tagExists.forEach(tg -> {
                tg.setPosts(listpost);
                tagRepo.persist(tg);
            });
        }
        if(!tagNews.isEmpty() && tagNews.size() > 0){
            tagNews.forEach(tg->{
                if(tg.getLabel().isEmpty() || tg.getLabel().equalsIgnoreCase("")){
                    errors.add("Tag Label is empty at tag id" + tg.getId());
                }
                tg.setId(null);
                tg.setPosts(listpost);
                tagRepo.persist(tg);
            });
        }

        if(!errors.isEmpty()){
            return Response.status(Status.BAD_REQUEST).entity(errors.toString()).build();
        }
        tagExists.addAll(tagNews);
        tblPost.setTags(tagExists);
        postRepo.persist(tblPost);
        
        if(postRepo.isPersistent(tblPost)){
            return Response.created(URI.create("/svc/post")).build();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    // @PUT
    // @Transactional
    // @Path("{id}")
    // public Response updatePost(@PathParam("id") Long id, TblPost tblPost){
    //     Set<TblTags> tagExists = new HashSet<>();
    //     Set<TblTags> tagNews = new HashSet<>();
    //     if()
    // }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id){
        return postRepo.findByIdOptional(id)
            .map(post -> Response.ok(post).build())
            .orElse(Response.status(Status.NOT_FOUND).build());

    }

    @GET
    @Path("title")
    public Response getByTitle(@QueryParam("title") String title){
        title += "'%" + title + "%'";
        List<TblPost> posts = postRepo.findByTitle(title);
        return Response.ok(posts).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteById(@PathParam("id") Long id){
        boolean deleted = postRepo.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Status.BAD_REQUEST).build();
    }
    
}
