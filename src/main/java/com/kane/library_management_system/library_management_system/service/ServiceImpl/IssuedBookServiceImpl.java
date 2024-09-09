package com.kane.library_management_system.library_management_system.service.ServiceImpl;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kane.library_management_system.library_management_system.dao.IssuedBookDao;
import com.kane.library_management_system.library_management_system.dto.BookDto;
import com.kane.library_management_system.library_management_system.dto.IssuedBookDto;
import com.kane.library_management_system.library_management_system.dto.UserDto;
import com.kane.library_management_system.library_management_system.entity.Book;
import com.kane.library_management_system.library_management_system.entity.IssuedBook;
import com.kane.library_management_system.library_management_system.entity.User;
import com.kane.library_management_system.library_management_system.exception.BookUnavailableException;
import com.kane.library_management_system.library_management_system.exception.ResourceNotFoundException;
import com.kane.library_management_system.library_management_system.service.BookService;
import com.kane.library_management_system.library_management_system.service.IssuedBookService;
import com.kane.library_management_system.library_management_system.service.UserService;

@Service
public class IssuedBookServiceImpl implements IssuedBookService{

    @Autowired
    IssuedBookDao issuedBookDao;
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @Override
    public Long issueBook(long userId, long bookId) {
        UserDto userDto = userService.getUserById(userId);
        User user=new ModelMapper().map(userDto, User.class);
        BookDto bookDto = bookService.getBookById(bookId);
        Book book= new ModelMapper().map(bookDto, Book.class);

        if(bookService.checkBookAvailability(bookId)){
            IssuedBook issuedBook = IssuedBook.builder().book(book).user(user).issueDate(LocalDate.now()).returnDate(null).build();
            bookDto.setAvailable(false);
            bookService.updateBook(bookId, bookDto);
            return issuedBookDao.save(issuedBook);
        }else{
            throw new BookUnavailableException(bookId);
        }
    }

    @Override
    public String returnBook(long userId, long bookId) {
        UserDto userDto = userService.getUserById(userId);
        BookDto bookDto = bookService.getBookById(bookId);
        IssuedBookDto issuedBookDto = getIssuedBookByUserIdAndBookId(userId, bookId);

        if(issuedBookDto.getReturnDate()==null){
            issuedBookDto.setReturnDate(LocalDate.now());
            IssuedBook updatedIssuedBook=new ModelMapper().map(issuedBookDto, IssuedBook.class);
            bookDto.setAvailable(true);
            bookService.updateBook(bookId, bookDto);
            issuedBookDao.update(issuedBookDto.getId(), updatedIssuedBook);
            return String.format("%s %s has return '%s' book", userDto.getFirstName(),userDto.getLastName(),bookDto.getTitle());
        }else{
            return String.format("%s %s has already return the %s", userDto.getFirstName(),userDto.getLastName(),bookDto.getTitle());
        }
    }

    @Override
    public IssuedBookDto getIssuedBookByUserIdAndBookId(Long userId, Long bookId) {
        IssuedBook issuedBook = issuedBookDao.findByUserIdAndBookId(userId, bookId).orElseThrow(()->new ResourceNotFoundException("IssuedBook",userId,bookId));
        return new ModelMapper().map(issuedBook, IssuedBookDto.class);
    }

    @Override
    public IssuedBookDto getIssuedBookById(long issuedBookId){
        IssuedBook issuedBook = issuedBookDao.findById(issuedBookId).orElseThrow(()->new ResourceNotFoundException("IssuedBook","id",issuedBookId));
        return new ModelMapper().map(issuedBook, IssuedBookDto.class);
    }
    
}
