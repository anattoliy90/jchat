package ru.service.jchat.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "roles", schema = "jchat")
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_generator")
    @SequenceGenerator(name = "role_id_generator", schema = "jchat", sequenceName = "role_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title")
    @Enumerated(EnumType.STRING)
    private AuthorityEntity authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityEntity getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityEntity authority) {
        this.authority = authority;
    }
}
