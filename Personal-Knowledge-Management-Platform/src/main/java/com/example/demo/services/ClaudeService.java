package com.example.demo.services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(havingValue ="claude",name = "ai.agent.service",matchIfMissing = false )

public class ClaudeService implements  AIAgentService{
    @Override
    public String getSummary(String text) {
        return "Claud give summary text "+text;
    }
}
