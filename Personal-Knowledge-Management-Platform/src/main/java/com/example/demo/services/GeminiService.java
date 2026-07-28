package com.example.demo.services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(havingValue ="gemini",name = "ai.agent.service",matchIfMissing = false )

public class GeminiService implements  AIAgentService{
    @Override
    public String getSummary(String text) {
        return "GEMINI give summary text "+text;
    }
}
