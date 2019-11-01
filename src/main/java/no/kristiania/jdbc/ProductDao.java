package no.kristiania.jdbc;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertProduct(String productName) {
        try { Connection conn = dataSource.getConnection();
            try (PreparedStatement statement = conn.prepareStatement(
                    "insert into products (name) values (?)")) {
                statement.setString(1, productName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from products")) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<String> result = new ArrayList<>();

                    while (rs.next()) {
                        result.add(rs.getString("name"));
                    }

                    return result;
                }
            }
        }
        }

    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/webshop");
        dataSource.setUser("webshop");
        dataSource.setPassword("cuteanimal19");
        ProductDao productDao = new ProductDao(dataSource);
        productDao.insertProduct("Test");
    }



    }

