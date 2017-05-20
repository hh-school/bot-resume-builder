package ru.hh.resumebuilderbot.database.model;

import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.resumebuilderbot.database.model.education.Education;
import ru.hh.resumebuilderbot.database.model.experience.Experience;
import ru.hh.resumebuilderbot.database.model.gender.Gender;
import ru.hh.resumebuilderbot.database.model.gender.GenderConverter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "`user`")
public class User {
    public static final int PHONE_LENGTH = 20;
    private static final Logger log = LoggerFactory.getLogger(User.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    // Некоторые проверки на null убраны, чтобы можно было последовательно заполнять резюме, необходимо будет проверять
    // это в коде (telegram_id оставлен, чтобы нельзя было создать пользователя без привязки к telegram)
    @Column(name = "telegram_id", nullable = false, unique = true, updatable = false)
    private long telegramId;
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "first_name", length = 100)
    private String firstName;
    @Column(name = "last_name", length = 100)
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone", length = PHONE_LENGTH)
    private String phone;
    //TODO заменить EAGER на LAZY
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Education> educations;
    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Experience> experiences;
    @Convert(converter = GenderConverter.class)
    @Column(name = "gender", length = 1)
    private Gender gender;
    // Желаемая должность
    @Column(name = "career_objective")
    private String careerObjective;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user__specialization",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "specialization_id", nullable = false, updatable = false)}
    )
    private Set<Specialization> specializations;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user__skill",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "skill_id", nullable = false, updatable = false)}
    )
    private Set<Skill> skills;
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

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
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
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(area, user.area) &&
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
        return Objects.hash(id, telegramId, birthDate, email, phone, firstName, lastName, area, gender,
                careerObjective, salaryAmount, salaryCurrency, createDatetime, nodeId, nodeRelationId);
    }

    @Override
    public String toString() {
        return String.format("firstName=%s, lastName='%s', birthDate='%s', phone='%s', area='%s', " +
                        "gender='%s, educations='%s', experience='%s', skills='%s', salary=%s %s",
                firstName, lastName, birthDate, phone, area, gender, educations,
                experiences, skills, salaryAmount, salaryCurrency);
    }
}
