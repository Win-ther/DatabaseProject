package org.example.entities;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "CourseEvaluation", schema = "languageCourseDatabase")
public class CourseEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluationId", nullable = false)
    private Integer id;

    @Column(name = "evaluationScore")
    private Integer evaluationScore;

    @Column(name = "evaluationText", length = 500)
    private String evaluationText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluationCourseId")
    private LanguageCourse evaluationCourse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(Integer evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public String getEvaluationText() {
        return evaluationText;
    }

    public void setEvaluationText(String evaluationText) {
        this.evaluationText = evaluationText;
    }

    public LanguageCourse getEvaluationCourse() {
        return evaluationCourse;
    }

    public void setEvaluationCourse(LanguageCourse evaluationCourse) {
        this.evaluationCourse = evaluationCourse;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CourseEvaluation that = (CourseEvaluation) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ID: "+ id+" Score: "+evaluationScore+"\n Text answer: \n"+evaluationText;
    }
}