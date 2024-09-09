package com.kane.library_management_system.library_management_system.service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kane.library_management_system.library_management_system.dao.IssuedBookDao;
import com.kane.library_management_system.library_management_system.dao.UserDao;
import com.kane.library_management_system.library_management_system.dto.UserDto;
import com.kane.library_management_system.library_management_system.entity.User;
import com.kane.library_management_system.library_management_system.exception.ResourceNotFoundException;
import com.kane.library_management_system.library_management_system.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    @Autowired
    private IssuedBookDao issuedBookDao;


    @Autowired
    ModelMapper mapper;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userDao.findAll();
        List<UserDto> userDtos=new ArrayList<>();
        for(User user:users){
            userDtos.add(getUserDtoMapped(user));
        }
        return userDtos;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user=userDao.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));
        return getUserDtoMapped(user);

    }

    //return number of row deleted
    @Override
    public int deleteById(Long userId) {
        return issuedBookDao.isBookIssuedByUser(userId) ? 0 : userDao.deleteById(userId);
    }

    @Override
    public long createUser(UserDto userDto) {
        User user=getUserMapped(userDto);
        return userDao.save(user);
    }

    @Override
    public int updateUser(Long id, UserDto userDto) {
        User user=getUserMapped(userDto);
        return userDao.update(id, user);
    }

    private UserDto getUserDtoMapped(User user){
        return UserDto.builder().
                            id(user.getId()).
                            firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .build();
    }

    private User getUserMapped(UserDto userDto){
        return User.builder().
                            firstName(userDto.getFirstName())
                            .lastName(userDto.getLastName())
                            .email(userDto.getEmail())
                            .build();
    }
    
}
