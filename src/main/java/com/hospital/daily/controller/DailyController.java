package com.hospital.daily.controller;

import com.hospital.bed.enums.BedType;
import com.hospital.daily.dto.DailyRequest;
import com.hospital.daily.model.Daily;
import com.hospital.daily.service.DailyService;
import com.hospital.ward.enums.Specialty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/daily")
public class DailyController {

    private final DailyService dailyService;

    public DailyController(DailyService dailyService) {
        this.dailyService = dailyService;
    }

    @PostMapping
    public ResponseEntity<Daily> criar(@RequestBody DailyRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(dailyService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Daily> atualizar(@PathVariable Long id, @RequestBody DailyRequest request){

        return ResponseEntity.status(HttpStatus.OK).body(dailyService.atualizar(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){

        this.dailyService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Daily> consultar(@RequestParam BedType type, @RequestParam Specialty specialty){
        return  ResponseEntity.status(HttpStatus.OK).body(dailyService.consultar(type,specialty));
    }
}
