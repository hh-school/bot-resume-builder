package ru.hh.resumebuilderbot.database.model;

import org.hibernate.annotations.CreationTimestamp;
import ru.hh.resumebuilderbot.database.model.contact.Contact;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.database.model.gender.GenderConverter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    // Некоторые проверки на null убраны, чтобы можно было последовательно заполнять резюме, необходимо будет проверять
    // это в коде (telegram_id оставлен, чтобы нельзя было создать пользователя без привязки к telegram)
    @Column(name = "telegram_id", nullable = false, unique = true, updatable = false)
    private int telegramId;
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "first_name", length = 100)
    private String firstName;
    @Column(name = "last_name", length = 100)
    private String lastName;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Contact> contacts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Education> educations;
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Experience> experiences;
    @Convert(converter = GenderConverter.class)
    @Column(name = "gender", length = 1)
    private Gender gender;
    // Желаемая должность
    @Column(name = "career_objective")
    private String careerObjective;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user__specialization",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "specialization_id", nullable = false, updatable = false)}
    )
    private Set<Specialization> specializations;
    @Column(name = "salary_amount")
    private Integer salaryAmount;
    @Enumerated(EnumType.STRING)
    @Column(name = "salary_currency", length = 3)
    private SalaryCurrency salaryCurrency;

    @Column(name = "create_datetime")
    @CreationTimestamp
    private Date createDatetime;

    @Column(name = "node_id")
    private Integer nodeId;

    @Column(name = "node_relation_id")
    private Integer nodeRelationId;

    public User() {
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNodeRelationId() {
        return nodeRelationId;
    }

    public void setNodeRelationId(Integer nodeRelationId) {
        this.nodeRelationId = nodeRelationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(int telegramId) {
        this.telegramId = telegramId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(Set<Experience> experiences) {
        this.experiences = experiences;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCareerObjective() {
        return careerObjective;
    }

    public void setCareerObjective(String careerObjective) {
        this.careerObjective = careerObjective;
    }

    public Set<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(Set<Specialization> specializations) {
        this.specializations = specializations;
    }

    public Integer getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(Integer salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public SalaryCurrency getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(SalaryCurrency salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Set<Education> getEducations() {
        return educations;
    }

    public void setEducations(Set<Education> educations) {
        this.educations = educations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return telegramId == user.telegramId &&
                Objects.equals(id, user.id) &&
                Objects.equals(birthDate, user.birthDate) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(area.getId(), user.area.getId()) &&
                gender == user.gender &&
                Objects.equals(careerObjective, user.careerObjective) &&
                Objects.equals(salaryAmount, user.salaryAmount) &&
                salaryCurrency == user.salaryCurrency &&
                Objects.equals(createDatetime, user.createDatetime) &&
                Objects.equals(nodeId, user.nodeId) &&
                Objects.equals(nodeRelationId, user.nodeRelationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telegramId, birthDate, firstName, lastName, area.getId(), gender, careerObjective,
                salaryAmount, salaryCurrency, createDatetime, nodeId, nodeRelationId);
    }
}
