package xyz.manojraw.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import xyz.manojraw.base.BaseEntity;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

}
