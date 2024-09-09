package com.kane.library_management_system.library_management_system.appconfig;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kane.library_management_system.library_management_system.dao.BookDao;
import com.kane.library_management_system.library_management_system.dao.IssuedBookDao;
import com.kane.library_management_system.library_management_system.dao.UserDao;
import com.kane.library_management_system.library_management_system.entity.Book;
import com.kane.library_management_system.library_management_system.entity.IssuedBook;
import com.kane.library_management_system.library_management_system.entity.User;

@Component
public class DataInitialization implements CommandLineRunner {

    private JdbcTemplate jdbcTemplate;
    private BookDao bookDao;
    private UserDao userDao;
    private IssuedBookDao issuedBookDao;

    public DataInitialization(JdbcTemplate jdbcTemplate, BookDao bookDao, UserDao userDao,
            IssuedBookDao issuedBookDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.issuedBookDao = issuedBookDao;
    }

    @Override
    public void run(String... args) throws Exception {
        // Insert data into book_table if it is empty
        String bookExistsQuery = "SELECT EXISTS (SELECT 1 FROM book_table LIMIT 1)";
        Boolean booksExist = jdbcTemplate.queryForObject(bookExistsQuery, Boolean.class);
        
        if (!booksExist) {
            List<Book> books=List.of(
                new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 1925, false),
                new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 1960, true),
                new Book("1984", "George Orwell", "Dystopian", 1949, true),
                new Book("Moby Dick", "Herman Melville", "Adventure", 1851, true),
                new Book("The Catcher in the Rye", "J.D. Salinger", "Fiction", 1951, true)
            );
            for(Book book:books){
                bookDao.save(book);
            }
        }

        // Insert data into user_table if it is empty
        String userExistsQuery = "SELECT EXISTS (SELECT 1 FROM user_table LIMIT 1)";
        Boolean usersExist = jdbcTemplate.queryForObject(userExistsQuery, Boolean.class);
        
        if (!usersExist) {
            List<User> users = List.of(
                new User("akshay", "kumar", "akshay.kumar@example.com"),
                new User("salman", "khan", "salman.khan@example.com"),
                new User("tushar", "porje", "tushar.porje@example.com"),
                new User("aneesh", "kumar", "aneesh.kumar@example.com"),
                new User("shubham", "kumbhar", "shubham.kumbhar@example.com")
            );
            for(User user:users){
                userDao.save(user);
            }
        }

        // Insert data into issued_book_table if it is empty
        String issuedBookExistsQuery = "SELECT EXISTS (SELECT 1 FROM issued_book_table LIMIT 1)";
        Boolean issuedBooksExist = jdbcTemplate.queryForObject(issuedBookExistsQuery, Boolean.class);
        
        if (!issuedBooksExist) {
            List<User> users = userDao.findAll();
            List<Book> books = bookDao.findAll();

            List<IssuedBook> issuedBooks = List.of(
                new IssuedBook(books.get(0), users.get(0), LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 15)),
                new IssuedBook(books.get(1), users.get(1), LocalDate.of(2024, 9, 5), LocalDate.of(2024, 9, 20)),
                new IssuedBook(books.get(2), users.get(2), LocalDate.of(2024, 8, 25), LocalDate.of(2024, 9, 10)),
                new IssuedBook(books.get(0), users.get(3), LocalDate.of(2024, 9, 1), null),
                new IssuedBook(books.get(3), users.get(1), LocalDate.of(2024, 8, 30), LocalDate.of(2024, 9, 12))
            );
            for(IssuedBook issuedBook: issuedBooks ){
                issuedBookDao.save(issuedBook);
            }
        }
    }

}
