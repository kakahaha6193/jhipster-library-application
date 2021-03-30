package com.khmt.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Theloai.
 */
@Entity
@Table(name = "theloai")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Theloai implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_the_loai")
    private String tenTheLoai;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Theloai id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenTheLoai() {
        return this.tenTheLoai;
    }

    public Theloai tenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
        return this;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Theloai)) {
            return false;
        }
        return id != null && id.equals(((Theloai) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Theloai{" +
            "id=" + getId() +
            ", tenTheLoai='" + getTenTheLoai() + "'" +
            "}";
    }
}
