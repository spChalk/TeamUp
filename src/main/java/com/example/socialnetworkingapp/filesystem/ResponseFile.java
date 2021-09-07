package com.example.socialnetworkingapp.filesystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseFile {

    private String name;
    private String url;
    private String type;
    private long size;
}