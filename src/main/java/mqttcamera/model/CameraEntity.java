package main.java.mqttcamera.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "camera", schema = "public")
public class CameraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NonNull
    @Column(name = "tag")
    private String tag;
    @OneToMany(targetEntity = AlarmEntity.class,fetch = FetchType.EAGER)
    public List<AlarmEntity> alarmEntities;

    public CameraEntity(String tag){
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CameraEntity that = (CameraEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString(){
        return tag;
    }
}
