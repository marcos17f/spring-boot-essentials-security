package br.com.felipe.spring_boot_essentials.service;


import br.com.felipe.spring_boot_essentials.config.TokenProvider;
import br.com.felipe.spring_boot_essentials.database.model.AlunosEntity;
import br.com.felipe.spring_boot_essentials.database.model.RolesEntity;
import br.com.felipe.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.felipe.spring_boot_essentials.database.repository.IRolesRepository;
import br.com.felipe.spring_boot_essentials.dto.LoginRequestDto;
import br.com.felipe.spring_boot_essentials.dto.RegisterRequestDto;
import br.com.felipe.spring_boot_essentials.dto.TokenResponseDTO;
import br.com.felipe.spring_boot_essentials.enums.RoleTypeEnum;
import br.com.felipe.spring_boot_essentials.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final IAlunosRepository alunosRepository;
    private final IRolesRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Value("${jwt.expiration}")
    private long expirationTime;

    public void register(RegisterRequestDto registerRequestDto) throws BadRequestException {
        if (alunosRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new BadRequestException("Email já cadastrado");
        }

        RolesEntity role = roleRepository.findByNome(RoleTypeEnum.ROLE_ALUNO.name())
                .orElseGet(() -> roleRepository.save(RolesEntity.builder()
                        .nome(RoleTypeEnum.ROLE_ALUNO.name())
                        .build()));

        alunosRepository.save(AlunosEntity.builder()
                .nome(registerRequestDto.getNome())
                .email(registerRequestDto.getEmail())
                .senha(passwordEncoder.encode(registerRequestDto.getSenha()))
                .roles(Set.of(role))
                .build());
    }

    public TokenResponseDTO login(LoginRequestDto loginDto) throws Exception{
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getSenha())
            );

            String token = tokenProvider.generateToken(authentication);
            return TokenResponseDTO.builder()
                    .token(token)
                    .type("Bearer")
                    .expiration(expirationTime)
                    .build();
        }catch (BadCredentialsException e){
            throw new BadRequestException("Credenciais inválidas");
        }catch (Exception e){
            throw new Exception("Erro interno inesperado: " + e.getMessage());
        }
    }
}
