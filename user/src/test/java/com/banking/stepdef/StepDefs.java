package com.banking.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class StepDefs {

    @LocalServerPort
    String port;

    ResponseEntity<String> latestResponse;


    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Given("the user service is running")
    public void theUserServiceIsRunning() {
        System.out.println("Hi there!!");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @When("I create a user with name {string} and email {string}")
    public void iCreateAUserWithNameAndEmail(String name, String email) throws Exception {
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content("{\"name\": \"" + name + "\", \"email\": \"" + email + "\"}"))
                .andExpect(status().isOk());
    }

    @Then("the user should be created successfully")
    public void theUserShouldBeCreatedSuccessfully() {
    }

    @Then("I get the details of the user with id {int}")
    public void iGetTheDetailsOfTheUserWithId(int id) throws Exception {
        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Then("the user details should be returned")
    public void theUserDetailsShouldBeReturned() {
        // Additional assertions can be added here if needed
    }
}