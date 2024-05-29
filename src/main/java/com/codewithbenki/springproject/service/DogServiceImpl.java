package com.codewithbenki.springproject.service;

import com.codewithbenki.springproject.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class DogServiceImpl implements DogService {

    private WebClient webclient;

    @Autowired
    private WebClient.Builder weBuilder;

    @Override
    public Dog getDog() {
        // Configure the WebClient instance with the base URL for the Dog API
        webclient = weBuilder
                .baseUrl("https://dog.ceo/api/breeds/image/random")
                .build();

        // Make a GET request using WebClient
        return webclient
                .get()
                .retrieve()
                // Handle HTTP status errors (4xx and 5xx)
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorMessage -> Mono.error(new RuntimeException("Error: " + errorMessage)))
                )
                // Map the response body to a Dog object
                .bodyToMono(Dog.class)
                // Handle WebClientResponseException specifically
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Log the error message
                    System.err.println("Error response: " + ex.getMessage());
                    // Return an empty Mono, effectively returning null
                    return Mono.empty();
                })
                // Handle any other exceptions that might occur
                .onErrorResume(Exception.class, ex -> {
                    // Log the general error message
                    System.err.println("General error: " + ex.getMessage());
                    // Return an empty Mono, effectively returning null
                    return Mono.empty();
                })
                // Block to get the result synchronously (not recommended for reactive programming in production)
                .block();
    }
}
