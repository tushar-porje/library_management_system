package com.kane.library_management_system.library_management_system.dao.daoimpl;

import java.sql.PreparedStatement;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kane.library_management_system.library_management_system.dao.BookDao;
import com.kane.library_management_system.library_management_system.entity.Book;

@Repository
public class BookDaoImpl implements BookDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book_table";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("genre"),
                rs.getInt("year_published"),
                rs.getBoolean("available")
        ));
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book_table WHERE id = ?";
        List<Book> books = jdbcTemplate.query(sql, (rs, rowNum) -> 
            new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("genre"),
                rs.getInt("year_published"),
                rs.getBoolean("available")
            ),id);
        return books.stream().findFirst();
    }

    @Override
    public List<Book> findByTitle(String title) {
        String sql = "SELECT * FROM book_table WHERE title LIKE ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("genre"),
                rs.getInt("year_published"),
                rs.getBoolean("available")
            ),title);
    }

    @Override
    public long save(Book book) {
        String sql = "INSERT INTO book_table (title, author, genre, year_published, available) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setInt(4, book.getYearPublished());
            ps.setBoolean(5, book.isAvailable());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Long id, Book book) {
        String sql = "UPDATE book_table SET title = ?, author = ?, genre = ?, year_published = ?, available = ? WHERE id = ?";
        return jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), 
                            book.getGenre(), book.getYearPublished(), book.isAvailable(), id);

    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM book_table WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isBookAvailable(Long bookId) {
        String sql = "SELECT available FROM book_table WHERE id = ?";
        Boolean isAvailable = jdbcTemplate.queryForObject(sql, Boolean.class, bookId);
        return isAvailable != null && isAvailable;
    }

    @Override
    public List<Book> findByAvailability(boolean available) {
        String sql = "SELECT * FROM book_table WHERE available = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("genre"),
                rs.getInt("year_published"),
                rs.getBoolean("available")
            ),available);
    }
    
}
