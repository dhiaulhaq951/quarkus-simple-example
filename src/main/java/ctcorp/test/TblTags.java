package ctcorp.test;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TblTags {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100)
    private String label;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TblPost> posts;

    public void setId(Long id){
        this.id = id;
    }
    
    public Long getId(){
        return this.id;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

    public void setPosts(List<TblPost> posts){
        this.posts = posts;
    }

    public List<TblPost> getPosts(){
        return this.posts;
    }

}
