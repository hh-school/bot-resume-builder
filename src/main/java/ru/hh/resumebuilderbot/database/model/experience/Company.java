package ru.hh.resumebuilderbot.database.model.experience;

import ru.hh.resumebuilderbot.database.model.Area;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    @Column(name = "hh_id")
    private Integer hhId;

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

    public Integer getHHId() {
        return hhId;
    }

    public void setHHId(Integer hhId) {
        this.hhId = hhId;
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
                Objects.equals(hhId, company.hhId) &&
                Objects.equals(area, company.area);
    }

    @Override
    public int hashCode() {
        Integer areaId = area == null ? null : area.getId();
        return Objects.hash(id, name, hhId, areaId);
    }

    @Override
    public String toString() {
        String areaInfo = area == null ? "no information" : area.toString();
        return String.format("Company{id=%d, name='%s', hhId='%s', area='%s' }", id, name, hhId, areaInfo);
    }
}
