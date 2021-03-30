package com.khmt.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Nhaxuatban.
 */
@Entity
@Table(name = "nhaxuatban")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nhaxuatban implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_nxb")
    private String tenNXB;

    @Column(name = "dia_chi")
    private String diaChi;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nhaxuatban id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenNXB() {
        return this.tenNXB;
    }

    public Nhaxuatban tenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
        return this;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public Nhaxuatban diaChi(String diaChi) {
        this.diaChi = diaChi;
        return this;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nhaxuatban)) {
            return false;
        }
        return id != null && id.equals(((Nhaxuatban) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nhaxuatban{" +
            "id=" + getId() +
            ", tenNXB='" + getTenNXB() + "'" +
            ", diaChi='" + getDiaChi() + "'" +
            "}";
    }
}
