package com.community.Community.dto;

import com.community.Community.models.Users.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class Community_Create_Dto {




    @NotEmpty(message = "Name should not be empty")
    private String name;
    private String description;
    private boolean isPrivate;
    private long kralid;
    private User admin;

    public User getAdmin() {
        return admin;
    }
    public void setAdmin(User admin) {
        this.admin = admin;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getisPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void  setisPrivate() {
        this.isPrivate = true;
    }

    public long getKralid() {
        return kralid;
    }

    public void setKralid(long kralid) {
        this.kralid = kralid;
    }
}
