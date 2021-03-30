package com.khmt.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Thuephong.
 */
@Entity
@Table(name = "thuephong")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Thuephong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_thue")
    private Instant ngayThue;

    @Column(name = "ca")
    private Integer ca;

    @ManyToOne
    @JsonIgnoreProperties(value = { "thuephongs", "muonsaches" }, allowSetters = true)
    private Docgia docgia;

    @ManyToOne
    private Phongdocsach phongdocsach;

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

    public Thuephong id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getNgayThue() {
        return this.ngayThue;
    }

    public Thuephong ngayThue(Instant ngayThue) {
        this.ngayThue = ngayThue;
        return this;
    }

    public void setNgayThue(Instant ngayThue) {
        this.ngayThue = ngayThue;
    }

    public Integer getCa() {
        return this.ca;
    }

    public Thuephong ca(Integer ca) {
        this.ca = ca;
        return this;
    }

    public void setCa(Integer ca) {
        this.ca = ca;
    }

    public Docgia getDocgia() {
        return this.docgia;
    }

    public Thuephong docgia(Docgia docgia) {
        this.setDocgia(docgia);
        return this;
    }

    public void setDocgia(Docgia docgia) {
        this.docgia = docgia;
    }

    public Phongdocsach getPhongdocsach() {
        return this.phongdocsach;
    }

    public Thuephong phongdocsach(Phongdocsach phongdocsach) {
        this.setPhongdocsach(phongdocsach);
        return this;
    }

    public void setPhongdocsach(Phongdocsach phongdocsach) {
        this.phongdocsach = phongdocsach;
    }

    public Docgia getDocgia() {
        return this.docgia;
    }

    public Thuephong docgia(Docgia docgia) {
        this.setDocgia(docgia);
        return this;
    }

    public void setDocgia(Docgia docgia) {
        this.docgia = docgia;
    }

    public Thuthu getThuthu() {
        return this.thuthu;
    }

    public Thuephong thuthu(Thuthu thuthu) {
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
        if (!(o instanceof Thuephong)) {
            return false;
        }
        return id != null && id.equals(((Thuephong) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thuephong{" +
            "id=" + getId() +
            ", ngayThue='" + getNgayThue() + "'" +
            ", ca=" + getCa() +
            "}";
    }
}
