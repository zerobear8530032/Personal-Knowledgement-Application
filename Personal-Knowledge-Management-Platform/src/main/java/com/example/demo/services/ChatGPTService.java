package com.example.demo.services;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(havingValue ="chatgpt",name = "ai.agent.service",matchIfMissing = false )
public class ChatGPTService implements  AIAgentService{
    @Override
    public String getSummary(String text) {
        return "CHATGPT give summary text "+text;
    }
}
