package com.example.demo.controllers;

import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
// this controller is just for small experiments nothing else

@RestController
@RequestMapping("/cache")
public class CacheController {
    CacheManager cacheManager;
    @Autowired
    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping
    public List<String> getFullCache(){
        return cacheManager.getCacheNames().stream().toList();
    }
    @GetMapping("/{name}")
    public Object getCacheByName(@PathVariable(name = "name") String name){
        return  cacheManager.getCache(name);
    }
}
