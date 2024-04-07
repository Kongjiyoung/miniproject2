package com.many.miniproject1.post;

import com.many.miniproject1.main.MainRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.StringJoiner;

@RequiredArgsConstructor
@Repository
public class PostQueryRepository {
    private final EntityManager em;

    private static String buildSkillClause(List<String> keywords) {
        StringJoiner whereClause = new StringJoiner(" OR ");
        for (String keyword : keywords) {
            whereClause.add("s.skill = '" + keyword + "'");
        }
        return whereClause.toString();
    }

    public List<Object[]> findAllWithKeywords(MainRequest.SearchDTO requestDTO) {
        String queryStart = """
                SELECT p.id, p.title, p.career, COUNT(s.skill) AS skill_count
                FROM post_tb p
                INNER JOIN skill_tb s ON s.post_id = p.id
                WHERE
                """;
//        String whereTitleClause = null;
//        String whereCareerClause = null;
//        String whereSkillClause = null;
        StringJoiner whereClause = new StringJoiner("AND ");
        if (!requestDTO.getTitle().isEmpty()) {
            String whereTitleClause = "p.title LIKE '%" + requestDTO.getTitle() + "%'";
            whereClause.add(whereTitleClause);
        }
        if (!requestDTO.getCareer().isEmpty()) {
            String whereCareerClause = "p.career LIKE '%" + requestDTO.getCareer() + "%'";
            whereClause.add(whereCareerClause);
        }
        if (!requestDTO.getSkills().isEmpty()) {
            String whereSkillClause = "(" + buildSkillClause(requestDTO.getSkills()) + ")";
            whereClause.add(whereSkillClause);
        }
        String queryEnd = "GROUP BY p.id";
        String query = queryStart + whereClause + queryEnd;

        return em.createNativeQuery(query).getResultList();
    }
}
