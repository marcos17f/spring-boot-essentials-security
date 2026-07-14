package br.com.felipe.spring_boot_essentials.service;

import br.com.felipe.spring_boot_essentials.database.model.AlunosEntity;
import br.com.felipe.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.felipe.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.felipe.spring_boot_essentials.database.repository.IAvaliacoesFisicasRepository;
import br.com.felipe.spring_boot_essentials.dto.AvaliacaoFisicaDto;
import br.com.felipe.spring_boot_essentials.dto.AvaliacaoFisicaResponseDto;
import br.com.felipe.spring_boot_essentials.exception.BadRequestException;
import br.com.felipe.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoFisicaService {
    private final IAlunosRepository alunosRepository;
    private final IAvaliacoesFisicasRepository avaliacoesFisicasRepository;

    public void criarAvaliacaoFisica(AvaliacaoFisicaDto avaliacaoFisicaDto) throws NotFoundException, BadRequestException {
        AlunosEntity aluno = alunosRepository.findById(avaliacaoFisicaDto.getAlunoId())
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        AvaliacoesFisicasEntity avaliacaoFisica = aluno.getAvaliacaoFisica();
        if (avaliacaoFisica != null) {
            throw new BadRequestException("Avaliação física já cadastrada para este aluno");
        }

        avaliacaoFisica = AvaliacoesFisicasEntity.builder()
                .peso(avaliacaoFisicaDto.getPeso())
                .altura(avaliacaoFisicaDto.getAltura())
                .porcentagemGorduraCorporal(avaliacaoFisicaDto.getPercentualGorduraCorporal())
                .build();

        aluno.setAvaliacaoFisica(avaliacaoFisica);
        alunosRepository.save(aluno);
    }

    public List<AvaliacaoFisicaResponseDto> getAllAvaliacoes(){
        List<AvaliacoesFisicasEntity> avaliacoes = avaliacoesFisicasRepository.findAll();
        return avaliacoes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Page<AvaliacaoFisicaResponseDto> getAllAvaliacoesPageable(Integer page, Integer size){
        Page<AvaliacoesFisicasEntity> avaliacoesPage = avaliacoesFisicasRepository.findAll(PageRequest.of(page, size));
        List<AvaliacaoFisicaResponseDto> dtos = avaliacoesPage.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, avaliacoesPage.getPageable(), avaliacoesPage.getTotalElements());
    }

    private AvaliacaoFisicaResponseDto mapToDto(AvaliacoesFisicasEntity avaliacao) {
        // Esta é uma forma de encontrar o aluno associado.
        // Pode não ser a mais performática para listas grandes, mas resolve o problema.
        AlunosEntity aluno = alunosRepository.findAll().stream()
                .filter(a -> avaliacao.equals(a.getAvaliacaoFisica()))
                .findFirst()
                .orElse(null);

        return AvaliacaoFisicaResponseDto.builder()
                .id(avaliacao.getId())
                .nomeAluno(aluno != null ? aluno.getNome() : "Aluno não encontrado")
                .peso(avaliacao.getPeso())
                .altura(avaliacao.getAltura())
                .percentualGorduraCorporal(avaliacao.getPorcentagemGorduraCorporal())
                .dataAvaliacao(avaliacao.getDataAvaliacao())
                .build();
    }
}