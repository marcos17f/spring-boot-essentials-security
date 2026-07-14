package br.com.felipe.spring_boot_essentials.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExercicioResponseDto {
    private Integer id;
    private String nome;
    private String grupoMuscular;
}
