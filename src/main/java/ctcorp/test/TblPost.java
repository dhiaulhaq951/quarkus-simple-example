package ctcorp.test;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@Entity
public class TblPost {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 200)
    private String title;

    @Column(length = 1000000)
    private String content;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "post_tag",
        joinColumns = {@JoinColumn(name="post_id")},
        inverseJoinColumns = {@JoinColumn(name="tags_id")}
    )
    private List<TblTags> tags;


    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setContent(String content){
        this.content = content;
    }    

    public String getContent(){
        return this.content;
    }

    public void setTags(List<TblTags> tags){
        this.tags = tags;
    }

    public List<TblTags> getTags(){
        return this.tags;
    }
    
}
