package com.agi.secondaryUserService.web;

import com.agi.secondaryUserService.dao.GroupDao;
import com.agi.secondaryUserService.dao.UserDao;
import com.agi.secondaryUserService.model.Group;
import com.agi.secondaryUserService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class controller {
    @Autowired
    GroupDao groupDao;

    @Autowired
    UserDao userDao;

    @PostMapping("/addGroups")
    public void saveGroup(@RequestBody Group group){
        groupDao.save(group);
    }


    @GetMapping("/users")
    public List<User> userList(){
        List<User> users;

        users = userDao.findAll();
        return users;
    }

    @PostMapping("/addUser")
    public void saveUser(@RequestBody User user){

        User userAdded;
        userAdded = userDao.save(user);

        List<Group> groups= groupDao.findAll();

        for(Group g : groups){
            if(g.getName().equalsIgnoreCase("General")){
                if(g.getUsers() == null){
                    g.setUsers(Arrays.asList(new User(user.getEmail())));
                }else{
                    g.getUsers().add(new User(user.getEmail()));
                }
                groupDao.save(g);
            }
        }

    }

    @PostMapping ("/addUserToGroup/{id}")
    public void addUserToGroup( @RequestParam ("email") String email,@PathVariable UUID id){
        Group group= null;

        List<Group> groups= groupDao.findAll();

        for(Group g : groups){
            if(g.getId().equals(id)){
                System.out.println(g.getId());
                group = g;
                if(g.getUsers() == null){
                    g.setUsers(Arrays.asList(new User(email)));
                }else{
                    g.getUsers().add(new User(email));
                }
                groupDao.save(g);
            }
        }

    }

    @PostMapping ("/removeUserToGroup/{id}")
    public void removeUserToGroup( @RequestParam ("email") String email,@PathVariable UUID id){
        Group group= null;

        List<Group> groups= groupDao.findAll();


            for(Group g : groups) {
                if (g.getId().equals(id)) {
                    group = g;
                        for (User u : g.getUsers()) {
                            if (u.getEmail().equalsIgnoreCase(email)) {
                                g.getUsers().remove(u);
                                groupDao.save(g);

                            }
                        }
                    }
                }


            }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable UUID id) {
        Optional<User> user = userDao.findById(id);

        List<Group> groups = groupDao.findAll();

        for(Group g: groups){
            for(User u : g.getUsers()) {
                if(u.getEmail().equalsIgnoreCase(user.get().getEmail())) {
                    g.getUsers().remove(u);
                    groupDao.save(g);
                }
            }
        }
        userDao.deleteById(id);
    }
}
