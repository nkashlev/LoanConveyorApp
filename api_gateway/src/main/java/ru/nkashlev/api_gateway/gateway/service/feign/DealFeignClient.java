package ru.nkashlev.api_gateway.gateway.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nkashlev.api_gateway.model.FinishRegistrationRequestDTO;
import ru.nkashlev.api_gateway.model.LoanApplicationRequestDTO;
import ru.nkashlev.api_gateway.model.SendSesCodeRequest;

import java.util.List;

@FeignClient(name = "dealFeignClient", url = "http://localhost:8080/deal")
public interface DealFeignClient {
    @PutMapping("/calculate/{application_id}")
    ResponseEntity<Void> dealCalculateApplicationIdPut(@PathVariable("application_id") Long applicationId, @RequestBody FinishRegistrationRequestDTO request);

    @PostMapping("/document/{applicationId}/code")
    ResponseEntity<Void> sendSesCode(@PathVariable Long applicationId, @RequestBody SendSesCodeRequest sendSesCodeRequest);

    @PostMapping("/document/{applicationId}/send")
    ResponseEntity<Void> createDocuments(@PathVariable("applicationId") Long applicationId);

    @PostMapping("/document/{applicationId}/sign")
    ResponseEntity<Void> signDocuments(@PathVariable Long applicationId);

    @GetMapping("/admin/application/{applicationId}")
    ResponseEntity<LoanApplicationRequestDTO> get(@PathVariable Long applicationId);

    @GetMapping("/admin/application")
    ResponseEntity<List<LoanApplicationRequestDTO>> getAll();
}
