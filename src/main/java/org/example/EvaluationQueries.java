package org.example;

import jakarta.persistence.NoResultException;
import org.example.entities.CourseEvaluation;
import org.example.entities.LanguageCourse;
import org.example.entities.School;

import java.util.Scanner;

import static org.example.LanguageCourseQueries.getCourseByName;
import static org.example.Main.getChoice;
import static org.example.Main.inTransaction;

public class EvaluationQueries {
    static Scanner scanner = new Scanner(System.in);
    public static void viewEvaluations() {
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();

        inTransaction(entityManager -> {
            LanguageCourse course = getCourseByName(entityManager, name);
            if (course == null) {
                System.out.println("No course by that name, returning to menu");
                return;
            }
            String queryString = """
                select ce from CourseEvaluation ce where ce.evaluationCourse.courseName like :name
                """;
            var query = entityManager.createQuery(queryString, CourseEvaluation.class);
            query.setParameter("name", name);
            var evaluations = query.getResultList();
            System.out.println("Evaluations for "+course.getCourseName() + "\n");
            evaluations.forEach(System.out::println);
        });
    }
    public static void insertEvaluation() {
        System.out.println("Enter ID for the course that you want to evaluate:");
        int courseId = getId();
        if (courseId == -1)
            return;
        System.out.println("Enter a score (0-10)");
        int score = 0;
        try {
            score = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input, returning to menu");
            return;
        }
        System.out.println("Enter text answer(max 500 characters)");
        String textAnswer = scanner.nextLine();
        int finalScore = score;
        inTransaction(entityManager -> {
            LanguageCourse course = entityManager.find(LanguageCourse.class,courseId);
            if (course == null) {
                System.out.println("No course by that Id, returning to menu");
                return;
            }
            CourseEvaluation evaluation = new CourseEvaluation();
            evaluation.setEvaluationScore(finalScore);
            evaluation.setEvaluationText(textAnswer);
            evaluation.setEvaluationCourse(course);
            entityManager.persist(evaluation);
        });
    }
    private static int getId() {
        int id = getChoice();
        if (id == -1) {
            System.out.println("Incorrect ID for course, returning to menu");
        }
        return id;
    }
}
