package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.job_view.JobView;
import com.example.socialnetworkingapp.model.job_view.JobViewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobViewMapper {

    @Mapping(target = "id", expression = "java(mapId(jobView.getId()))")
    @Mapping(target = "times", expression = "java(mapTimes(jobView.getTimes()))")
    @Mapping(target = "firstName", expression = "java(mapFirstName(jobView.getViewer()))")
    @Mapping(target = "lastName", expression = "java(mapLastName(jobView.getViewer()))")
    @Mapping(target = "email", expression = "java(mapEmail(jobView.getViewer()))")
    @Mapping(target = "imageUrl", expression = "java(mapImage(jobView.getViewer()))")
    JobViewResponse JobViewToJobViewResponse(JobView jobView);

    default Long mapId(Long id) {
        return id;
    }
    default Long mapTimes(Long times) {
        return times;
    }
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