package ru.tecon.scadaApi.entity;

import ru.tecon.scadaApi.Constants;
import ru.tecon.scadaApi.entity.util.LocalDateTimeAdapter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
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
@Table(name = "hist_log", schema = "scada", catalog = "postgres")
@IdClass(HistLogEntityPK.class)
@NamedQueries({
        @NamedQuery(name = "HistLogEntity.byMuid", query = "select e from HistLogEntity e where e.muid = ?1"),
        @NamedQuery(name = "HistLogEntity.byDate", query = "select e from HistLogEntity e where e.timeStamp >= ?1 and e.timeStamp <= ?2"),
        @NamedQuery(name = "HistLogEntity.byMuidAndDate", query = "select e from HistLogEntity e where e.muid = ?1 and e.timeStamp >= ?2 and e.timeStamp <= ?3")
})
@XmlRootElement
public class HistLogEntity {

    @JsonbTransient
    private String tableName;

    @JsonbProperty("paramName")
    private String columnName;

    @JsonbTransient
    private String operation;
    private String muid;
    private String brand;
    private String clientId;
    private String oldValueChar;
    private String newValueChar;

    @JsonbDateFormat(Constants.DATE_TIME_FORMAT)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime timeStamp;

    @Id
    @Column(name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Id
    @Column(name = "column_name")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Id
    @Column(name = "operation")
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Id
    @Column(name = "muid")
    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    @Basic
    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "client_id")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "old_value_char")
    public String getOldValueChar() {
        return oldValueChar;
    }

    public void setOldValueChar(String oldValueChar) {
        this.oldValueChar = oldValueChar;
    }

    @Basic
    @Column(name = "new_value_char")
    public String getNewValueChar() {
        return newValueChar;
    }

    public void setNewValueChar(String newValueChar) {
        this.newValueChar = newValueChar;
    }

    @Id
    @Column(name = "time_stamp")
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistLogEntity that = (HistLogEntity) o;
        return Objects.equals(tableName, that.tableName) &&
                Objects.equals(columnName, that.columnName) &&
                Objects.equals(operation, that.operation) &&
                Objects.equals(muid, that.muid) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(oldValueChar, that.oldValueChar) &&
                Objects.equals(newValueChar, that.newValueChar) &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, columnName, operation, muid, brand, clientId, oldValueChar, newValueChar, timeStamp);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HistLogEntity.class.getSimpleName() + "[", "]")
                .add("tableName='" + tableName + "'")
                .add("columnName='" + columnName + "'")
                .add("operation='" + operation + "'")
                .add("muid='" + muid + "'")
                .add("brand='" + brand + "'")
                .add("clientId='" + clientId + "'")
                .add("oldValueChar='" + oldValueChar + "'")
                .add("newValueChar='" + newValueChar + "'")
                .add("timeStamp=" + timeStamp)
                .toString();
    }
}
