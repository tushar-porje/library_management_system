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
import com.kane.library_management_system.library_management_system.dao.IssuedBookDao;
import com.kane.library_management_system.library_management_system.dao.UserDao;
import com.kane.library_management_system.library_management_system.entity.IssuedBook;

@Repository
public class IssuedBookDaoImpl implements IssuedBookDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<IssuedBook> findAll() {
        String sql = "SELECT * FROM issued_book_table";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new IssuedBook(
                rs.getLong("id"),
                bookDao.findById(rs.getLong("book_id")).get(),
                userDao.findById(rs.getLong("user_id")).get(),
                rs.getDate("issue_date").toLocalDate(),
                rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null
        ));
    }

    @Override
    public Optional<IssuedBook> findById(Long id) {
        String sql = "SELECT * FROM issued_book_table WHERE id = ?";
        IssuedBook issuedBooks = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
            new IssuedBook(
                rs.getLong("id"),
                bookDao.findById(rs.getLong("book_id")).get(),
                userDao.findById(rs.getLong("user_id")).get(),
                rs.getDate("issue_date").toLocalDate(),
                rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null
            ), id);
        return Optional.ofNullable(issuedBooks);
    }

    @Override
    public Optional<IssuedBook> findByUserIdAndBookId(Long userId, Long bookId) {
        String sql = "SELECT * FROM issued_book_table WHERE user_id = ? AND book_id = ? AND return_date IS NULL";
        
        List<IssuedBook> issuedBooks = jdbcTemplate.query(sql, (rs, rowNum) -> 
            new IssuedBook(
                rs.getLong("id"),
                bookDao.findById(rs.getLong("book_id")).get(),
                userDao.findById(rs.getLong("user_id")).get(),
                rs.getDate("issue_date").toLocalDate(),
                rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null
            ), userId, bookId);
        
        return issuedBooks.stream().findFirst();
    }

    @Override
    public boolean isBookIssuedByUser(Long userId){
        String sql="SELECT EXISTS (SELECT * FROM issued_book_table WHERE user_id = ?)";
        Boolean isBookIssued = jdbcTemplate.queryForObject(sql, Boolean.class,userId);
        return isBookIssued;
    }

    @Override
    public long save(IssuedBook issuedBook) {
        String sql = "INSERT INTO issued_book_table (book_id, user_id, issue_date, return_date) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setLong(1, issuedBook.getBook().getId());
            ps.setLong(2, issuedBook.getUser().getId());
            ps.setDate(3, java.sql.Date.valueOf(issuedBook.getIssueDate()));
            ps.setDate(4, issuedBook.getReturnDate() != null ? java.sql.Date.valueOf(issuedBook.getReturnDate()) : null);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(long id, IssuedBook issuedBook) {
        String sql = "UPDATE issued_book_table SET book_id = ?, user_id = ?, issue_date = ?, return_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, issuedBook.getBook().getId(), issuedBook.getUser().getId(), issuedBook.getIssueDate(), issuedBook.getReturnDate(), id);
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM issued_book_table WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }    
}
