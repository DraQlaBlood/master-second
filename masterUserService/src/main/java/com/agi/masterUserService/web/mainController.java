package com.agi.masterUserService.web;

import com.agi.masterUserService.dao.GroupDao;
import com.agi.masterUserService.dao.UserDao;
import com.agi.masterUserService.exceptions.EmailExistsException;
import com.agi.masterUserService.exceptions.UserInGroupException;
import com.agi.masterUserService.model.Group;
import com.agi.masterUserService.model.User;
import com.agi.masterUserService.proxies.SecondaryServiceProxy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.*;


@Api( description="API For CRUD operations on Users and Groups")

@RestController
@RequestMapping(value = "/master/v1")
public class mainController {
    @Autowired
    GroupDao groupDao;

    @Autowired
    UserDao userDao;

    @Autowired
    private MongoOperations mongoTemplate;

    @Autowired
    private SecondaryServiceProxy serviceProxy;

    @GetMapping("/groups")
    public List<Group> groupList(){

        List<Group> groups=null;

        groups = groupDao.findAll();
        return  groups;
    }

    @ApiOperation(value = "Get users list")
    @GetMapping("/users")
    public List<User> userList(){
        List<User> users;

        users = userDao.findAll();
        return users;
    }

    @ApiOperation(value = "Populate both Microservices database with group")
    @PostMapping("/addGroup")
    public void  addGroup(){
        Group group = new Group();
        Group group1 = new Group();
        Group group2 = new Group();

        Group groupAdded;
        group.setId(UUID.randomUUID());
        group.setName("General");

        group1.setId(UUID.randomUUID());
        group1.setName("Group1");

        group2.setId(UUID.randomUUID());
        group2.setName("Group2");

        groupDao.save(group);
        groupDao.save(group1);
        groupDao.save(group2);

        serviceProxy.saveGroup(group);
        serviceProxy.saveGroup(group1);
        serviceProxy.saveGroup(group2);
    }

    @ApiOperation(value = "add new user and assign them to general group")
    @PostMapping("/addUser")
    public ResponseEntity<Void> saveUser(@RequestBody User user){


        User userAdded;

        if(emailExist(user.getEmail()))
            throw new EmailExistsException("Email already exists");

        user.setId(UUID.randomUUID());
        user.setGroups(Arrays.asList(new Group("General")));
        userAdded = userDao.save(user);

        List<Group> groups= groupDao.findAll();

        for(Group g : groups){
            if(g.getName().equalsIgnoreCase("General")){
                System.out.println(g.getName() +" "+ g.getUsers());
                if(g.getUsers() == null){
                    g.setUsers(Arrays.asList(new User(user.getEmail())));
                }else{
                    g.getUsers().add(new User(user.getEmail()));
                }
                groupDao.save(g);
            }
        }
        if(userAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userAdded.getId())
                .toUri();
        serviceProxy.saveUser(user);
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Add user to group using the group Id and user email")
    @PostMapping ("/addUserToGroup/{id}")
    public ResponseEntity<Void> addUserToGroup( @RequestParam ("email") String email,@PathVariable UUID id){
        Group group= null;

       List<Group> groups= groupDao.findAll();

        if(emailExist(email)){

        for(Group g : groups){
            if(g.getId().equals(id)){
                group = g;
                if(g.getUsers() == null){
                    g.setUsers(Arrays.asList(new User(email)));
                }else{
                    for(User u : g.getUsers()) {
                        if(u.getEmail().equalsIgnoreCase(email)) {
                            throw new UserInGroupException("User cannot be added twice to the same group");
                        }else {
                            g.getUsers().add(new User(email));
                            groupDao.save(g);
                            serviceProxy.addUserToGroup(email, id);
                       }
                    }
                }
            }
        }
        }else{
            throw new UserInGroupException("User does not exist");
        }
        if(group == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(group.getId())
                .toUri();



        return ResponseEntity.created(location).build();
    }


    @ApiOperation(value = "Remove user to group using the group Id and user email")
    @PostMapping ("/removeUserToGroup/{id}")
    public void removeUserToGroup( @RequestParam ("email") String email,@PathVariable UUID id){
        Group group= null;

        List<Group> groups= groupDao.findAll();

        if(emailExist(email)){

            for(Group g : groups){
                if(g.getId().equals(id)){
                    group = g;
                    if(g.getUsers() == null){
                        throw new UserInGroupException("This group is empty");
                    }else{
                        for(User u : g.getUsers()) {
                            if(u.getEmail().equalsIgnoreCase(email)) {
                                g.getUsers().remove(u);
                                groupDao.save(g);
                                serviceProxy.removeUserToGroup(email, id);
                            }else {
                                throw new UserInGroupException("User does not exist");

                            }
                        }
                    }
                }
            }
        }else{
            throw new UserInGroupException("User does not exist");
        }

    }

    @ApiOperation(value = "Delete user from system using user Id")
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable UUID id) {
        Optional<User> user = userDao.findById(id);

        List<Group> groups = groupDao.findAll();

        for(Group g: groups){
            for(User u : g.getUsers()) {
                if(u.getEmail().equalsIgnoreCase(user.get().getEmail())) {
                    g.getUsers().remove(u);
                    groupDao.save(g);
                    serviceProxy.deleteUser(id);
                }else {
                    throw new UserInGroupException("User does not exist");

                }
            }
        }
        userDao.deleteById(id);
    }

    private boolean emailExist(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

}
