package steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public class UserSteps {

    private static final String BASE_URL = "https://reqres.in/api";
    private static Response response;
    private static JSONObject requestParams;
    private static int getUserId;

    // get users
    @Given("eu faço uma requisição get ao endpoint {string}")
    public void eu_faco_uma_requisicao_ao_endpoint(String string) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get(string);
    }
    @Then("o endpoint retorna o status {int}")
    public void o_endpoint_retorna_o_status(Integer int1) {
        Assertions.assertEquals(int1, response.statusCode());
    }
    @Then("lista todos os {int} usuários da página")
    public void lista_todos_os_usuarios_da_pagina(Integer int1) {
        List<Map<String, String>> users = response.jsonPath().getList("data");
        Assertions.assertTrue(users.size() == int1);
        Assertions.assertEquals(int1.toString(), response.jsonPath().getString("per_page"));
    }

    //post users
    @Given("eu informo os dados do usuário a ser persistido")
    public void eu_faco_uma_requisicao_post_ao_endpoint() {
        requestParams = new JSONObject();
        requestParams.put("name", "Morpheus");
        requestParams.put("job", "leader");
    }
    @When("e faço uma requisição post ao endpoint {string}")
    public void eu_informo_os_dados_do_usuario_a_ser_persisitido(String string) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .post(string);
    }
    @Then("o endpoint retorna o status {int} e persisite o usuário com sucesso")
    public void o_endpoint_retorna_o_status_e_persisite_o_usuario_com_sucesso(Integer int1) {
        Assertions.assertEquals(int1, response.statusCode());
        Assertions.assertEquals("Morpheus", response.jsonPath().getString("name"));
        Assertions.assertEquals("leader", response.jsonPath().getString("job"));
        // guardar o id do usuário para atualizá-lo e deletá-lo posteriormente
        getUserId = response.jsonPath().getInt("id");
    }

    //put user
    @Given("eu informo os dados do usuário a serem atualizados")
    public void eu_informo_os_dados_do_usuario_a_serem_atualizados() {
        requestParams = new JSONObject();
        requestParams.put("name", "Morpheus Billy");
        requestParams.put("job", "manager");
    }
    @When("e faço uma requisição put ao endpoint {string} + userId")
    public void e_faço_uma_requisicao_put_ao_endpoint_user_id(String string) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .put(string + getUserId);
    }
    @Then("o endpoint retorna o status {int} e atualiza todos os dados do usuário com sucesso")
    public void o_endpoint_retorna_o_status_e_atualiza_o_usuario_com_sucesso(Integer int1) {
        Assertions.assertEquals(int1, response.statusCode());
        Assertions.assertEquals("Morpheus Billy", response.jsonPath().getString("name"));
        Assertions.assertEquals("manager", response.jsonPath().getString("job"));
    }

    // patch user
    @Given("eu informo o novo cargo do usuário a ser atualizado")
    public void eu_informo_o_novo_cargo_do_usuario_a_ser_atualizado() {
        requestParams = new JSONObject();
        requestParams.put("job", "HR");
    }
    @When("e faço uma requisição patch ao endpoint {string} + userId")
    public void e_faço_uma_requisicao_patch_ao_endpoint_user_id(String string) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .patch(string + getUserId);
    }
    @Then("o endpoint retorna o status {int} e atualiza o cargo do usuário com sucesso")
    public void o_endpoint_retorna_o_status_e_atualiza_o_cargo_do_usuario_com_sucesso(Integer int1) {
        Assertions.assertEquals(int1, response.statusCode());
        Assertions.assertEquals("HR", response.jsonPath().getString("job"));
    }

    // delete user
    @Given("eu faço uma requisição delete ao endpoint {string} + userId")
    public void eu_faço_uma_requisição_delete_ao_endpoint_user_id(String string) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request
                .contentType(ContentType.JSON)
                .delete(string + getUserId);
    }
    @Then("o endpoint retorna o status {int} e deleta o usuário com sucesso")
    public void o_endpoint_retorna_o_status_e_deleta_o_usuário_com_sucesso(Integer int1) {
        Assertions.assertEquals(204, response.statusCode());
    }

}
