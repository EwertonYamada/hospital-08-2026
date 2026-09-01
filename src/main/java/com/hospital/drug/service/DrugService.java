package com.hospital.drug.service;

import com.hospital.drug.dto.DrugRequest;
import com.hospital.drug.model.Drug;
import com.hospital.drug.repository.DrugRepository;
import com.hospital.utils.exceptions.AlreadyExistingEntityException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugService {

    private final DrugRepository drugRepository;

    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    private void validarCodigoDuplicado(String code){
        if (drugRepository.existsByCode(code)) {
            throw new AlreadyExistingEntityException("Code ja cadastrado");
        }
    }

    private void validarEstoque(Integer stock){
        if (stock < 0){
            throw new RuntimeException("Estoque negativo");
        }
    }
    public Drug criar(DrugRequest request) {
        this.validarCodigoDuplicado(request.code());
        this.validarEstoque(request.stock());

        Drug drug = new Drug();
        drug.setCode(request.code());
        drug.setName(request.name());
        drug.setValue(request.value());
        drug.setStock(request.stock());

        return drugRepository.save(drug);
    }

    public Drug getById(Long id){
        return this.drugRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id nao encontrado"));
    }

    public List<Drug> findAll(){
        return drugRepository.findAll();
    }

    public Drug atualizar(Long id, DrugRequest request){

        Drug drug = this.getById(id);

        if (!drug.getCode().equals(request.code())){
            this.validarCodigoDuplicado(request.code());
        }

        this.validarEstoque(request.stock());

        drug.setCode(request.code());
        drug.setName(request.name());
        drug.setValue(request.value());
        drug.setStock(request.stock());

        return drugRepository.save(drug);
    }

    public void deletar(Long id){
        Drug drug = this.getById(id);
        this.drugRepository.delete(drug);
    }
}
