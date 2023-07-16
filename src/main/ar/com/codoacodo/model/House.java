package main.ar.com.codoacodo.model;

import java.sql.Timestamp;

public class House {

    private Integer id;
    private String descr, address;
    private Double prize;
    private Float m2;
    private Integer hab, bath, amb;
    private Timestamp publishDate;
    private boolean active;

    public House() {}

    @Override
    public String toString() {
        return String.format(
                "[ID=%d, DIRECCIÓN=%s, DESCRIPCIÓN=%s, PRECIO=%.2f, M&sup2;=%.2f," +
                " HABITACIONES=%d, BAÑOS=%d, AMBIENTES=%d, PUBLICADO EL=%s, ACTIVO=%s]",
                id,
                address,
                descr,
                prize,
                m2,
                hab,
                bath,
                amb,
                publishDate,
                (active) ? "si" : "no"
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public Float getM2() {
        return m2;
    }

    public void setM2(Float m2) {
        this.m2 = m2;
    }

    public Integer getHab() {
        return hab;
    }

    public void setHab(Integer hab) {
        this.hab = hab;
    }

    public Integer getBath() {
        return bath;
    }

    public void setBath(Integer bath) {
        this.bath = bath;
    }

    public Integer getAmb() {
        return amb;
    }

    public void setAmb(Integer amb) {
        this.amb = amb;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
