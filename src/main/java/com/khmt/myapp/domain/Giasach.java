package com.khmt.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Giasach.
 */
@Entity
@Table(name = "giasach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Giasach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thu_tu")
    private Integer thuTu;

    @ManyToOne
    private Phongdungsach phongdungsach;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Giasach id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getThuTu() {
        return this.thuTu;
    }

    public Giasach thuTu(Integer thuTu) {
        this.thuTu = thuTu;
        return this;
    }

    public void setThuTu(Integer thuTu) {
        this.thuTu = thuTu;
    }

    public Phongdungsach getPhongdungsach() {
        return this.phongdungsach;
    }

    public Giasach phongdungsach(Phongdungsach phongdungsach) {
        this.setPhongdungsach(phongdungsach);
        return this;
    }

    public void setPhongdungsach(Phongdungsach phongdungsach) {
        this.phongdungsach = phongdungsach;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Giasach)) {
            return false;
        }
        return id != null && id.equals(((Giasach) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Giasach{" +
            "id=" + getId() +
            ", thuTu=" + getThuTu() +
            "}";
    }
}
