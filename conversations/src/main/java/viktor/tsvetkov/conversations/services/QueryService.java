package viktor.tsvetkov.conversations.services;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class QueryService {

    @PersistenceContext
    private EntityManager entityManager;

    private void setQueryParameters(@Nonnull Query query, @Nonnull Map<String, Object> params) {
        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }
    }

    public <T> List<T> executeSql(String sql, Class<T> className, @Nullable Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql, className);
        if (params != null && !params.isEmpty()) {
            setQueryParameters(query, params);
        }
        return (List<T>) query.getResultList();
    }

    public List<Object> executeSql(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setQueryParameters(query, params);
        return query.getResultList();
    }
}
