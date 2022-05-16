package ru.tecon.scadaApi.entity;

import org.hibernate.annotations.GenericGenerator;
import ru.tecon.scadaApi.entity.util.LocalDateTimeAdapter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Maksim Shchelkonogov
 */
@Entity
@Table(name = "TUBES", schema = "SCADA")
@NamedQuery(name = "TubesEntity.byBrand", query = "select e from TubesEntity e where e.brand = ?1")
@XmlRootElement
public class TubesEntity {

    @JsonbTransient
    private String muid;

    private String brand;
    private Long status;
    private String clientId;

    @JsonbDateFormat("dd.MM.yyyy HH:mm:ss")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime timeStamp;

    @Id
    @GeneratedValue(generator = "tube-generator")
    @GenericGenerator(name = "tube-generator", strategy = "ru.tecon.scadaApi.entity.util.MyGenerator")
    @Column(name = "MUID")
    public String getMuid() {
        return muid;
    }

    private void setMuid(String muid) {
        this.muid = muid;
    }

    @Basic
    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Basic
    @Column(name = "CLIENT_ID")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "TIME_STAMP")
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @PrePersist
    public void prePersist() {
        this.timeStamp = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.timeStamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TubesEntity that = (TubesEntity) o;
        return Objects.equals(muid, that.muid) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(status, that.status) &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(muid, brand, status, clientId, timeStamp);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TubesEntity.class.getSimpleName() + "[", "]")
                .add("muid='" + muid + "'")
                .add("brand='" + brand + "'")
                .add("status=" + status)
                .add("clientId='" + clientId + "'")
                .add("timeStamp=" + timeStamp)
                .toString();
    }
}
