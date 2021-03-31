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
 * A Nhapsach.
 */
@Entity
@Table(name = "nhapsach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nhapsach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_gio_nhap")
    private Instant ngayGioNhap;

    @Column(name = "so_luong")
    private Integer soLuong;

    @ManyToOne
    @JsonIgnoreProperties(value = { "nhapsaches", "muonsaches", "thuephongs" }, allowSetters = true)
    private Thuthu thuthu;

    @ManyToMany(mappedBy = "nhapsaches")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "theloai", "nhaxuatban", "giasach", "nhapsaches" }, allowSetters = true)
    private Set<Sach> saches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nhapsach id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getNgayGioNhap() {
        return this.ngayGioNhap;
    }

    public Nhapsach ngayGioNhap(Instant ngayGioNhap) {
        this.ngayGioNhap = ngayGioNhap;
        return this;
    }

    public void setNgayGioNhap(Instant ngayGioNhap) {
        this.ngayGioNhap = ngayGioNhap;
    }

    public Integer getSoLuong() {
        return this.soLuong;
    }

    public Nhapsach soLuong(Integer soLuong) {
        this.soLuong = soLuong;
        return this;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Nhapsach thuthu(Thuthu thuthu) {
        this.setThuthu(thuthu);
        return this;
    }

    public void setThuthu(Thuthu thuthu) {
        this.thuthu = thuthu;
    }

    public Thuthu getThuthu() {
        return this.thuthu;
    }

    public Set<Sach> getSaches() {
        return this.saches;
    }

    public Nhapsach saches(Set<Sach> saches) {
        this.setSaches(saches);
        return this;
    }

    public Nhapsach addSach(Sach sach) {
        this.saches.add(sach);
        sach.getNhapsaches().add(this);
        return this;
    }

    public Nhapsach removeSach(Sach sach) {
        this.saches.remove(sach);
        sach.getNhapsaches().remove(this);
        return this;
    }

    public void setSaches(Set<Sach> saches) {
        if (this.saches != null) {
            this.saches.forEach(i -> i.removeNhapsach(this));
        }
        if (saches != null) {
            saches.forEach(i -> i.addNhapsach(this));
        }
        this.saches = saches;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nhapsach)) {
            return false;
        }
        return id != null && id.equals(((Nhapsach) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nhapsach{" +
            "id=" + getId() +
            ", ngayGioNhap='" + getNgayGioNhap() + "'" +
            ", soLuong=" + getSoLuong() +
            "}";
    }
}
