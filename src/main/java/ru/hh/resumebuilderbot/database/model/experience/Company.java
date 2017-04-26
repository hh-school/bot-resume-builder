package ru.hh.resumebuilderbot.database.model.experience;

import ru.hh.resumebuilderbot.database.model.Area;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Objects;

@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    public Company() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        return Objects.equals(id, company.id) &&
                Objects.equals(name, company.name) &&
                Objects.equals(url, company.url) &&
                Objects.equals(area, company.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url);
    }

    @Override
    public String toString() {
        return String.format("Company{id=%d, name='%s', url = '%s', area = '%d'}", id, name, url, area.getId());
    }
}
