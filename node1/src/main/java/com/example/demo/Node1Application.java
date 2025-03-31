package com.example.demo;

import com.example.demo.eneity.DbNode;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Node1Application {

  public static void main(String[] args) {
    SpringApplication.run(Node1Application.class, args);
  }
}
