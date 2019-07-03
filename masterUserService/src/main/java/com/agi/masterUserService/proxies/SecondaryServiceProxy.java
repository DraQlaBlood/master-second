package com.agi.masterUserService.proxies;


import com.agi.masterUserService.beans.UserBean;
import com.agi.masterUserService.model.Group;
import com.agi.masterUserService.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "secondaryService" ,url = "localhost:8082")
public interface SecondaryServiceProxy {

    @PostMapping("/addUser")
    public void saveUser(@RequestBody User user);

    @PostMapping ("/addUserToGroup/{id}")
    public void addUserToGroup(@RequestParam("email") String email, @PathVariable UUID id);

    @PostMapping("/addGroups")
    public void saveGroup(@RequestBody Group group);

    @PostMapping ("/removeUserToGroup/{id}")
    public void removeUserToGroup( @RequestParam ("email") String email,@PathVariable UUID id);

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable UUID id);


}
