package com.khmt.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Phongdocsach.
 */
@Entity
@Table(name = "phongdocsach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Phongdocsach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_phong")
    private String tenPhong;

    @Column(name = "vi_tri")
    private String viTri;

    @Column(name = "suc_chua")
    private Integer sucChua;

    @Column(name = "gia_thue")
    private Long giaThue;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Phongdocsach id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenPhong() {
        return this.tenPhong;
    }

    public Phongdocsach tenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
        return this;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getViTri() {
        return this.viTri;
    }

    public Phongdocsach viTri(String viTri) {
        this.viTri = viTri;
        return this;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public Integer getSucChua() {
        return this.sucChua;
    }

    public Phongdocsach sucChua(Integer sucChua) {
        this.sucChua = sucChua;
        return this;
    }

    public void setSucChua(Integer sucChua) {
        this.sucChua = sucChua;
    }

    public Long getGiaThue() {
        return this.giaThue;
    }

    public Phongdocsach giaThue(Long giaThue) {
        this.giaThue = giaThue;
        return this;
    }

    public void setGiaThue(Long giaThue) {
        this.giaThue = giaThue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phongdocsach)) {
            return false;
        }
        return id != null && id.equals(((Phongdocsach) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Phongdocsach{" +
            "id=" + getId() +
            ", tenPhong='" + getTenPhong() + "'" +
            ", viTri='" + getViTri() + "'" +
            ", sucChua=" + getSucChua() +
            ", giaThue=" + getGiaThue() +
            "}";
    }
}
