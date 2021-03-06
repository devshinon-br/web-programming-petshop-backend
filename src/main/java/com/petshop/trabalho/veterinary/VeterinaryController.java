package com.petshop.trabalho.veterinary;

import com.petshop.trabalho.response.Response;
import com.petshop.trabalho.veterinary.request.VeterinaryRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Veterinary")
@Slf4j
@RestController
@RequestMapping("/api/vet")
public class VeterinaryController {

    private final VeterinaryService veterinaryService;

    public VeterinaryController(VeterinaryService veterinaryService) {
        this.veterinaryService = veterinaryService;
    }

    @GetMapping
    public ResponseEntity<Response<List<Veterinary>>> getValues(){
        Response<List<Veterinary>> response = new Response<>();
        try {
            response.setData(veterinaryService.getAll());
        } catch (Exception e){
            log.error("Erro buscar todas os vets.");
            response.getErrors().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Veterinary>> getValueById (@PathVariable(name = "id") Long id) {
        Response<Veterinary> response = new Response<>();
        try{
            response.setData(veterinaryService.findById(id));
        } catch (Exception e){
            log.error("Erro ao encontrar o Vet de id: {}", id);
            response.getErrors().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Veterinary>> saveValues (VeterinaryDTO veterinaryDTO) {
        Response<Veterinary> response = new Response<>();
        try{
            log.info("Salvando Vet: {}", veterinaryDTO);
            response.setData(veterinaryService.save(veterinaryDTO));
            log.info("Vet salvo com sucesso.");
        } catch (Exception e){
            log.error("Erro salvar Vet: {}", veterinaryDTO);
            response.getErrors().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Response<Veterinary>> updateValues (@PathVariable(name = "id") Long id, VeterinaryRequest veterinaryRequest) {
        Response<Veterinary> response = new Response<>();
        try{
            log.info("Atualizando Vet: {}", veterinaryRequest);
            response.setData(veterinaryService.updateCrmv(id, veterinaryRequest));
            log.info("Vet atualizado com sucesso.");
        } catch (Exception e){
            log.error("Erro atualizar Vet: {}", veterinaryRequest);
            response.getErrors().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Veterinary>> delete(@PathVariable(name = "id") Long id) {
        Response<Veterinary> response = new Response<>();
        try{
            log.info("Deletando Vet de id: {}", id);
            response.setData(veterinaryService.delete(id));
            log.info("Vet deletado com sucesso.");
        } catch (Exception e){
            log.error("Erro deletar Vet de id: {}", id);
            response.getErrors().add(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
