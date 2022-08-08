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
@Table(name = "FITTINGS", schema = "SCADA")
@NamedQuery(name = "FittingEntity.byBrand", query = "select e from FittingsEntity e where e.brand = ?1")
@XmlRootElement
public class FittingsEntity {

    @JsonbTransient
    private String muid;

    private String brand;
    private String fitName;
    private Long fitNum;
    private Long fitType;
    private String fitDesc;
    private Long fitDu;
    private Long fitDriveType;
    private Long fitPower;
    private Long fitStat;
    private Long fitBypassStat;
    private Long fitJumperStat;
    private String clientId;

    @JsonbDateFormat("dd.MM.yyyy HH:mm:ss")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime timeStamp;

    @Id
    @GeneratedValue(generator = "fitting-generator")
    @GenericGenerator(name = "fitting-generator", strategy = "ru.tecon.scadaApi.entity.util.MyGenerator")
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
    @Column(name = "FIT_NAME")
    public String getFitName() {
        return fitName;
    }

    public void setFitName(String fitName) {
        this.fitName = fitName;
    }

    @Basic
    @Column(name = "FIT_NUM")
    public Long getFitNum() {
        return fitNum;
    }

    public void setFitNum(Long fitNum) {
        this.fitNum = fitNum;
    }

    @Basic
    @Column(name = "FIT_TYPE")
    public Long getFitType() {
        return fitType;
    }

    public void setFitType(Long fitType) {
        this.fitType = fitType;
    }

    @Basic
    @Column(name = "FIT_DESC")
    public String getFitDesc() {
        return fitDesc;
    }

    public void setFitDesc(String fitDesc) {
        this.fitDesc = fitDesc;
    }

    @Basic
    @Column(name = "FIT_DU")
    public Long getFitDu() {
        return fitDu;
    }

    public void setFitDu(Long fitDu) {
        this.fitDu = fitDu;
    }

    @Basic
    @Column(name = "FIT_DRIVE_TYPE")
    public Long getFitDriveType() {
        return fitDriveType;
    }

    public void setFitDriveType(Long fitDriveType) {
        this.fitDriveType = fitDriveType;
    }

    @Basic
    @Column(name = "FIT_POWER")
    public Long getFitPower() {
        return fitPower;
    }

    public void setFitPower(Long fitPower) {
        this.fitPower = fitPower;
    }

    @Basic
    @Column(name = "FIT_STAT")
    public Long getFitStat() {
        return fitStat;
    }

    public void setFitStat(Long fitStat) {
        this.fitStat = fitStat;
    }

    @Basic
    @Column(name = "FIT_BYPASS_STAT")
    public Long getFitBypassStat() {
        return fitBypassStat;
    }

    public void setFitBypassStat(Long fitBypassStat) {
        this.fitBypassStat = fitBypassStat;
    }

    @Basic
    @Column(name = "FIT_JUMPER_STAT")
    public Long getFitJumperStat() {
        return fitJumperStat;
    }

    public void setFitJumperStat(Long fitJumperStat) {
        this.fitJumperStat = fitJumperStat;
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
        FittingsEntity that = (FittingsEntity) o;
        return Objects.equals(muid, that.muid) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(fitName, that.fitName) &&
                Objects.equals(fitNum, that.fitNum) &&
                Objects.equals(fitType, that.fitType) &&
                Objects.equals(fitDesc, that.fitDesc) &&
                Objects.equals(fitDu, that.fitDu) &&
                Objects.equals(fitDriveType, that.fitDriveType) &&
                Objects.equals(fitPower, that.fitPower) &&
                Objects.equals(fitStat, that.fitStat) &&
                Objects.equals(fitBypassStat, that.fitBypassStat) &&
                Objects.equals(fitJumperStat, that.fitJumperStat) &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(muid, brand, fitName, fitNum, fitType, fitDesc, fitDu, fitDriveType, fitPower, fitStat, fitBypassStat, fitJumperStat, clientId, timeStamp);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FittingsEntity.class.getSimpleName() + "[", "]")
                .add("muid='" + muid + "'")
                .add("brand='" + brand + "'")
                .add("fitName='" + fitName + "'")
                .add("fitNum=" + fitNum)
                .add("fitType=" + fitType)
                .add("fitDesc='" + fitDesc + "'")
                .add("fitDu=" + fitDu)
                .add("fitDriveType=" + fitDriveType)
                .add("fitPower=" + fitPower)
                .add("fitStat=" + fitStat)
                .add("fitBypassStat=" + fitBypassStat)
                .add("fitJumperStat=" + fitJumperStat)
                .add("clientId='" + clientId + "'")
                .add("timeStamp=" + timeStamp)
                .toString();
    }
}
