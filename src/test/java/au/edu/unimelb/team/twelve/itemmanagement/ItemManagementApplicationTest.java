package au.edu.unimelb.team.twelve.itemmanagement;

import au.edu.unimelb.team.twelve.itemmanagement.controllers.UserController;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Category;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Storage;
import au.edu.unimelb.team.twelve.itemmanagement.repositories.*;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemManagementApplicationTest {
    @Autowired
    UserRepository users;
    @Autowired
    ItemRepository items;
    @Autowired
    BookRepository books;
    @Autowired
    TagRepository tags;
    @Autowired
    CategoryRepository categories;
    @Autowired
    StorageRepository storages;

    @Autowired
    UserController userController;
    @Autowired
    MockMvc mvc;

    @Test
    void contextLoads() {
    }

    @Test
    void userTests() {
        userController.register("Test", "PaSsWoRd");
        var user = users.exactMatch("Test");
        Assertions.assertNotNull(user);
        Assertions.assertNull(users.exactMatch("TestNull"));
        Assertions.assertEquals(user.getName(), users.fromToken(user.getToken()).getName());
        Assertions.assertTrue(userController.login("TestNull", "PaSsWoRd").contents.toString().contains("does not " + "exist"));
        Assertions.assertTrue(userController.login("Test", "Password").contents.toString().contains("incorrect"));
        var token = userController.login("Test", "PaSsWoRd").contents.toString();
        Assertions.assertNull(users.fromToken(user.getToken()));
        Assertions.assertEquals(user.getName(), users.fromToken(token).getName());
    }

    String users() throws Exception {
        RequestBuilder request;
        request = get("/user/register?name=test&password=pass");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/register?name=&password=");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":false")));
        request = get("/user/login?name=test&password=pass");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/login?name=&password=pass");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":false")));
        request = get("/user/login?name=test&password=");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":false")));
        request = get("/user/login?name=&password=");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":false")));
        request = get("/user/login?name=test&password=pass");
        var result = new JSONObject(mvc.perform(request).andReturn().getResponse().getContentAsString());
        var token = result.getString("contents");
        request = get("/user/logout?token=" + token);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/logout?token=" + token);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":false")));
        request = get("/user/login?name=test&password=pass");
        result = new JSONObject(mvc.perform(request).andReturn().getResponse().getContentAsString());
        token = result.getString("contents");
        Assertions.assertTrue(users.fromToken(token).isNewComer());
        request = get("/user/came?token=" + token);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        Assertions.assertFalse(users.fromToken(token).isNewComer());
        // 2.1 - 2.4
        return token;
    }

    void metas(String token) throws Exception {
        var request = get("/user/tags/add?token=" + token + "&name=TagA");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/tags/add?token=" + token + "&name=TagB");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/tags/add?token=" + token + "&name=TagC");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/categories/add?token=" + token + "&name=CategoryA&description=Something not important");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/storages/add?token=" + token + "&location=on my back");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        // 2.21 - 2.23
    }

    void items(String token, Storage storage, UUID tid0, UUID tid2) throws Exception {

        var request =
                get("/user/items/add?token=" + token + "&name=ItemA&description=ItemDescription&storageId=" + storage.getId());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        // 2.17

        request =
                get("/user/items/add?token=" + token + "&name=ItemA&description=ItemDescription&storageId=" + storage.getId());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));

        request = get("/user/items/all?token=" + token);
        var results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        var result = results.getJSONObject(0);
        var uuid = result.getString("id");
        Assertions.assertEquals(result.getString("name"), "ItemA");
        // Get one item, for remove test

        request = get("/user/favorites/add?token=" + token + "&uuid=" + uuid);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/items/delete?token=" + token + "&id=" + uuid);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));


        request = get("/user/items/all?token=" + token);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        result = results.getJSONObject(0);
        uuid = result.getString("id");
        Assertions.assertEquals(result.getString("name"), "ItemA");
        // 2.8

        request =
                get("/user/items/update?token=" + token + "&id=" + uuid + "&name=ItemB&description=ItemDescription" + "&storageId=" + storage.getId() + "&tagIds=" + tid0 + "," + tid2 + "&sharedIds=" + users.findByName("test2").iterator().next().getId());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/items/all?token=" + token);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        result = results.getJSONObject(0);
        Assertions.assertEquals(result.getString("name"), "ItemB");
        Assertions.assertEquals(2, result.getJSONArray("tagIds").length());
        // 2.18

        request = get("/user/items/all?token=" + users.findByName("test2").iterator().next().getToken());
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        result = results.getJSONObject(0);
        Assertions.assertEquals(result.getString("name"), "ItemB");
        Assertions.assertEquals(2, result.getJSONArray("tagIds").length());

        Assertions.assertTrue(items.findAll().iterator().hasNext());
        request = get("/user/items/search/and?token=" + token + "&tag=" + tid0);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        Assertions.assertTrue(items.findAll().iterator().hasNext());
        request = get("/user/items/search/and?token=" + token + "&name=ItemB");
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        request = get("/user/items/search/and?token=" + token + "&description=nOtExIs&name=ItemB");
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(0, results.length());
        request = get("/user/items/search/or?token=" + token + "&description=somethingNotExist&name=ItemB");
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        // 2.10 & 2.11
    }

    void books(String token, Category category, Storage storage, UUID tid0, UUID tid1, UUID tid2) throws Exception {

        var request =
                get("/user/books/add?token=" + token + "&author=a&publisher=a&categoryIds=" + category.getId() +
                        "&publishedDate=2020/2/1" +
                        "&isbn=123456test&name=BookA&description=ItemDescription&storageId=" + storage.getId());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        // 2.19

        request =
                get("/user/books/add?token=" + token + "&author=a&publisher=a&categoryIds=" + category.getId() +
                        "&publishedDate=2020/2/1" +
                        "&isbn=123456test&name=BookA&description=ItemDescription&storageId=" + storage.getId());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));

        request = get("/user/books/all?token=" + token);
        var results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        var result = results.getJSONObject(0);
        var uuid = result.getString("id");
        Assertions.assertEquals(result.getString("name"), "BookA");
        // Get one book, for remove test

        request = get("/user/favorites/add?token=" + token + "&uuid=" + uuid);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/books/delete?token=" + token + "&id=" + uuid);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));

        request = get("/user/books/all?token=" + token);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        result = results.getJSONObject(0);
        uuid = result.getString("id");
        Assertions.assertEquals(result.getString("name"), "BookA");
        // 2.9

        request =
                get("/user/books/update?token=" + token + "&id=" + uuid + "&author=a&publisher=a&categoryIds=" + category.getId() +
                        "&isbn=test123456&name=BookB&description=ItemDescription&storageId=" + storage.getId() +
                        "&tagIds=" + tid0 + "," + tid2);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/books/all?token=" + token);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        result = results.getJSONObject(0);
        Assertions.assertEquals(result.getString("name"), "BookB");
        Assertions.assertEquals(2, result.getJSONArray("tagIds").length());
        // 2.20


        Assertions.assertTrue(items.findAll().iterator().hasNext());
        request = get("/user/books/search/and?token=" + token + "&category=" + category.getId());
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        Assertions.assertTrue(items.findAll().iterator().hasNext());
        request = get("/user/books/search/and?token=" + token + "&tag=" + tid0 + "&category=" + category.getId());
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        Assertions.assertTrue(items.findAll().iterator().hasNext());
        request = get("/user/books/search/and?token=" + token + "&name=BookB&isbn=test123456");
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        request = get("/user/books/search/and?token=" + token + "&description=nOtExIs&name=BookB");
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(0, results.length());
        request = get("/user/books/search/or?token=" + token + "&description=somethingNotExist&name=BookB&tag=" + tid1);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        // 2.12 & 2.13
    }

    void favori(String token) throws Exception {
        var request = get("/user/favorites/items?token=" + token);
        var results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(0, results.length());
        request = get("/user/favorites/add?token=" + token + "&uuid=" + books.findAll().iterator().next().getId());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/favorites/books?token=" + token);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        request = get("/user/favorites/items?token=" + token);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        request = get("/user/favorites/add?token=" + token + "&uuid=" + items.findAll().iterator().next().getId());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/favorites/items?token=" + token);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(2, results.length());
        request = get("/user/favorites/remove?token=" + token + "&uuid=" + books.findAll().iterator().next().getId());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/favorites/items?token=" + token);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(1, results.length());
        request = get("/user/favorites/books?token=" + token);
        results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals(0, results.length());
    }

    @Test
    void apis() throws Exception {
        var token = users();
        var request = get("/user/info?token=" + token);
        var result = new JSONObject(mvc.perform(request).andReturn().getResponse().getContentAsString());
        result = result.getJSONObject("contents");
        var name = result.getString("name");
        var uuid = result.getString("uuid");
        Assertions.assertEquals(name, "test");
        Assertions.assertNotEquals(name, "tEsT");
        // 2.6
        metas(token);

        request = get("/user/search?name=tt");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("[]"));
        request = get("/user/search?name=te");
        var results = new JSONArray(mvc.perform(request).andReturn().getResponse().getContentAsString());
        result = results.getJSONObject(0);
        Assertions.assertEquals(result.getString("uuid"), uuid);
        // 2.7


        var storage = storages.findAll().iterator().next();
        Assertions.assertNotNull(storage);
        var category = categories.findAll().iterator().next();
        Assertions.assertNotNull(category);

        var allTags = StreamSupport.stream(tags.findAll().spliterator(), false).collect(Collectors.toList());
        var tid0 = allTags.get(0).getId();
        var tid1 = allTags.get(1).getId();
        var tid2 = allTags.get(2).getId();

        request = get("/user/register?name=test2&password=pass");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/info/get?token=" + token + "&uuid=" + users.findByName("test2").iterator().next().getId());
        result = new JSONObject(mvc.perform(request).andReturn().getResponse().getContentAsString());
        Assertions.assertEquals("test2", result.getJSONObject("contents").getString("name"));

        items(token, storage, tid0, tid2);
        books(token, category, storage, tid0, tid1, tid2);
        favori(token);

        request = get("/user/info/set?token=" + token + "&avatar=testAvatar&password=");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        Assertions.assertEquals(users.fromToken(token).getAvatar(), "testAvatar");

        request = get("/user/login?name=test&password=pass");
        result = new JSONObject(mvc.perform(request).andReturn().getResponse().getContentAsString());
        token = result.getString("contents");

        request = get("/user/delete?token=" + users.findByName("test2").iterator().next().getToken());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        Assertions.assertTrue(items.findAll().iterator().hasNext());
        request = get("/user/delete?token=" + token);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":true")));
        request = get("/user/login?name=test&password=pass");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("\"success"
                + "\":false")));
        Assertions.assertFalse(items.findAll().iterator().hasNext());
        // 2.5


    }
}
