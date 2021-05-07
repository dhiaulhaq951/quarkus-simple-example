package ctcorp.test;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PostRepository implements PanacheRepository<TblPost>{
    
    public List<TblPost> findByTitle(String title){
        return list("SELECT p FROM TblPost p WHERE p.title like ?1 ORDER BY id DESC", title);
    }

}
