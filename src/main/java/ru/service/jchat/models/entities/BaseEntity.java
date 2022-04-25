package ru.service.jchat.models.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "created")
    private ZonedDateTime created = ZonedDateTime.now();

    @Column(name = "updated")
    private ZonedDateTime updated = ZonedDateTime.now();

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }
}
