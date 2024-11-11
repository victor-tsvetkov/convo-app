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

    private void setPaginationParameters(@Nonnull Query query, int start, int pageSize) {
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
    }

    public <T> List<T> executeSql(String sql, Class<T> className,
                                  @Nullable Map<String, Object> params,
                                  int start, int pageSize) {
        Query query = createNativeQuery(sql, className);
        if (params != null && !params.isEmpty()) {
            setQueryParameters(query, params);
        }
        setPaginationParameters(query, start, pageSize);
        return (List<T>) query.getResultList();
    }

    private <T> Query createNativeQuery(@Nonnull String sql, @Nullable Class<T> className) {
        Query query;
        if (className != null) {
            query = entityManager.createNativeQuery(sql, className);
        } else {
            query = entityManager.createNativeQuery(sql);
        }
        return query;
    }

    public Long executeCountSql(String sql, @Nullable Map<String, Object> params) {
        Query query = createNativeQuery(sql, null);
        if (params != null && !params.isEmpty()) {
            setQueryParameters(query, params);
        }
        return (Long) query.getSingleResult();
    }

    public <T> List<T> executeSql(String sql, Class<T> className, @Nullable Map<String, Object> params) {
        Query query = createNativeQuery(sql, className);
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
