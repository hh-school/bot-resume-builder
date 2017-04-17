package ru.hh.resumebuilderbot.database.model.experience;

import ru.hh.resumebuilderbot.database.model.Area;
import ru.hh.resumebuilderbot.database.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "company_url")
    private String companyUrl;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "experience__industry",
            joinColumns = {@JoinColumn(name = "experience_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "industry_id", nullable = false, updatable = false)}
    )
    private Set<Industry> industries;
    @Column(name = "position", nullable = false)
    private String position;
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "description", length = 1000)
    private String description;

    public Experience() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Set<Industry> getIndustries() {
        return industries;
    }

    public void setIndustries(Set<Industry> industries) {
        this.industries = industries;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Experience that = (Experience) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user.getId(), that.user.getId()) &&
                Objects.equals(company.getId(), that.company.getId()) &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(companyUrl, that.companyUrl) &&
                Objects.equals(area.getId(), that.area.getId()) &&
                Objects.equals(position, that.position) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user.getId(), company.getId(), companyName, companyUrl, area.getId(), position, startDate,
                endDate, description);
    }

    @Override
    public String toString() {
        return String.format("Experience{id=%d, user=%s, company=%s, companyName='%s', companyUrl='%s', area=%s, " +
                        "position='%s', startDate=%s, endDate=%s, description='%s'}", id, user.getId(), company.getId(),
                companyName, companyUrl, area.getId(), position, startDate, endDate, description);
    }
}