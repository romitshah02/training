package com.sunbird.training.spec;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sunbird.training.entity.Course;
import com.sunbird.training.enums.Board;
import com.sunbird.training.enums.Grade;
import com.sunbird.training.enums.Medium;
import com.sunbird.training.enums.Subject;

import jakarta.persistence.criteria.Predicate;

public class CourseSpecifications {
    //? Course Filter Specification file will check what filters where presesnt in the request
    public static Specification<Course> hasFilter(String name,Board board, Medium medium, Grade grade, Subject subject){
       return (root,query,cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null){
                predicates.add(cb.equal(root.get("name"), name));
            }

            if (board != null) {
                predicates.add(cb.equal(root.get("board"), board));
            }
            if (medium != null) {
                predicates.add(cb.equal(root.get("medium"), medium));
            }
            if (grade != null) {
                predicates.add(cb.equal(root.get("grade"), grade));
            }
            if (subject != null) {
                predicates.add(cb.equal(root.get("subject"), subject));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
