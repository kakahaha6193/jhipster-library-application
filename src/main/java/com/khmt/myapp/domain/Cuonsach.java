package com.khmt.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cuonsach.
 */
@Entity
@Table(name = "cuonsach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cuonsach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_het_han")
    private Instant ngayHetHan;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @ManyToOne
    @JsonIgnoreProperties(value = { "theloai", "nhaxuatban", "giasach", "nhapsaches" }, allowSetters = true)
    private Sach sach;

    @ManyToOne
    @JsonIgnoreProperties(value = { "cuonsaches", "docgia", "docgia", "thuthu" }, allowSetters = true)
    private Muonsach muonsach;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cuonsach id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getNgayHetHan() {
        return this.ngayHetHan;
    }

    public Cuonsach ngayHetHan(Instant ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
        return this;
    }

    public void setNgayHetHan(Instant ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public Integer getTrangThai() {
        return this.trangThai;
    }

    public Cuonsach trangThai(Integer trangThai) {
        this.trangThai = trangThai;
        return this;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Sach getSach() {
        return this.sach;
    }

    public Cuonsach sach(Sach sach) {
        this.setSach(sach);
        return this;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }

    public Muonsach getMuonsach() {
        return this.muonsach;
    }

    public Cuonsach muonsach(Muonsach muonsach) {
        this.setMuonsach(muonsach);
        return this;
    }

    public void setMuonsach(Muonsach muonsach) {
        this.muonsach = muonsach;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cuonsach)) {
            return false;
        }
        return id != null && id.equals(((Cuonsach) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cuonsach{" +
            "id=" + getId() +
            ", ngayHetHan='" + getNgayHetHan() + "'" +
            ", trangThai=" + getTrangThai() +
            "}";
    }
}
