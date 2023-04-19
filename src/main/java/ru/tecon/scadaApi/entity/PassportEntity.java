package ru.tecon.scadaApi.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Maksim Shchelkonogov
 * 18.04.2023
 */
@Entity
@Table(name = "passport", schema = "scada", catalog = "postgres")
public class PassportEntity {

    @Id
    @Column(name = "muid", nullable = false)
    private long muid;

    @Basic
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Basic
    @Column(name = "first_name", nullable = false, length = 2)
    @Size(max = 2)
    private String firstName;

    @Basic
    @Column(name = "second_name", nullable = false, length = 3)
    @Size(max = 3)
    private String secondName;

    @Basic
    @Column(name = "status", nullable = false)
    @Min(0)
    @Max(255)
    private short status;

    public long getMuid() {
        return muid;
    }

    public void setMuid(long muid) {
        this.muid = muid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassportEntity that = (PassportEntity) o;
        return muid == that.muid && status == that.status && Objects.equals(address, that.address) && Objects.equals(firstName, that.firstName) && Objects.equals(secondName, that.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(muid, address, firstName, secondName, status);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PassportEntity.class.getSimpleName() + "[", "]")
                .add("muid=" + muid)
                .add("address='" + address + "'")
                .add("firstName='" + firstName + "'")
                .add("secondName='" + secondName + "'")
                .add("status=" + status)
                .toString();
    }
}
