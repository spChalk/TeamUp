package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.account.AccountResponse;
import com.example.socialnetworkingapp.post.Post;
import com.example.socialnetworkingapp.post.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "following", expression = "java(mapFollowing(account))")
    AccountResponse AccountToAccountResponse(Account account);

    default List<String> mapFollowing(Account account){
        List<String> friends = new ArrayList<String>();
        for (Account friend : account.getFollowing()){
           friends.add(friend.getUsername());
        }
        return friends;
    }
}
