package com.microservice.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
/**
 * Spring Boot Microservice Demo Application
 * 
 * This demonstrates:
 * - RESTful web services
 * - JSON responses
 * - Path variables and request parameters
 * - Health checks
 * - Containerization with Docker
 */
@SpringBootApplication
@RestController
public class SpringBootDockerDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDockerDemoApplication.class, args);
    }
    /**
     * Root endpoint - welcome message
     */
    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "🐳 Hello Docker World!Welcome learners!!");
        response.put("service", "Spring Boot Microservice Demo");
        response.put("version", "1.0.0");
        response.put("timestamp", getCurrentTimestamp());
        response.put("endpoints", Map.of(
            "health", "/actuator/health",
            "greeting", "/api/greeting/{name}",
            "echo", "/api/echo?message=your-message",
            "status", "/api/status"
        ));
        return response;
    }
    /**
     * Personalized greeting endpoint
     */
    @GetMapping("/api/greeting/{name}")
    public Map<String, Object> greeting(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        response.put("greeting", "Hello, Learner " + name + "!");
        response.put("service", "greeting-service");
        response.put("timestamp", getCurrentTimestamp());
        response.put("containerized", "true");
        return response;
    }
    /**
     * Echo endpoint for testing
     */
    @GetMapping("/api/echo")
    public Map<String, Object> echo(@RequestParam(defaultValue = "Hello Microservices World...echo learners!") String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("echo", message);
        response.put("length", message.length());
        response.put("uppercase", message.toUpperCase());
        response.put("timestamp", getCurrentTimestamp());
        return response;
    }
    /**
     * Service status endpoint
     */
    @GetMapping("/api/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "spring-boot-docker-demo");
        response.put("uptime", getUptime());
        response.put("timestamp", getCurrentTimestamp());
        response.put("environment", System.getProperty("spring.profiles.active", "default"));
        
        // Memory information
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> memory = new HashMap<>();
        memory.put("total", runtime.totalMemory() / 1024 / 1024 + " MB");
        memory.put("free", runtime.freeMemory() / 1024 / 1024 + " MB");
        memory.put("used", (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + " MB");
        response.put("memory", memory);
        
        return response;
    }
    /**
     * Get current timestamp
     */
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    /**
     * Get application uptime (simplified)
     */
    private String getUptime() {
        long uptimeMs = java.lang.management.ManagementFactory.getRuntimeMXBean().getUptime();
        long seconds = uptimeMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        if (hours > 0) {
            return String.format("%d hours, %d minutes", hours, minutes % 60);
        } else if (minutes > 0) {
            return String.format("%d minutes, %d seconds", minutes, seconds % 60);
        } else {
            return String.format("%d seconds", seconds);
        }
    }
}