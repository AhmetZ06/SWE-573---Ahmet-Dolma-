package com.community.Community.dto;

import com.community.Community.models.Users.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Community_Create_Dto {


    @NotEmpty(message = "Name should not be empty")
    private String name;
    private String description;
    private boolean isPrivate;
    private long kralid;

    public void setAdmin (User admin){
        this.kralid = admin.getUserId();
    }

}
