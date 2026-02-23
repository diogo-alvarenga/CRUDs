package com.example.agendador_horarios.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.agendador_horarios.infrastructure.entity.Agendamento;
import com.example.agendador_horarios.infrastructure.repository.AgendamentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
	
	private final AgendamentoRepository repository;

	public Agendamento salvarAgendamento(Agendamento agendamento) {
		LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
		LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusHours(1);
		
		Agendamento agendados= repository.findByServicoAndDataHoraAgendamentoBetween(agendamento.getServico(), horaAgendamento, horaFim);
		
		if(Objects.nonNull(agendados)) {
			throw new RuntimeException("O horario já está preenchido.");
		}
		
		return repository.save(agendamento);
	}
	
	public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente){
        repository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);
    }
	
	public List<Agendamento> buscarAgendamentosDia(LocalDate data) {
		LocalDateTime primeiraHoraDia = data.atStartOfDay();//primeira hora do dia
		LocalDateTime horaFinalDia = data.atTime(23,59,59);
		
		return repository.findByDataHoraAgendamentoBetween(primeiraHoraDia, horaFinalDia);
	}
	
	public Agendamento alterarAgendamento(Agendamento agendamento, String ciente, LocalDateTime dataHoraAgendamento) {
		Agendamento agenda = repository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, ciente);
		
		if(Objects.isNull(agenda)) {
			throw new RuntimeException("Horário não está preenchido.");
		}
		agendamento.setId(agenda.getId());
		return repository.save(agendamento);
	}
}
