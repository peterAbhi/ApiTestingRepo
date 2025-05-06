import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class ReqResApiTesting {

    @Test
    public void getRequest(){
        baseURI="https://reqres.in/api/";
        Integer code = get("users?page=2").getStatusCode();
        System.out.println(code);
    }

    //Write Code using Rest Assured API to verify API Response Code 200.
    @Test
    public void testResponseCode200(){
        baseURI="https://reqres.in/api/";
        given().
            when().
                get("users?page=2").
            then().
                assertThat().
                statusCode(200);
    }

    //Write Code using Rest Assured API to verify API Response Code 404
    @Test
    public void testResponseCode401(){
        baseURI="https://reqres.in/api/";
        given().
                when().
                    get("users/23").
                then().
                    assertThat().
                    statusCode(401);
    }

    //Write Code using Rest Assured API to verify Content Type of API response
    @Test
    public void testContentTypeAsJson(){
        baseURI="https://reqres.in/api/";
        given().
                when().
                    get("users?page=2").
                then().
                    assertThat().
                    contentType(equalTo("application/json; charset=utf-8"));
    }

//    TC_GetUsersFirstNames() : To extract all the Status code and total no of id and extract all first_names from page2.
    @Test
    public void getUsersFirstNames(){
        baseURI="https://reqres.in/api/";
//       get the responses in json format in the response variable
        Response response = given().
                when().get("users?page=2");
//       get the list of usernames as List<String> using the above responses
        List<String> userNames = new ArrayList<>();
        userNames = response.jsonPath().getList("data.first_name");
        for(String user:userNames){
            System.out.println(user);
        }
    }

//    TC_ CheckUserInputName(): To accept random first_name from user to check if its in the API’s Page2 and index no.
    @Test
    public void checkUserInputName(){
        baseURI="https://reqres.in/api/";
        Response response = given().
                when().get("users?page=2");

//        Create the list of user first names
        List<String> firstNames = response.jsonPath().getList("data.first_name");
        Assert.assertTrue(firstNames.contains("Lindsay"));
    }

//    TC_GetResourceRequest(): This method shows the data like name,color,pantone_value which is greater than year 2001.
    @Test
    public void getResourceRequest(){
        baseURI="https://reqres.in/api/";
        Response response = given().
                when().
                get("unknown");

        List<Integer> responseData = new ArrayList<>();
        responseData = response.jsonPath().getList("data.year");

        for(int i=0;i<responseData.size();i++){
            if(responseData.get(i)>=2001){
                System.out.println(response.jsonPath().getString(String.format("data[%d].name",i)));
                System.out.println(response.jsonPath().getString(String.format("data[%d].color",i)));
                System.out.println(response.jsonPath().getString(String.format("data[%d].pantone_value",i)));
                System.out.println();
            }
        }
    }

//    TC_GetEmailId: Get email id of the user with first name Lindsay
    @Test
    public void getEmailId(){
        baseURI = "https://reqres.in/api/";
        Response response = given().when().get("users?page=2");
        List<String> firstNames = new ArrayList<>();
        firstNames = response.jsonPath().getList("data.first_name");
        for(int i=0;i<firstNames.size();i++){
            if(firstNames.get(i).equalsIgnoreCase("Lindsay")){
                String str =  response.jsonPath().getString(String.format("data[%d].email",i));
                System.out.println(str);
            }
        }
    }
}
