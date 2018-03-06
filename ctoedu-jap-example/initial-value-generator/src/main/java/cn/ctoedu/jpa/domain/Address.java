package cn.ctoedu.jpa.domain;

import javax.persistence.*;

/**
 * Created on 2018/3/2.
 *
 * @author ctoedu
 * @since 1.0
 */
@Entity
public class Address {
    @Id
    @Column( name = "ID" )
    @TableGenerator(
            name = "AppSeqStore",
            table = "APP_SEQ_STORE",
            pkColumnName = "APP_SEQ_NAME",
            pkColumnValue = "LISTENER_PK",
            valueColumnName = "APP_SEQ_VALUE",
            initialValue = 10000,
            allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.TABLE, generator = "AppSeqStore" )
    private long id;

    private String street;

    private String city;

    private String state;

    private String zip;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String address) {
        this.street = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String toString() {
        return "Address id: " + getId() + ", street: " + getStreet() + ", city: " + getCity()
                + ", state: " + getState() + ", zip: " + getZip();
    }

}