package com.razzzil.lab4.controllers;

import com.razzzil.lab4.exceptions.ForbiddenException;
import com.razzzil.lab4.exceptions.ResourceNotFoundException;
import com.razzzil.lab4.models.Resource;
import com.razzzil.lab4.models.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    @Qualifier("resourcesDatasource")
    private List<Resource> resources;

    public static final String RESOURCES_URL_PREFIX = "/resources";
    public static final String ADD_URL_PREFIX = "/add";
    public static final String MODIFY_URL_PREFIX = "/modify";
    public static final String DELETE_URL_PREFIX = "/delete";
    public static final String INIT = "/init";

    @GetMapping(RESOURCES_URL_PREFIX + "/{id}")
    public String getResource(Authentication authentication, Model model, @PathVariable("id") String id){
        Resource resource = this.getResource(id);
        this.setUsername(model, authentication);
        if(resource.getAvailableRoles().contains(this.getRole(authentication))) {
            model.addAttribute("resource", resource);
            return "resource";
        } else throw new ForbiddenException();
    }

    @GetMapping("/")
    public String getResourcesList(Authentication authentication, Model model){
        this.setUsername(model, authentication);
        Role role = this.getRole(authentication);
        List<Resource> availableResources = new ArrayList<>();
        for (Resource resource: resources){
            if (resource.getAvailableRoles().contains(role))
                availableResources.add(resource);
        }
        model.addAttribute("resources", availableResources);
        model.addAttribute("isAdmin", role.equals(Role.ADMIN) ? true : null);
        model.addAttribute("isManager", role.equals(Role.ADMIN) || role.equals(Role.MANAGER) ? true : null);
        model.addAttribute("isTeacher", role.equals(Role.ADMIN) || role.equals(Role.MANAGER) || role.equals(Role.TEACHER) ? true : null);
        return "main";
    }

    @GetMapping(ADD_URL_PREFIX)
    public String addResource(@RequestParam("name") String name, @RequestParam("url") String url, @RequestParam("roles") String[] roles){
        Resource resource = Resource.builder()
                .name(name)
                .url(url)
                .availableRoles(Role.mapToSet(roles))
                .build();
        resource.getAvailableRoles().add(Role.MANAGER);
        resource.getAvailableRoles().add(Role.ADMIN);
        resource.setId(resources.size());
        resources.add(resource);
        return "redirect:/";
    }

    @GetMapping(MODIFY_URL_PREFIX + "/{id}")
    public String modifyResource(@PathVariable("id") String id, @RequestParam("name") String name, @RequestParam("url") String url, @RequestParam("roles") String[] roles){
        Resource resource = this.getResource(id);
        resource.setName(name);
        resource.setUrl(url);
        resource.setAvailableRoles(Role.mapToSet(roles));
        resource.getAvailableRoles().add(Role.MANAGER);
        resource.getAvailableRoles().add(Role.ADMIN);
        //LoggerFactory.getLogger(getClass()).info(resources.toString());
        return "redirect:/";
    }

    @GetMapping(INIT + MODIFY_URL_PREFIX + "/{id}")
    public String modifyResource(@PathVariable("id") String id, Model model, Authentication authentication){
        this.setUsername(model, authentication);
        Resource resource = this.getResource(id);
        model.addAttribute("action", "Modify " + resource.getName());
        model.addAttribute("url", MODIFY_URL_PREFIX + "/" + id);
        model.addAttribute("resource", resource);
        model.addAttribute("roles", Role.values());
        return "addOrModify";
    }

    @GetMapping(INIT + ADD_URL_PREFIX)
    public String addResource(Model model, Authentication authentication){
        this.setUsername(model, authentication);
        Resource resource = Resource.getInitialResource();
        model.addAttribute("action", "Add new resource");
        model.addAttribute("url", ADD_URL_PREFIX);
        model.addAttribute("resource", resource);
        model.addAttribute("roles", Role.values());
        return "addOrModify";
    }

    @GetMapping(DELETE_URL_PREFIX + "/{id}")
    public String deleteResource(@PathVariable("id") String id){
        resources.remove(Integer.parseInt(id));
        return "redirect:/";
    }

    private Resource getResource(String id){
        try {
            return resources.get(Integer.parseInt(id));
        } catch (IndexOutOfBoundsException e){
            throw new ResourceNotFoundException();
        }
    }
    
    private Role getRole(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = ((UserDetails) authentication.getPrincipal()).getAuthorities();
        return Role.mapToRole(authorities.iterator().next().getAuthority().split("_")[1]);
    }

    private void setUsername(Model model, Authentication authentication){
        model.addAttribute("username", ((UserDetails) authentication.getPrincipal()).getUsername());
    }
}
