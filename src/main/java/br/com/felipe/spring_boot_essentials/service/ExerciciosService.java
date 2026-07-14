package br.com.felipe.spring_boot_essentials.service;

import br.com.felipe.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.felipe.spring_boot_essentials.database.repository.IExerciciosRepository;
import br.com.felipe.spring_boot_essentials.dto.ExercicioDto;
import br.com.felipe.spring_boot_essentials.dto.ExercicioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciciosService {

    private final IExerciciosRepository exerciciosRepository;

    public List<ExercicioResponseDto> findAll(){
        return exerciciosRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void save(ExercicioDto exercicioDto){
        ExerciciosEntity exercicio= ExerciciosEntity.builder()
                .nome(exercicioDto.getNome())
                .grupoMuscular(exercicioDto.getGrupoMuscular())
                .build();

        exerciciosRepository.save(exercicio);
    }

    public List<ExercicioResponseDto> getExerciciosByGrupoMuscular(String grupoMuscular){
        return exerciciosRepository.findByGrupoMuscular(grupoMuscular).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ExercicioResponseDto toDto(ExerciciosEntity entity) {
        return ExercicioResponseDto.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .grupoMuscular(entity.getGrupoMuscular())
                .build();
    }
}