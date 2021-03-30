package com.khmt.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Docgia.
 */
@Entity
@Table(name = "docgia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Docgia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "ngay_sinh")
    private Instant ngaySinh;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "cmt")
    private String cmt;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "tien_coc")
    private Long tienCoc;

    @OneToMany(mappedBy = "docgia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "docgia", "phongdocsach", "docgia", "thuthu" }, allowSetters = true)
    private Set<Thuephong> thuephongs = new HashSet<>();

    @OneToMany(mappedBy = "docgia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cuonsaches", "docgia", "docgia", "thuthu" }, allowSetters = true)
    private Set<Muonsach> muonsaches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Docgia id(Long id) {
        this.id = id;
        return this;
    }

    public String getHoTen() {
        return this.hoTen;
    }

    public Docgia hoTen(String hoTen) {
        this.hoTen = hoTen;
        return this;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Instant getNgaySinh() {
        return this.ngaySinh;
    }

    public Docgia ngaySinh(Instant ngaySinh) {
        this.ngaySinh = ngaySinh;
        return this;
    }

    public void setNgaySinh(Instant ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public Docgia diaChi(String diaChi) {
        this.diaChi = diaChi;
        return this;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getCmt() {
        return this.cmt;
    }

    public Docgia cmt(String cmt) {
        this.cmt = cmt;
        return this;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public Integer getTrangThai() {
        return this.trangThai;
    }

    public Docgia trangThai(Integer trangThai) {
        this.trangThai = trangThai;
        return this;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Long getTienCoc() {
        return this.tienCoc;
    }

    public Docgia tienCoc(Long tienCoc) {
        this.tienCoc = tienCoc;
        return this;
    }

    public void setTienCoc(Long tienCoc) {
        this.tienCoc = tienCoc;
    }

    public Set<Thuephong> getThuephongs() {
        return this.thuephongs;
    }

    public Docgia thuephongs(Set<Thuephong> thuephongs) {
        this.setThuephongs(thuephongs);
        return this;
    }

    public Docgia addThuephong(Thuephong thuephong) {
        this.thuephongs.add(thuephong);
        thuephong.setDocgia(this);
        return this;
    }

    public Docgia removeThuephong(Thuephong thuephong) {
        this.thuephongs.remove(thuephong);
        thuephong.setDocgia(null);
        return this;
    }

    public void setThuephongs(Set<Thuephong> thuephongs) {
        if (this.thuephongs != null) {
            this.thuephongs.forEach(i -> i.setDocgia(null));
        }
        if (thuephongs != null) {
            thuephongs.forEach(i -> i.setDocgia(this));
        }
        this.thuephongs = thuephongs;
    }

    public Set<Muonsach> getMuonsaches() {
        return this.muonsaches;
    }

    public Docgia muonsaches(Set<Muonsach> muonsaches) {
        this.setMuonsaches(muonsaches);
        return this;
    }

    public Docgia addMuonsach(Muonsach muonsach) {
        this.muonsaches.add(muonsach);
        muonsach.setDocgia(this);
        return this;
    }

    public Docgia removeMuonsach(Muonsach muonsach) {
        this.muonsaches.remove(muonsach);
        muonsach.setDocgia(null);
        return this;
    }

    public void setMuonsaches(Set<Muonsach> muonsaches) {
        if (this.muonsaches != null) {
            this.muonsaches.forEach(i -> i.setDocgia(null));
        }
        if (muonsaches != null) {
            muonsaches.forEach(i -> i.setDocgia(this));
        }
        this.muonsaches = muonsaches;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Docgia)) {
            return false;
        }
        return id != null && id.equals(((Docgia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Docgia{" +
            "id=" + getId() +
            ", hoTen='" + getHoTen() + "'" +
            ", ngaySinh='" + getNgaySinh() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            ", cmt='" + getCmt() + "'" +
            ", trangThai=" + getTrangThai() +
            ", tienCoc=" + getTienCoc() +
            "}";
    }
}
