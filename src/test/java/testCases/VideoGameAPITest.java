package testCases;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;
public class VideoGameAPITest {
	@Test(priority = 1)
	public void test_getAllVideoGames() {
		given().when()
		.get("http://localhost:8080/app/videogames")
		.then().statusCode(200);
	}
	
	@Test(priority = 2)
	public void test_createNewVideoGame() {
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Suraj");
		data.put("releaseDate", "2022-08-07T16:07:39.472Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response res = given().contentType("application/json").body(data).when().
		post("http://localhost:8080/app/videogames")
		.then().statusCode(200).log().body()
		.extract().response();
		//getting the response string
		String jsonString  = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"),true);
	}
  @Test(priority = 3)
	public void test_getVideoGame() {
		given().when()
		.get("http://localhost:8080/app/videogames/100").then()
		.statusCode(200)
		.log().body() //prints the response body
		.body("videoGame.id",equalTo("100"))
		.body("videoGame.name",equalTo("Suraj"));
	}
	
	//http://localhost:8080/app/videogames/2
	
	@Test(priority = 4)
	public void test_UpdateVideoGame() {
		HashMap data = new HashMap();
		data.put("id", "100");
		data.put("name", "Sagar");
		data.put("releaseDate", "2022-08-07T16:07:39.472Z");
		data.put("reviewScore", "4");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		given().contentType("application/json").body(data)
		.when().put("http://localhost:8080/app/videogames/100")
		.then().statusCode(200)
		.log().body()
		.body("videoGame.id",equalTo("100"))
		.body("videoGame.name",equalTo("Sagar"));
	}
	@Test(priority = 5)
	public void test_deleteVideoGame() throws InterruptedException {
		//http://localhost:8080/app/videogames/3
		
		Response response = given().when().delete("http://localhost:8080/app/videogames/3")
		.then().statusCode(200).log().body()
		.extract().response();
		Thread.sleep(3000);
		String jsonString = response.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	}

}


//{
//	  "id": 0,
//	  "name": "string",
//	  "releaseDate": "2022-08-07T16:07:39.472Z",
//	  "reviewScore": 0,
//	  "category": "string",
//	  "rating": "string"
//	}