package com.example.socialnetworkingapp.export.to_xml;

import com.example.socialnetworkingapp.export.ByteArrMultipartFile;
import com.example.socialnetworkingapp.filesystem.FileDBService;
import com.example.socialnetworkingapp.model.account.Account;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@Controller
public class exportToXML {

    private FileDBService storageService;

    @PostMapping("/export/xml")
    public ResponseEntity<String> export(@RequestBody ArrayList<Account> accounts) throws IOException {

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMapper.writeValue(byteArrayOutputStream, accounts);

        byte[] isr = byteArrayOutputStream.toByteArray();
        String fileName = "EXP_accounts_" + LocalDate.now() + "__"
                + LocalTime.now().toString().replace(':', '-')
                .replace('.', '-')
                + ".xml", path = "";
        ByteArrMultipartFile customMultipartFile = new ByteArrMultipartFile(isr, fileName, path);
        try {
            customMultipartFile.transferTo(customMultipartFile.getFile());
        } catch (IllegalStateException | IOException e) {
            System.out.println(e);
        }
        return new ResponseEntity<>("/download/file/" + customMultipartFile.getFileName(), HttpStatus.OK);
    }
}