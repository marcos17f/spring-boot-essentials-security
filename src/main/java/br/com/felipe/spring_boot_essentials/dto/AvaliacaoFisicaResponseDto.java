package br.com.felipe.spring_boot_essentials.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AvaliacaoFisicaResponseDto {
    private Integer id;
    private String nomeAluno;
    private BigDecimal peso;
    private BigDecimal altura;
    private BigDecimal percentualGorduraCorporal;
    private LocalDateTime dataAvaliacao;
}
