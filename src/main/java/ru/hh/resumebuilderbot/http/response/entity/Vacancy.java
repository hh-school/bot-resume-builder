package ru.hh.resumebuilderbot.http.response.entity;

import java.util.Objects;

public class Vacancy {
    private String id;
    private String name;
    private String url;
    private String companyName;
    private String companyLogo;
    private Integer salaryFrom;
    private Integer salaryTo;
    private String salaryCurrency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public Integer getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(Integer salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public Integer getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(Integer salaryTo) {
        this.salaryTo = salaryTo;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    @Override
    public String toString() {
        return String.format("Vacancy{id='%s', name='%s', url='%s', companyName='%s', companyLogo='%s', " +
                        "salaryFrom=%d, salaryTo=%d, salaryCurrency='%s'}", id, name, url, companyName, companyLogo,
                salaryFrom, salaryTo, salaryCurrency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vacancy)) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(id, vacancy.id) &&
                Objects.equals(name, vacancy.name) &&
                Objects.equals(url, vacancy.url) &&
                Objects.equals(companyName, vacancy.companyName) &&
                Objects.equals(companyLogo, vacancy.companyLogo) &&
                Objects.equals(salaryFrom, vacancy.salaryFrom) &&
                Objects.equals(salaryTo, vacancy.salaryTo) &&
                Objects.equals(salaryCurrency, vacancy.salaryCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url, companyName, companyLogo, salaryFrom, salaryTo, salaryCurrency);
    }
}
