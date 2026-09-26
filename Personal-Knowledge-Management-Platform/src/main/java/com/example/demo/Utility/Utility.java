package com.example.demo.Utility;

import com.example.demo.dtos.CustomUserDetail;
import com.example.demo.enums.UserEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Utility {


    @Value("${users.max-pages.size}")
    private  int maxPageSize;

    public  Authentication getCurrentUserAuthentication(){
       return SecurityContextHolder.getContext().getAuthentication();
    }


    public  String getCurrentUserName(){
       return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public  CustomUserDetail getCurrentLoggedInUser(){
       return (CustomUserDetail)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }


    public  Long getCurrentLoggedInUserId(){
        return getCurrentLoggedInUser().getId();
    }

    public  PageRequest getPagination(int size, int page, UserEnum sortBy, Sort.Direction direction){
        if (size <= 0) {
            size = 5;
        } else {
            size = Math.min(maxPageSize, size);
        }
        if(page<0){
            page=0;
        }
        Sort sort=Sort.by(direction,sortBy.getValue());
        PageRequest pageRequest= PageRequest.of(page,size,sort);
        return pageRequest;
    }

}
