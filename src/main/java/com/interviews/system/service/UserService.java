package com.interviews.system.service;

import com.interviews.system.dto.UserDTO;
import com.interviews.system.dto.UserResponse;

public interface UserService {

    public UserDTO createUser(UserDTO userDTO);

    public UserResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortOrder);

    public UserDTO getUserById(long id);

    public UserDTO updateUser(UserDTO userDTO, long id);

    public void deleteUser(long id);
}
