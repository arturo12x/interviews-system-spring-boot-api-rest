package com.interviews.system.service;

import com.interviews.system.dto.UserDTO;
import com.interviews.system.dto.UserResponse;
import com.interviews.system.entity.User;
import com.interviews.system.exceptions.ResourceNotFoundException;
import com.interviews.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapEntity(userDTO);

        User newUser = newUser = userRepository.save(user);

        UserDTO userReponse = mapDTO(newUser);

        return userReponse;
    }

    @Override
    public UserResponse getAllUsers(int pageNo, int pageSize, String sortBy,String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);

        Page<User> users = userRepository.findAll(pageable);

        List<User> listOfUsers = users.getContent();
        List<UserDTO> content = listOfUsers.stream().map(user -> mapDTO(user)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setIs_last(users.isLast());

        return userResponse;
    }

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return mapDTO(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setEmail(userDTO.getEmail());
        user.setFirst_name(userDTO.getFirst_name());
        user.setLast_name(userDTO.getLast_name());

        User updatedUser = userRepository.save(user);

        return mapDTO(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userRepository.delete(user);
    }

    //Convertir entidad a DTO
    private UserDTO mapDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirst_name(user.getFirst_name());
        userDTO.setLast_name(user.getLast_name());

        return userDTO;
    }

    //Convertir DTO a entidad
    private User mapEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setFirst_name(userDTO.getFirst_name());
        user.setLast_name(userDTO.getLast_name());

        return user;
    }
}
