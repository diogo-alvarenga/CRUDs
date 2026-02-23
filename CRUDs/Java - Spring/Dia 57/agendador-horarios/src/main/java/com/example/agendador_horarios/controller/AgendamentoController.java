package com.example.agendador_horarios.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.agendador_horarios.infrastructure.entity.Agendamento;
import com.example.agendador_horarios.service.AgendamentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

	private final AgendamentoService service;
	
	@PostMapping
	public ResponseEntity<Agendamento> salvarAgendamento(@RequestBody Agendamento agendamento){
		return ResponseEntity.accepted().body(service.salvarAgendamento(agendamento));
	}
	
	@DeleteMapping
    public ResponseEntity<Void> deletarAgendamento(@RequestParam String cliente,@RequestParam LocalDateTime dataHoraAgendamento) {

        service.deletarAgendamento(dataHoraAgendamento, cliente);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping
	public ResponseEntity<List<Agendamento>> buscarAgendamentoDoDia(@RequestHeader LocalDate data){
		return ResponseEntity.ok().body(service.buscarAgendamentosDia(data));
	}
	
	@PutMapping
	public ResponseEntity<Agendamento> alterarAgendamentos(@RequestBody Agendamento agendamento, @RequestHeader String cliente,@RequestHeader LocalDateTime dataHoraAgendamento){
		return ResponseEntity.accepted().body(service.alterarAgendamento(agendamento, cliente, dataHoraAgendamento));
	}
}
