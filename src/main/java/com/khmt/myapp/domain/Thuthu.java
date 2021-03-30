package com.khmt.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Thuthu.
 */
@Entity
@Table(name = "thuthu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Thuthu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "thuthu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "thuthu", "thuthu", "saches" }, allowSetters = true)
    private Set<Nhapsach> nhapsaches = new HashSet<>();

    @OneToMany(mappedBy = "thuthu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cuonsaches", "docgia", "docgia", "thuthu" }, allowSetters = true)
    private Set<Muonsach> muonsaches = new HashSet<>();

    @OneToMany(mappedBy = "thuthu")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "docgia", "phongdocsach", "docgia", "thuthu" }, allowSetters = true)
    private Set<Thuephong> thuephongs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Thuthu id(Long id) {
        this.id = id;
        return this;
    }

    public String getHoTen() {
        return this.hoTen;
    }

    public Thuthu hoTen(String hoTen) {
        this.hoTen = hoTen;
        return this;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getUsername() {
        return this.username;
    }

    public Thuthu username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public Thuthu password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Nhapsach> getNhapsaches() {
        return this.nhapsaches;
    }

    public Thuthu nhapsaches(Set<Nhapsach> nhapsaches) {
        this.setNhapsaches(nhapsaches);
        return this;
    }

    public Thuthu addNhapsach(Nhapsach nhapsach) {
        this.nhapsaches.add(nhapsach);
        nhapsach.setThuthu(this);
        return this;
    }

    public Thuthu removeNhapsach(Nhapsach nhapsach) {
        this.nhapsaches.remove(nhapsach);
        nhapsach.setThuthu(null);
        return this;
    }

    public void setNhapsaches(Set<Nhapsach> nhapsaches) {
        if (this.nhapsaches != null) {
            this.nhapsaches.forEach(i -> i.setThuthu(null));
        }
        if (nhapsaches != null) {
            nhapsaches.forEach(i -> i.setThuthu(this));
        }
        this.nhapsaches = nhapsaches;
    }

    public Set<Muonsach> getMuonsaches() {
        return this.muonsaches;
    }

    public Thuthu muonsaches(Set<Muonsach> muonsaches) {
        this.setMuonsaches(muonsaches);
        return this;
    }

    public Thuthu addMuonsach(Muonsach muonsach) {
        this.muonsaches.add(muonsach);
        muonsach.setThuthu(this);
        return this;
    }

    public Thuthu removeMuonsach(Muonsach muonsach) {
        this.muonsaches.remove(muonsach);
        muonsach.setThuthu(null);
        return this;
    }

    public void setMuonsaches(Set<Muonsach> muonsaches) {
        if (this.muonsaches != null) {
            this.muonsaches.forEach(i -> i.setThuthu(null));
        }
        if (muonsaches != null) {
            muonsaches.forEach(i -> i.setThuthu(this));
        }
        this.muonsaches = muonsaches;
    }

    public Set<Thuephong> getThuephongs() {
        return this.thuephongs;
    }

    public Thuthu thuephongs(Set<Thuephong> thuephongs) {
        this.setThuephongs(thuephongs);
        return this;
    }

    public Thuthu addThuephong(Thuephong thuephong) {
        this.thuephongs.add(thuephong);
        thuephong.setThuthu(this);
        return this;
    }

    public Thuthu removeThuephong(Thuephong thuephong) {
        this.thuephongs.remove(thuephong);
        thuephong.setThuthu(null);
        return this;
    }

    public void setThuephongs(Set<Thuephong> thuephongs) {
        if (this.thuephongs != null) {
            this.thuephongs.forEach(i -> i.setThuthu(null));
        }
        if (thuephongs != null) {
            thuephongs.forEach(i -> i.setThuthu(this));
        }
        this.thuephongs = thuephongs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thuthu)) {
            return false;
        }
        return id != null && id.equals(((Thuthu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thuthu{" +
            "id=" + getId() +
            ", hoTen='" + getHoTen() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
