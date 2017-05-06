package ru.hh.resumebuilderbot.http.response.entity;

public class Company {
    private String id;
    private String name;
    private String logoUrl;
    private String url;

    public Company(String id, String url, String name, String logoUrl) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.logoUrl = logoUrl;
    }

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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format("Company{id='%s', name='%s', logoUrl='%s', url='%s'}", id, name, logoUrl, url);
    }
}
