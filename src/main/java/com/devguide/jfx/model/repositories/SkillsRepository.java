package com.devguide.jfx.model.repositories;

import com.devguide.jfx.model.beans.Skills;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends CrudRepository<Skills, Integer> {
}
