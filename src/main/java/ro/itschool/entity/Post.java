package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer id;

    @Column(nullable = false)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;

    @OneToMany
    private Set<Reply> replies = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "mentions")
    private Set<SpringUser> mentions = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "likes")
    private Set<SpringUser> likes = new LinkedHashSet<>();

    public Post(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
