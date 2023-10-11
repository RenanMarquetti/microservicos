package com.codeum.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeum.dto.UserDTO;
import com.codeum.model.User;
import com.codeum.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public List<UserDTO> getAll() {
		
		List<User> usuarios = userRepository.findAll();
		return usuarios
				.stream()
				.map(UserDTO::convert)
				.collect(Collectors.toList());	
	}
	
	public UserDTO findById(Long userId) {
		User usuario = userRepository
						.findById(userId)
						.orElseThrow(() -> new RuntimeException("User not found"));
		
		return UserDTO.convert(usuario);
	}
	
	public UserDTO save(UserDTO userDTO) {
		userDTO.setDataCadastro(LocalDateTime.now());
		User user = userRepository.save(User.convert(userDTO));
		return UserDTO.convert(user);
	}
	
	public UserDTO delete(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		userRepository.delete(user);
		return UserDTO.convert(user);
	}
	
	public UserDTO findByCpf(String cpf) {
		User user = userRepository.findByCpf(cpf);
		return user != null ? UserDTO.convert(user) : null;
	}
	
	public List<UserDTO> findByName(String name) {
		List<User> usuarios = userRepository.queryByNomeLike(name);
		return usuarios
				.stream()
				.map(UserDTO::convert)
				.collect(Collectors.toList());
	}
	
	public UserDTO editUser(Long userId, UserDTO userDTO) {
		
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		if(userDTO.getEmail() != null && !user.getEmail().equals(userDTO.getEmail())) user.setEmail(userDTO.getEmail());
		if(userDTO.getTelefone() != null && !user.getTelefone().equals(userDTO.getTelefone())) user.setTelefone(userDTO.getTelefone());
		if(userDTO.getEndereco() != null && !user.getEndereco().equals(userDTO.getEndereco())) user.setEndereco(userDTO.getEndereco());
		
		user = userRepository.save(user);
		return UserDTO.convert(user);
	}
	
	public Page<UserDTO> getAllPage(Pageable page) {
		return null;
	}

}
