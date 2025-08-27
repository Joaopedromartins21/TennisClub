package com.joaopedroaguiar.tennisclub.controller;

import com.joaopedroaguiar.tennisclub.dto.CreateUserDTO;
import com.joaopedroaguiar.tennisclub.dto.UserDTO;
import com.joaopedroaguiar.tennisclub.model.User;
import com.joaopedroaguiar.tennisclub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador UserController - API REST para operações com usuários
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 * 
 * Este controlador fornece endpoints para gerenciamento de usuários do sistema,
 * incluindo operações CRUD e consultas específicas.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "API para gerenciamento de usuários - João Pedro Aguiar TennisClub")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Criar novo usuário", 
               description = "Cria um novo usuário no sistema TennisClub - João Pedro Aguiar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Email já está em uso")
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            UserDTO createdUser = userService.createUser(createUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(summary = "Listar todos os usuários", 
               description = "Retorna lista de todos os usuários cadastrados - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Buscar usuário por ID", 
               description = "Retorna um usuário específico pelo ID - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar usuário por email", 
               description = "Retorna um usuário específico pelo email - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(
            @Parameter(description = "Email do usuário") @PathVariable String email) {
        try {
            UserDTO user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar usuários por nome", 
               description = "Retorna usuários que contenham o nome especificado - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getUsersByName(
            @Parameter(description = "Nome ou parte do nome") @RequestParam String name) {
        List<UserDTO> users = userService.getUsersByName(name);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Buscar usuários por role", 
               description = "Retorna usuários com a role especificada - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(
            @Parameter(description = "Role do usuário (ADMIN ou CLIENT)") @PathVariable User.Role role) {
        List<UserDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Listar usuários ativos", 
               description = "Retorna lista de usuários ativos no sistema - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de usuários ativos retornada com sucesso")
    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getActiveUsers() {
        List<UserDTO> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Atualizar usuário", 
               description = "Atualiza os dados de um usuário existente - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "409", description = "Email já está em uso")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(summary = "Ativar/Desativar usuário", 
               description = "Altera o status ativo de um usuário - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status do usuário alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserDTO> toggleUserStatus(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @RequestBody Map<String, Boolean> statusRequest) {
        try {
            Boolean isActive = statusRequest.get("isActive");
            UserDTO updatedUser = userService.toggleUserStatus(id, isActive);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir usuário", 
               description = "Remove um usuário do sistema - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Contar usuários ativos", 
               description = "Retorna o número total de usuários ativos - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso")
    @GetMapping("/count/active")
    public ResponseEntity<Map<String, Long>> countActiveUsers() {
        long count = userService.countActiveUsers();
        return ResponseEntity.ok(Map.of("count", count));
    }

    @Operation(summary = "Contar usuários por role", 
               description = "Retorna o número de usuários com a role especificada - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso")
    @GetMapping("/count/role/{role}")
    public ResponseEntity<Map<String, Long>> countUsersByRole(
            @Parameter(description = "Role do usuário") @PathVariable User.Role role) {
        long count = userService.countUsersByRole(role);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @Operation(summary = "Verificar disponibilidade de email", 
               description = "Verifica se um email já está em uso - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    @GetMapping("/email-available")
    public ResponseEntity<Map<String, Boolean>> checkEmailAvailability(
            @Parameter(description = "Email a ser verificado") @RequestParam String email) {
        boolean isAvailable = !userService.isEmailInUse(email);
        return ResponseEntity.ok(Map.of("available", isAvailable));
    }

    @Operation(summary = "Listar usuários com agendamentos", 
               description = "Retorna usuários que possuem agendamentos - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @GetMapping("/with-bookings")
    public ResponseEntity<List<UserDTO>> getUsersWithBookings() {
        List<UserDTO> users = userService.getUsersWithBookings();
        return ResponseEntity.ok(users);
    }
}

