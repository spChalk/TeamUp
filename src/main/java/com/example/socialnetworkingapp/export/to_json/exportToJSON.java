package com.example.socialnetworkingapp.export.to_json;

import com.example.socialnetworkingapp.export.ByteArrMultipartFile;
import com.example.socialnetworkingapp.filesystem.FileDBService;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.account.details.FullAccountDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
public class exportToJSON {

    private FileDBService storageService;
    private AccountService accountService;

    @PostMapping("/export/json")
    public ResponseEntity<String> export(@RequestBody ArrayList<Long> accountIds) throws IOException {

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