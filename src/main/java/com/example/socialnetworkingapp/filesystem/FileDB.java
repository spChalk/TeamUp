package com.example.socialnetworkingapp.filesystem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileDB {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private String type;
    private String ownerEmail;

    @Lob
    private byte[] data;

    public FileDB(String name, String type, String ownerEmail, byte[] data) {
        this.name = name;
        this.type = type;
        this.ownerEmail = ownerEmail;
        this.data = data;
    }
}