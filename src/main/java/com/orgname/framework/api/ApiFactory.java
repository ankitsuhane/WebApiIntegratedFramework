package com.orgname.framework.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiFactory {

    public Response getMethod(String path, String queryParam){
        return RestAssured
                .given()
                .when()
                .get(path+queryParam);
    }
    public String printOutput(Response response){
        return response.then().extract().response().prettyPrint();
    }

    public Response postMethod(String path, String requestBody) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(path);
    }
}
