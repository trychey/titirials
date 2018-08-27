package com.baeldung.optaplanner.test;

import com.baeldung.optaplanner.CourseSchedule;
import com.baeldung.optaplanner.Lecture;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import java.util.Arrays;

public class OptaPlannerUnitTest {

    static SolverFactory<CourseSchedule> solverFactory;
    static CourseSchedule unsolvedCourseSchedule;
    static CourseSchedule solvedCourseSchedule;
    static Solver<CourseSchedule> solver;

    @BeforeAll
    public static void setUp() {

        unsolvedCourseSchedule = new CourseSchedule();

        for(int i = 0; i < 10; i++){
            unsolvedCourseSchedule.getLectureList().add(new Lecture());
        }

        unsolvedCourseSchedule.getPeriodList().addAll(Arrays.asList(new Integer[] { 1, 2, 3 }));
        unsolvedCourseSchedule.getRoomList().addAll(Arrays.asList(new Integer[] { 1, 2 }));
    }

    @Test
    public void test_whenCustomJavaSolver() {

        SolverFactory<CourseSchedule> solverFactory = SolverFactory.createFromXmlResource("courseScheduleSolverConfiguration.xml");
        solver = solverFactory.buildSolver();
        CourseSchedule solvedCourseSchedule = solver.solve(unsolvedCourseSchedule);

        Assert.assertNotNull(solvedCourseSchedule.getScore());
        Assert.assertEquals(-4, solvedCourseSchedule.getScore().getHardScore());
    }

    @Test
    public void test_whenDroolsSolver() {

        SolverFactory<CourseSchedule> solverFactory = SolverFactory.createFromXmlResource("courseScheduleSolverConfigDrools.xml");
        solver = solverFactory.buildSolver();
        CourseSchedule solvedCourseSchedule = solver.solve(unsolvedCourseSchedule);

        Assert.assertNotNull(solvedCourseSchedule.getScore());
        Assert.assertEquals(0, solvedCourseSchedule.getScore().getHardScore());
    }
}
