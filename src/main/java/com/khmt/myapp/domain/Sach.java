package com.khmt.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sach.
 */
@Entity
@Table(name = "sach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_sach")
    private String tenSach;

    @Column(name = "gia_niem_yet")
    private Integer giaNiemYet;

    @Column(name = "tacgia")
    private String tacgia;

    @Column(name = "gia_thue")
    private Long giaThue;

    @Column(name = "ngan_xep")
    private String nganXep;

    @ManyToOne
    private Theloai theloai;

    @ManyToOne
    private Nhaxuatban nhaxuatban;

    @ManyToOne
    @JsonIgnoreProperties(value = { "phongdungsach" }, allowSetters = true)
    private Giasach giasach;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_sach__nhapsach",
        joinColumns = @JoinColumn(name = "sach_id"),
        inverseJoinColumns = @JoinColumn(name = "nhapsach_id")
    )
    @JsonIgnoreProperties(value = { "thuthu", "thuthu", "saches" }, allowSetters = true)
    private Set<Nhapsach> nhapsaches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sach id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenSach() {
        return this.tenSach;
    }

    public Sach tenSach(String tenSach) {
        this.tenSach = tenSach;
        return this;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public Integer getGiaNiemYet() {
        return this.giaNiemYet;
    }

    public Sach giaNiemYet(Integer giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
        return this;
    }

    public void setGiaNiemYet(Integer giaNiemYet) {
        this.giaNiemYet = giaNiemYet;
    }

    public String getTacgia() {
        return this.tacgia;
    }

    public Sach tacgia(String tacgia) {
        this.tacgia = tacgia;
        return this;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public Long getGiaThue() {
        return this.giaThue;
    }

    public Sach giaThue(Long giaThue) {
        this.giaThue = giaThue;
        return this;
    }

    public void setGiaThue(Long giaThue) {
        this.giaThue = giaThue;
    }

    public String getNganXep() {
        return this.nganXep;
    }

    public Sach nganXep(String nganXep) {
        this.nganXep = nganXep;
        return this;
    }

    public void setNganXep(String nganXep) {
        this.nganXep = nganXep;
    }

    public Theloai getTheloai() {
        return this.theloai;
    }

    public Sach theloai(Theloai theloai) {
        this.setTheloai(theloai);
        return this;
    }

    public void setTheloai(Theloai theloai) {
        this.theloai = theloai;
    }

    public Nhaxuatban getNhaxuatban() {
        return this.nhaxuatban;
    }

    public Sach nhaxuatban(Nhaxuatban nhaxuatban) {
        this.setNhaxuatban(nhaxuatban);
        return this;
    }

    public void setNhaxuatban(Nhaxuatban nhaxuatban) {
        this.nhaxuatban = nhaxuatban;
    }

    public Giasach getGiasach() {
        return this.giasach;
    }

    public Sach giasach(Giasach giasach) {
        this.setGiasach(giasach);
        return this;
    }

    public void setGiasach(Giasach giasach) {
        this.giasach = giasach;
    }

    public Set<Nhapsach> getNhapsaches() {
        return this.nhapsaches;
    }

    public Sach nhapsaches(Set<Nhapsach> nhapsaches) {
        this.setNhapsaches(nhapsaches);
        return this;
    }

    public Sach addNhapsach(Nhapsach nhapsach) {
        this.nhapsaches.add(nhapsach);
        nhapsach.getSaches().add(this);
        return this;
    }

    public Sach removeNhapsach(Nhapsach nhapsach) {
        this.nhapsaches.remove(nhapsach);
        nhapsach.getSaches().remove(this);
        return this;
    }

    public void setNhapsaches(Set<Nhapsach> nhapsaches) {
        this.nhapsaches = nhapsaches;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sach)) {
            return false;
        }
        return id != null && id.equals(((Sach) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sach{" +
            "id=" + getId() +
            ", tenSach='" + getTenSach() + "'" +
            ", giaNiemYet=" + getGiaNiemYet() +
            ", tacgia='" + getTacgia() + "'" +
            ", giaThue=" + getGiaThue() +
            ", nganXep='" + getNganXep() + "'" +
            "}";
    }
}
