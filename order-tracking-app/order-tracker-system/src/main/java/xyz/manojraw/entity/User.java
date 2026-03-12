package xyz.manojraw.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.manojraw.base.BaseEntity;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email")
})
public class User extends BaseEntity {
    private String fullName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
}
