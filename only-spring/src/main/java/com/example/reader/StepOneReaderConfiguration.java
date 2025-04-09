package com.example.reader;

import com.example.domain.SampleOne;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class StepOneReaderConfiguration {

    private static final String SELECT_CLAUSE = "id, name";
    private static final String FROM_CLAUSE = "FROM SCHEMA.TABLE";
    private static final String WHERE_CLAUSE = "WHERE id < 100";
    private static final int CHUNK_SIZE = 10;

    private static final RowMapper<SampleOne> ROW_MAPPER =
            (rs, rowNum) -> new SampleOne(
                    rs.getLong("id"),
                    rs.getString("name"));

    @Bean
    public ItemReader<SampleOne> stepOneReader(DataSource dataSource) {
        return new JdbcPagingItemReaderBuilder<SampleOne>()
                .name("stepOneReader")
                .dataSource(dataSource)
                .queryProvider(getQueryProvider())
                .rowMapper(ROW_MAPPER)
                .pageSize(CHUNK_SIZE)
                .build();
    }

    private PagingQueryProvider getQueryProvider() {
        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause(SELECT_CLAUSE);
        queryProvider.setFromClause(FROM_CLAUSE);
        queryProvider.setWhereClause(WHERE_CLAUSE);

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        return queryProvider;
    }
}
