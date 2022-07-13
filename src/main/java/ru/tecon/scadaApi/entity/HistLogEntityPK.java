package ru.tecon.scadaApi.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Maksim Shchelkonogov
 */
public class HistLogEntityPK implements Serializable {
    private String tableName;
    private String columnName;
    private String operation;
    private String muid;
    private LocalDateTime timeStamp;

    @Column(name = "table_name")
    @Id
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Column(name = "column_name")
    @Id
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Column(name = "operation")
    @Id
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Column(name = "muid")
    @Id
    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    @Column(name = "time_stamp")
    @Id
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
        HistLogEntityPK that = (HistLogEntityPK) o;
        return Objects.equals(tableName, that.tableName) &&
                Objects.equals(columnName, that.columnName) &&
                Objects.equals(operation, that.operation) &&
                Objects.equals(muid, that.muid) &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, columnName, operation, muid, timeStamp);
    }
}
