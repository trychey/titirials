package com.baeldung.cassandra.batch.repository;

import com.baeldung.cassandra.batch.domain.Product;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.DefaultBatchType;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.select.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductRepository {

    private static final String PRODUCT_TABLE_NAME = "product";
    private static final String PRODUCT_BY_ID_TABLE_NAME = "product_by_id";
    private static final String PRODUCT_BY_NAME_TABLE_NAME = "product_by_name";

    private final CqlSession session;

    public ProductRepository(CqlSession session) {
        this.session = session;
    }
	
    public void createProductTable(String keyspace) {
        CreateTable createTable = SchemaBuilder.createTable(PRODUCT_TABLE_NAME).ifNotExists()
                .withPartitionKey("product_id", DataTypes.UUID)
                .withClusteringColumn("variant_id", DataTypes.UUID)
                .withColumn("product_name", DataTypes.TEXT)
                .withColumn("description", DataTypes.TEXT)
                .withColumn("price", DataTypes.FLOAT);

        executeStatement(createTable.build(), keyspace);
	}

    public void createProductByIdTable(String keyspace) {
        CreateTable createTable = SchemaBuilder.createTable(PRODUCT_BY_ID_TABLE_NAME).ifNotExists()
                .withPartitionKey("product_id", DataTypes.UUID)
                .withColumn("product_name", DataTypes.TEXT)
                .withColumn("title", DataTypes.TEXT)
                .withColumn("description", DataTypes.TEXT)
                .withColumn("price", DataTypes.FLOAT);

        executeStatement(createTable.build(), keyspace);
    }

    public void createProductTableByName(String keyspace) {
        CreateTable createTable = SchemaBuilder.createTable(PRODUCT_BY_NAME_TABLE_NAME).ifNotExists()
                .withPartitionKey("product_name", DataTypes.TEXT)
                .withColumn("product_id", DataTypes.UUID)
                .withColumn("description", DataTypes.TEXT)
                .withColumn("price", DataTypes.FLOAT);

        executeStatement(createTable.build(), keyspace);
	}
	
    /**
     * Insert two variant Product into same table using a batch query.
     *  
	 * @param Product
	 */
    public void insertProductVariantBatch(Product productVariant1,Product productVariant2) {
        String insertQuery1 = new StringBuilder("").append("INSERT INTO ").append(PRODUCT_TABLE_NAME)
                .append("(product_id,variant_id,product_name,description,price) ").append("VALUES (").append(":product_id")
                .append(", ").append(":variant_id").append(", ").append(":product_name").append(", ")
                .append(":description").append(", ").append(":price").append(");").toString();

        String insertQuery2 = new StringBuilder("").append("INSERT INTO ").append(PRODUCT_TABLE_NAME)
                .append("(product_id,variant_id,product_name,description,price) ").append("VALUES (").append(":product_id")
                .append(", ").append(":variant_id").append(", ").append(":product_name")
                .append(", ").append(":description").append(", ").append(":price").append(");").toString();

        PreparedStatement preparedInsert1 = session.prepare(insertQuery1);
        PreparedStatement preparedInsert2 = session.prepare(insertQuery2);
        
        UUID productId = UUID.randomUUID();
		
        UUID productVariantId1 = UUID.randomUUID();
        UUID productVariantId2 = UUID.randomUUID();

        BatchStatement batch = BatchStatement.newInstance(DefaultBatchType.UNLOGGED,
                preparedInsert1.bind(productId, productVariantId1,
                        productVariant1.getProductName(), 
				        productVariant1.getDescription(),
                        productVariant1.getPrice()),
                preparedInsert2.bind(productId, productVariantId2, 
                		productVariant1.getProductName(),
                        productVariant2.getDescription(),
                        productVariant2.getPrice()));

        session.execute(batch);
     }


     /**
     * Insert two same Product into related tables using a batch query.
	 * 
	 * @param book
	 */
     public void insertProductBatch(Product product) {
        String cqlQuery1 = new StringBuilder("").append("INSERT INTO ").append(PRODUCT_BY_ID_TABLE_NAME)
                .append("(product_id,product_name,description,price) ").append("VALUES (").append(":product_id")
                .append(", ").append(":product_name").append(", ").append(":descriptioj").append(", ")
                .append(":price").append(");").toString();

        String cqlQuery2 = new StringBuilder("").append("INSERT INTO ").append(PRODUCT_BY_NAME_TABLE_NAME)
                .append("(product_name,product_id,description,price) ").append("VALUES (").append(":product_name")
                .append(", ").append(":product_id").append(", ").append(":descriptioj").append(", ")
                .append(":price").append(");").toString();

        PreparedStatement preparedInsert1 = session.prepare(cqlQuery1);
        PreparedStatement preparedInsert2 = session.prepare(cqlQuery2);
		
        UUID productId = UUID.randomUUID();

        BatchStatement batch = BatchStatement.newInstance(DefaultBatchType.LOGGED,
                preparedInsert1.bind(productId, product.getProductName(), product.getDescription(),
                        product.getPrice()),
                preparedInsert2.bind(product.getProductName(), productId, product.getDescription(),
                        product.getPrice()));

        session.execute(batch);
    }

    public List<Product> selectAllProduct(String keyspace) {
        Select select = QueryBuilder.selectFrom(PRODUCT_TABLE_NAME).all();

        ResultSet resultSet = executeStatement(select.build(), keyspace);

        List<Product> result = new ArrayList<>();

        resultSet.forEach(x -> result.add(new Product(x.getUuid("product_id"), x.getUuid("variant_id"),
               x.getString("product_name"), x.getString("description"), x.getFloat("price"))));

        return result;
    }
	
    public List<Product> selectAllProductByName(String keyspace) {
        Select select = QueryBuilder.selectFrom(PRODUCT_BY_NAME_TABLE_NAME).all();

        ResultSet resultSet = executeStatement(select.build(), keyspace);

        List<Product> result = new ArrayList<>();

        resultSet.forEach(x -> result.add(new Product(x.getUuid("product_id"),
               x.getString("product_name"), x.getString("description"), x.getFloat("price"))));

        return result;
    }
	
    public List<Product> selectAllProductById(String keyspace) {
        Select select = QueryBuilder.selectFrom(PRODUCT_BY_ID_TABLE_NAME).all();

        ResultSet resultSet = executeStatement(select.build(), keyspace);

        List<Product> result = new ArrayList<>();

        resultSet.forEach(x -> result.add(new Product(x.getUuid("product_id"),
            x.getString("product_name"), x.getString("description"), x.getFloat("price"))));

        return result;
    }
	
	/**
     * Delete table.
     * 
     * @param tableName the name of the table to delete.
     */
    public void deleteTable(String tableName) {
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(tableName);

        final String query = sb.toString();
        session.execute(query);
    }

    private ResultSet executeStatement(SimpleStatement statement, String keyspace) {
        if (keyspace != null) {
            statement.setKeyspace(CqlIdentifier.fromCql(keyspace));
        }

        return session.execute(statement);
    }
}
