package com.example.socialnetworkingapp.export.to_json;

import com.example.socialnetworkingapp.export.ByteArrMultipartFile;
import com.example.socialnetworkingapp.filesystem.FileDBService;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountRole;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.account.details.FullAccountDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*
    https://www.baeldung.com/jackson-xml-serialization-and-deserialization
    https://www.baeldung.com/jackson-object-mapper-tutorial
    https://roytuts.com/file-download-example-using-spring-rest-controller/
    https://www.baeldung.com/jackson-serialize-dates
*/

@Controller
@AllArgsConstructor
public class exportToJSON {

    private AccountService accountService;

    @PostMapping("/export/json")
    public ResponseEntity<String> export(@RequestBody ArrayList<Long> accountIds) throws IOException {

        Account account = this.accountService.findAccountByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(account.getRole() == AccountRole.ADMIN){
            return null;
        }
        List<FullAccountDetails> accounts = this.accountService.getFullAccountsDetails(accountIds);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = objectMapper.writeValueAsString(accounts);

        byte[] isr = json.getBytes();
        String fileName = "EXP_accounts_" + LocalDate.now() + "__"
                + LocalTime.now().toString().replace(':', '-')
                .replace('.', '-')
                + ".json ", path = "";
        ByteArrMultipartFile customMultipartFile = new ByteArrMultipartFile(isr, fileName, path);
        try {
            customMultipartFile.transferTo(customMultipartFile.getFile());
        } catch (IllegalStateException | IOException e) {
            System.out.println(e);
        }
        return new ResponseEntity<>("/download/file/" + customMultipartFile.getFileName(), HttpStatus.OK);
    }
}