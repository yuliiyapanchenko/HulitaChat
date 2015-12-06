package com.jpanchenko.chat.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Julia on 05.12.2015.
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "creation_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime creationTime;

    @Column(name = "modification_time", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modificationTime;

    @Version
    private long version;

    @PrePersist
    public void prePersist() {
        DateTime now = DateTime.now();
        creationTime = now;
        modificationTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        modificationTime = DateTime.now();
    }
}
