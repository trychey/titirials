package com.baeldung.projection.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.baeldung.projection.model.Student;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    @Aggregation(pipeline = {"{ $skip: ?0 }, { $limit: ?1 }"})
    List<Student> findAll(Long skip, Long limit);
    @Aggregation(pipeline = {"{ '$match': { 'age': ?0 } }", "{ $skip: ?1 }", "{ $limit: ?2 }"})
    List<Student> findAllWithAgeCriteria(Long age, Long skip, Long limit);

    @Aggregation(pipeline = {
            "{ '$match': { 'id' : ?0 } }",
            "{ '$sort' : { 'id' : 1 } }",
            "{ '$skip' : ?1 }",
            "{ '$limit' : ?2 }"
    })
    List<Student> findByStudentId(final String id, Long skip, Long limit);
}
