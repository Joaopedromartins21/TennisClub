package com.joaopedroaguiar.tennisclub.service;

import com.joaopedroaguiar.tennisclub.dto.CreateUserDTO;
import com.joaopedroaguiar.tennisclub.dto.UserDTO;
import com.joaopedroaguiar.tennisclub.model.User;
import com.joaopedroaguiar.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço UserService - Lógica de negócios para operações com usuários
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Cria um novo usuário
     * @param createUserDTO Dados do usuário a ser criado
     * @return DTO do usuário criado
     * @throws RuntimeException se o email já estiver em uso
     */
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        // Verifica se o email já existe
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new RuntimeException("Email já está em uso: " + createUserDTO.getEmail());
        }

        // Cria o usuário
        User user = new User();
        user.setName(createUserDTO.getName());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setRole(createUserDTO.getRole());
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser);
    }

    /**
     * Busca todos os usuários
     * @return Lista de DTOs dos usuários
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca usuário por ID
     * @param id ID do usuário
     * @return DTO do usuário encontrado
     * @throws RuntimeException se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return new UserDTO(user);
    }

    /**
     * Busca usuário por email
     * @param email Email do usuário
     * @return DTO do usuário encontrado
     * @throws RuntimeException se o usuário não for encontrado
     */
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));
        return new UserDTO(user);
    }

    /**
     * Busca usuários por nome (busca parcial)
     * @param name Nome ou parte do nome
     * @return Lista de DTOs dos usuários encontrados
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca usuários por role
     * @param role Role do usuário
     * @return Lista de DTOs dos usuários com a role especificada
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca usuários ativos
     * @return Lista de DTOs dos usuários ativos
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getActiveUsers() {
        return userRepository.findByIsActive(true)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza um usuário existente
     * @param id ID do usuário
     * @param userDTO Dados atualizados do usuário
     * @return DTO do usuário atualizado
     * @throws RuntimeException se o usuário não for encontrado ou email já estiver em uso
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        // Verifica se o email já está em uso por outro usuário
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new RuntimeException("Email já está em uso por outro usuário: " + userDTO.getEmail());
        }

        // Atualiza os dados
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        }
        if (userDTO.getIsActive() != null) {
            user.setIsActive(userDTO.getIsActive());
        }

        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser);
    }

    /**
     * Ativa ou desativa um usuário
     * @param id ID do usuário
     * @param isActive Status ativo
     * @return DTO do usuário atualizado
     * @throws RuntimeException se o usuário não for encontrado
     */
    public UserDTO toggleUserStatus(Long id, Boolean isActive) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        user.setIsActive(isActive);
        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser);
    }

    /**
     * Exclui um usuário
     * @param id ID do usuário
     * @throws RuntimeException se o usuário não for encontrado
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        userRepository.delete(user);
    }

    /**
     * Conta o número total de usuários ativos
     * @return Número de usuários ativos
     */
    @Transactional(readOnly = true)
    public long countActiveUsers() {
        return userRepository.countActiveUsers();
    }

    /**
     * Conta o número de usuários por role
     * @param role Role do usuário
     * @return Número de usuários com a role especificada
     */
    @Transactional(readOnly = true)
    public long countUsersByRole(User.Role role) {
        return userRepository.countByRole(role);
    }

    /**
     * Verifica se um email já está em uso
     * @param email Email a ser verificado
     * @return true se o email já está em uso, false caso contrário
     */
    @Transactional(readOnly = true)
    public boolean isEmailInUse(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Busca usuários com agendamentos
     * @return Lista de DTOs dos usuários que possuem agendamentos
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersWithBookings() {
        return userRepository.findUsersWithBookings()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }
}

