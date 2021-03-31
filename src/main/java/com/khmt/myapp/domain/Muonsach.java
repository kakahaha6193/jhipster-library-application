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
 * A Muonsach.
 */
@Entity
@Table(name = "muonsach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Muonsach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_muon")
    private Instant ngayMuon;

    @Column(name = "han_tra")
    private Instant hanTra;

    @Column(name = "ngay_tra")
    private Instant ngayTra;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "muonsach")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sach", "muonsach" }, allowSetters = true)
    private Set<Cuonsach> cuonsaches = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "thuephongs", "muonsaches" }, allowSetters = true)
    private Docgia docgia;

    @ManyToOne
    @JsonIgnoreProperties(value = { "nhapsaches", "muonsaches", "thuephongs" }, allowSetters = true)
    private Thuthu thuthu;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Muonsach id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getNgayMuon() {
        return this.ngayMuon;
    }

    public Muonsach ngayMuon(Instant ngayMuon) {
        this.ngayMuon = ngayMuon;
        return this;
    }

    public void setNgayMuon(Instant ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Instant getHanTra() {
        return this.hanTra;
    }

    public Muonsach hanTra(Instant hanTra) {
        this.hanTra = hanTra;
        return this;
    }

    public void setHanTra(Instant hanTra) {
        this.hanTra = hanTra;
    }

    public Instant getNgayTra() {
        return this.ngayTra;
    }

    public Muonsach ngayTra(Instant ngayTra) {
        this.ngayTra = ngayTra;
        return this;
    }

    public void setNgayTra(Instant ngayTra) {
        this.ngayTra = ngayTra;
    }

    public Integer getTrangThai() {
        return this.trangThai;
    }

    public Muonsach trangThai(Integer trangThai) {
        this.trangThai = trangThai;
        return this;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Set<Cuonsach> getCuonsaches() {
        return this.cuonsaches;
    }

    public Muonsach cuonsaches(Set<Cuonsach> cuonsaches) {
        this.setCuonsaches(cuonsaches);
        return this;
    }

    public Muonsach addCuonsach(Cuonsach cuonsach) {
        this.cuonsaches.add(cuonsach);
        cuonsach.setMuonsach(this);
        return this;
    }

    public Muonsach removeCuonsach(Cuonsach cuonsach) {
        this.cuonsaches.remove(cuonsach);
        cuonsach.setMuonsach(null);
        return this;
    }

    public void setCuonsaches(Set<Cuonsach> cuonsaches) {
        if (this.cuonsaches != null) {
            this.cuonsaches.forEach(i -> i.setMuonsach(null));
        }
        if (cuonsaches != null) {
            cuonsaches.forEach(i -> i.setMuonsach(this));
        }
        this.cuonsaches = cuonsaches;
    }

    public Muonsach docgia(Docgia docgia) {
        this.setDocgia(docgia);
        return this;
    }

    public Docgia getDocgia() {
        return this.docgia;
    }

    public void setDocgia(Docgia docgia) {
        this.docgia = docgia;
    }

    public Thuthu getThuthu() {
        return this.thuthu;
    }

    public Muonsach thuthu(Thuthu thuthu) {
        this.setThuthu(thuthu);
        return this;
    }

    public void setThuthu(Thuthu thuthu) {
        this.thuthu = thuthu;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Muonsach)) {
            return false;
        }
        return id != null && id.equals(((Muonsach) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Muonsach{" +
            "id=" + getId() +
            ", ngayMuon='" + getNgayMuon() + "'" +
            ", hanTra='" + getHanTra() + "'" +
            ", ngayTra='" + getNgayTra() + "'" +
            ", trangThai=" + getTrangThai() +
            "}";
    }
}
