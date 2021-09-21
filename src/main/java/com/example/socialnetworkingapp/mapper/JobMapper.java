package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.job.Job;
import com.example.socialnetworkingapp.model.job.JobResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface JobMapper {

    @Mapping(target = "id", expression = "java(mapId(job.getId()))")
    @Mapping(target = "publisherFirstName", expression = "java(mapFirstName(job.getPublisher()))")
    @Mapping(target = "publisherLastName", expression = "java(mapLastName(job.getPublisher()))")
    @Mapping(target = "publisherEmail", expression = "java(mapEmail(job.getPublisher()))")
    @Mapping(target = "publisherImage", expression = "java(mapImage(job.getPublisher()))")
    @Mapping(target = "date", expression = "java(mapDate(job.getDate()))")
    JobResponse JobToJobResponse(Job job);

    default Long mapId(Long id){
        return id;
    }
    default String mapDate(Date date) { return date.toString(); }
    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
    default String mapEmail(Account account){
        return account.getUsername();
    }
    default String mapImage(Account account){
        return account.getImageUrl();
    }
}
