package main.java.mqttcamera.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@AllArgsConstructor
@DynamicUpdate
@Table(name = "history", schema = "public")
@Builder
public class AlarmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NonNull
    @Column(name = "description")
    private String description;
    @NonNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @NonNull
    @JoinColumn(name = "camera")
    @ManyToOne(targetEntity = CameraEntity.class, fetch = FetchType.EAGER)
    private CameraEntity camera;

    public AlarmEntity(CameraEntity camera, Date date, String description){
        this.camera = camera;
        this.date = date;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AlarmEntity that = (AlarmEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString(){
        return new StringBuilder().append("Alarm happens on camera ").append(camera)
                .append("at time ").append(date).toString();
    }
}
