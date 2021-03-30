package com.khmt.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Phongdungsach.
 */
@Entity
@Table(name = "phongdungsach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Phongdungsach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_phong")
    private String tenPhong;

    @Column(name = "vi_tri")
    private String viTri;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Phongdungsach id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenPhong() {
        return this.tenPhong;
    }

    public Phongdungsach tenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
        return this;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getViTri() {
        return this.viTri;
    }

    public Phongdungsach viTri(String viTri) {
        this.viTri = viTri;
        return this;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phongdungsach)) {
            return false;
        }
        return id != null && id.equals(((Phongdungsach) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Phongdungsach{" +
            "id=" + getId() +
            ", tenPhong='" + getTenPhong() + "'" +
            ", viTri='" + getViTri() + "'" +
            "}";
    }
}
