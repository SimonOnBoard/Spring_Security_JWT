package ru.itis.servlets.services;


import java.util.Map;

public interface TemplateProcessor {
    String getProcessedTemplate(Map<String, String> params, String ftl);
}
