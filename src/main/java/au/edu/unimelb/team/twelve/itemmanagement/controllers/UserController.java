package au.edu.unimelb.team.twelve.itemmanagement.controllers;

import au.edu.unimelb.team.twelve.itemmanagement.ArgumentResponse;
import au.edu.unimelb.team.twelve.itemmanagement.AuthedUser;
import au.edu.unimelb.team.twelve.itemmanagement.GenericResponse;
import au.edu.unimelb.team.twelve.itemmanagement.Utils;
import au.edu.unimelb.team.twelve.itemmanagement.dto.BookDTO;
import au.edu.unimelb.team.twelve.itemmanagement.dto.ItemDTO;
import au.edu.unimelb.team.twelve.itemmanagement.dto.UserDTO;
import au.edu.unimelb.team.twelve.itemmanagement.entities.*;
import au.edu.unimelb.team.twelve.itemmanagement.repositories.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * User Related Apis
 */
@RestController
public class UserController {
    private final UserRepository users;
    private final ItemRepository items;
    private final BookRepository books;
    private final TagRepository tags;
    private final CategoryRepository categories;
    private final StorageRepository storages;

    public UserController(UserRepository users, ItemRepository items, BookRepository books, TagRepository tags,
                          CategoryRepository categories, StorageRepository storages) {
        this.users = users;
        this.items = items;
        this.books = books;
        this.tags = tags;
        this.categories = categories;
        this.storages = storages;
    }

    /**
     * Register
     *
     * @param name     username
     * @param password SHA256ed password, will be stored with salted SHA256
     * @return if successful registered
     */
    @RequestMapping("/register")
    public GenericResponse register(@RequestParam(value = "name", defaultValue = "") String name,
                                    @RequestParam(value = "password", defaultValue = "") String password) {
        if (name.isEmpty() || password.isEmpty()) {
            return new GenericResponse(false, "name or password can not be empty.");
        }
        if (users.exactMatch(name) != null) {
            return new GenericResponse(false, "user already exist.");
        }
        var user = new BookUser();
        user.setName(name);
        user.setSalt(RandomString.make());
        user.setPassword(Utils.sha256(password + user.getSalt()));
        user.setNewComer(true);
        user.setToken(RandomString.make(128));
        users.save(user);
        return new GenericResponse(true, user.getToken());
    }

    /**
     * Login
     *
     * @param name     username
     * @param password SHA256ed password
     * @return token, if successful logged in
     */
    @RequestMapping("/login")
    public GenericResponse login(@RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value =
            "password", defaultValue = "") String password) {
        var user = users.exactMatch(name);
        if (user == null) {
            return new GenericResponse(false, "user does not exist.");
        }
        if (!user.getPassword().equals(Utils.sha256(password + user.getSalt()))) {
            return new GenericResponse(false, "incorrect password.");
        }
        user = users.findById(user.getId()).orElseThrow();
        user.setToken(RandomString.make(128));
        users.save(user);
        return new GenericResponse(true, user.getToken());
    }

    /**
     * Logout
     *
     * @param token User token|java.lang.String
     * @return if the operation succeed
     */
    @RequestMapping("/logout")
    public GenericResponse logout(@AuthedUser BookUser token) {
        if (token == null) {
            return new GenericResponse(false);
        }
        token = users.findById(token.getId()).orElseThrow();
        token.setToken(null);
        users.save(token);
        return new GenericResponse(true);
    }

    /**
     * Remove Newcomer Flag
     *
     * @param token User token|java.lang.String
     * @return if the operation succeed
     */
    @RequestMapping("/came")
    public GenericResponse came(@AuthedUser BookUser token) {
        if (token == null) {
            return new GenericResponse(false);
        }
        token = users.findById(token.getId()).orElseThrow();
        token.setNewComer(false);
        users.save(token);
        return new GenericResponse(true);
    }

    /**
     * Delete User
     *
     * @param token User token|java.lang.String
     * @return if the operation succeed
     */
    @RequestMapping("/delete")
    public GenericResponse delete(@AuthedUser BookUser token) {
        if (token == null) {
            return new GenericResponse(false);
        }
        token = users.findById(token.getId()).orElseThrow();
        token.getFavorites().clear();
        users.save(token);
        BookUser finalToken = token;
        books.listAllSharedWith(token).forEach(b -> {
            b.getShared().remove(finalToken);
            books.save(b);
        });
        items.listAllSharedWith(token).forEach(i -> {
            i.getShared().remove(finalToken);
            items.save(i);
        });
        books.deleteAllByOwner(token);
        items.deleteAllByOwner(token);
        users.delete(token);
        return new GenericResponse(true);
    }

    /**
     * Retrieve Info
     *
     * @param token User token|java.lang.String
     * @return current user's basic information
     */
    @RequestMapping("/info")
    public ArgumentResponse<UserDTO> info(@AuthedUser BookUser token) {
        if (token == null) {
            return new ArgumentResponse<>(false);
        }
        return new ArgumentResponse<>(true, UserDTO.fromUser(token));
    }

    /**
     * Retrieve Others' Info
     *
     * @param token User token|java.lang.String
     * @return current user's basic information
     */
    @RequestMapping("/info/get")
    public ArgumentResponse<UserDTO> info(@AuthedUser BookUser token, String uuid) {
        try {
            var other = users.findById(UUID.fromString(uuid));
            if (token == null || other.isEmpty()) {
                return new ArgumentResponse<>(false);
            }
            return new ArgumentResponse<>(true, UserDTO.fromUser(other.get()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new ArgumentResponse<>(false);
    }

    /**
     * Retrieve Others' Info
     *
     * @param token User token|java.lang.String
     * @return current user's basic information
     */
    @RequestMapping("/info/set")
    public GenericResponse info(@AuthedUser BookUser token, UserDTO info,
                                @RequestParam(defaultValue = "") String password) {
        try {
            if (token == null) {
                return new GenericResponse(false);
            }
            token = users.findById(token.getId()).orElseThrow();
            if (info.getAvatar() != null && !info.getAvatar().isEmpty()) {
                token.setAvatar(info.getAvatar());
            }
            if (info.getProfile() != null && !info.getProfile().isEmpty()) {
                token.setAvatar(info.getProfile());
            }
            if (!password.isEmpty()) {
                token.setSalt(RandomString.make());
                token.setPassword(Utils.sha256(password + token.getSalt()));
            }
            users.save(token);
            return new GenericResponse(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new GenericResponse(false);
    }

    /**
     * Search User By Name
     *
     * @param name target user's name
     * @return list of possible users
     */
    @RequestMapping("/search")
    public Iterable<UserDTO> search(@RequestParam(value = "name", defaultValue = "") String name) {
        return StreamSupport.stream(users.findByName(name).spliterator(), false).map(UserDTO::fromUser).collect(Collectors.toList());
    }

    /**
     * List Items
     *
     * @param token User token|java.lang.String
     * @return items owned by the user
     */
    @RequestMapping("/items/all")
    public Iterable<ItemDTO> items(@AuthedUser BookUser token) {
        return ItemDTO.fromItem(items.searchAllByOwner(users.findById(token.getId()).orElseThrow()));
    }

    /**
     * List Books
     *
     * @param token User token|java.lang.String
     * @return books owned by the user
     */
    @RequestMapping("/books/all")
    public Iterable<BookDTO> books(@AuthedUser BookUser token) {
        return BookDTO.fromItem(books.searchAllByOwner(users.findById(token.getId()).orElseThrow()));
    }

    /**
     * Search Items (And)
     *
     * @param token       User token|java.lang.String
     * @param location    where it stores
     * @param name        item's name
     * @param description item's description
     * @param tag         item's tag|UUID[]
     * @return items that with all attributes the same
     */
    @RequestMapping("/items/search/and")
    public Iterable<ItemDTO> itemsSearchAnd(@AuthedUser BookUser token,
                                            @RequestParam(defaultValue = "") String location,
                                            @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue
            = "") String description, @RequestParam(defaultValue = "") String tag) {
        Iterable<Item> item;
        if (tag.isEmpty()) {
            item = items.searchAllByOwnerAnd(users.findById(token.getId()).orElseThrow(), location, name, description);
        } else {
            item = items.searchAllByOwnerAnd(users.findById(token.getId()).orElseThrow(), location, name, description
                    , tags.findById(UUID.fromString(tag)).orElseThrow());
        }
        return ItemDTO.fromItem(item);
    }

    /**
     * Search Items (Or)
     *
     * @param token User token|java.lang.String
     * @return items that with some attributes the same
     */
    @RequestMapping("/items/search/or")
    public Iterable<ItemDTO> itemsSearchOr(@AuthedUser BookUser token,
                                           @RequestParam(defaultValue = "") String location,
                                           @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue =
            "") String description, @RequestParam(defaultValue = "") String tag) {
        Iterable<Item> item;
        if (tag.isEmpty()) {
            item = items.searchAllByOwnerOr(users.findById(token.getId()).orElseThrow(), location, name, description);
        } else {
            item = items.searchAllByOwnerOr(users.findById(token.getId()).orElseThrow(), location, name, description,
                    tags.findById(UUID.fromString(tag)).orElseThrow());
        }
        return ItemDTO.fromItem(item);
    }

    /**
     * Search Books (And)
     *
     * @param token       User token|java.lang.String
     * @param location    where it stores
     * @param name        item's name
     * @param description item's description
     * @param tag         item's tag|UUID[]
     * @param isbn        book's isbn
     * @param author      book's author
     * @param publisher   book's publisher
     * @param category    book's category
     * @return books that with all attributes the same
     */
    @RequestMapping("/books/search/and")
    public Iterable<BookDTO> booksSearchAnd(@AuthedUser BookUser token,
                                            @RequestParam(defaultValue = "") String location,
                                            @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue
            = "") String description, @RequestParam(defaultValue = "") String tag,
                                            @RequestParam(defaultValue = "") String isbn, @RequestParam(defaultValue
            = "") String author, @RequestParam(defaultValue = "") String publisher,
                                            @RequestParam(defaultValue = "") String category) {
        Iterable<Book> item;
        if (tag.isEmpty() && category.isEmpty()) {
            item = books.searchAllByOwnerAnd(users.findById(token.getId()).orElseThrow(), location, name, description
                    , isbn, author, publisher);
        } else if (tag.isEmpty()) {
            item = books.searchAllByOwnerAnd(users.findById(token.getId()).orElseThrow(), location, name, description
                    , isbn, author, publisher, categories.findById(UUID.fromString(category)).orElseThrow());
        } else if (category.isEmpty()) {
            item = books.searchAllByOwnerAnd(users.findById(token.getId()).orElseThrow(), location, name, description
                    , isbn, author, publisher, tags.findById(UUID.fromString(tag)).orElseThrow());
        } else {
            item = books.searchAllByOwnerAnd(users.findById(token.getId()).orElseThrow(), location, name, description
                    , isbn, author, publisher, tags.findById(UUID.fromString(tag)).orElseThrow(),
                    categories.findById(UUID.fromString(category)).orElseThrow());
        }
        return BookDTO.fromItem(item);
    }

    /**
     * Search Books (Or)
     *
     * @param token       User token|java.lang.String
     * @param location    where it stores
     * @param name        item's name
     * @param description item's description
     * @param tag         item's tags|UUID[]
     * @param isbn        book's isbn
     * @param author      book's author
     * @param publisher   book's publisher
     * @param category    book's category
     * @return books that with some attributes the same
     */
    @RequestMapping("/books/search/or")
    public Iterable<BookDTO> booksSearchOr(@AuthedUser BookUser token,
                                           @RequestParam(defaultValue = "") String location,
                                           @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue =
            "") String description, @RequestParam String tag, @RequestParam(defaultValue = "") String isbn,
                                           @RequestParam(defaultValue = "") String author,
                                           @RequestParam(defaultValue = "") String publisher,
                                           @RequestParam(defaultValue = "") String category) {
        Iterable<Book> item;
        if (tag.isEmpty() && category.isEmpty()) {
            item = books.searchAllByOwnerOr(users.findById(token.getId()).orElseThrow(), location, name, description,
                    isbn, author, publisher);
        } else if (tag.isEmpty()) {
            item = books.searchAllByOwnerOr(users.findById(token.getId()).orElseThrow(), location, name, description,
                    isbn, author, publisher, categories.findById(UUID.fromString(category)).orElseThrow());
        } else if (category.isEmpty()) {
            item = books.searchAllByOwnerOr(users.findById(token.getId()).orElseThrow(), location, name, description,
                    isbn, author, publisher, tags.findById(UUID.fromString(tag)).orElseThrow());
        } else {
            item = books.searchAllByOwnerOr(users.findById(token.getId()).orElseThrow(), location, name, description,
                    isbn, author, publisher, tags.findById(UUID.fromString(tag)).orElseThrow(),
                    categories.findById(UUID.fromString(category)).orElseThrow());
        }
        return BookDTO.fromItem(item);
    }

    /**
     * List Favorite Items
     *
     * @param token User token|java.lang.String
     * @return favorite items
     */
    @RequestMapping("/favorites/items")
    public Iterable<ItemDTO> favoriteItems(@AuthedUser BookUser token) {
        return ItemDTO.fromItem(token.getFavorites());
    }

    /**
     * List Favorite Books
     *
     * @param token User token|java.lang.String
     * @return favorite books
     */
    @RequestMapping("/favorites/books")
    public Iterable<BookDTO> favoriteBooks(@AuthedUser BookUser token) {
        return BookDTO.fromGenericItem(token.getFavorites().stream());
    }

    /**
     * Add favorite item
     *
     * @param token User token|java.lang.String
     * @param uuid  item's uuid
     * @return if the operation succeed
     */
    @RequestMapping("/favorites/add")
    public GenericResponse addFavorite(@AuthedUser BookUser token, @RequestParam String uuid) {
        try {
            var id = UUID.fromString(uuid);
            var item = items.findById(id);
            if (item.isPresent()) {
                token = users.findById(token.getId()).orElseThrow();
                token.getFavorites().add(item.get());
                users.save(token);
                return new GenericResponse(true);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new GenericResponse(false);
    }

    /**
     * Remove favorite item
     *
     * @param token User token|java.lang.String
     * @param uuid  item's uuid
     * @return if the operation succeed
     */
    @RequestMapping("/favorites/remove")
    public GenericResponse removeFavorite(@AuthedUser BookUser token, @RequestParam String uuid) {
        try {
            var id = UUID.fromString(uuid);
            var item = items.findById(id);
            if (item.isPresent()) {
                token = users.findById(token.getId()).orElseThrow();
                token.getFavorites().remove(item.get());
                users.save(token);
                return new GenericResponse(true);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new GenericResponse(false);
    }

    /**
     * Add New Item
     *
     * @param token User token|java.lang.String
     * @param item  item's DTO, owner and id will be ignored
     * @return if the operation succeed
     */
    @RequestMapping("/items/add")
    public GenericResponse addItem(@AuthedUser BookUser token, ItemDTO item) {
        var created = new Item();
        item.fillSets();
        created.setOwner(users.findById(token.getId()).orElseThrow());
        created.setDescription(item.getDescription());
        created.setName(item.getName());
        created.setTags(item.getTagIds().stream().map(t -> tags.findById(t).orElseThrow()).collect(Collectors.toSet()));
        created.setShared(item.getSharedIds().stream().map(t -> users.findById(t).orElseThrow()).collect(Collectors.toSet()));
        created.setPlace(storages.findById(item.getStorageId()).orElseThrow());
        items.save(created);
        return new GenericResponse(true);
    }


    /**
     * Update Exist Item
     *
     * @param token User token|java.lang.String
     * @param item  item's DTO, owner and id will be ignored
     * @return if the operation succeed, false if not exist yet
     */
    @RequestMapping("/items/update")
    public GenericResponse updateItem(@AuthedUser BookUser token, ItemDTO item) {
        try {
            var id = item.getId();
            var changed = items.findById(id).orElseThrow();
            if (!changed.getOwner().getId().equals(token.getId())) {
                return new GenericResponse(false, "not the owner");
            }
            item.fillSets();
            changed.setDescription(item.getDescription());
            changed.setName(item.getName());
            changed.setTags(item.getTagIds().stream().map(t -> tags.findById(t).orElseThrow()).collect(Collectors.toSet()));
            changed.setShared(item.getSharedIds().stream().map(t -> users.findById(t).orElseThrow()).collect(Collectors.toSet()));
            changed.setPlace(storages.findById(item.getStorageId()).orElseThrow());
            items.save(changed);
            return new GenericResponse(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new GenericResponse(false);
    }

    /**
     * Delete an item
     *
     * @param token User token|java.lang.String
     * @param id  item's id
     * @return if the operation succeed
     */
    @RequestMapping("/items/delete")
    public GenericResponse deleteItem(@AuthedUser BookUser token, String id) {
        try {
            var uuid = UUID.fromString(id);
            var changed = items.findById(uuid).orElseThrow();
            if (!changed.getOwner().getId().equals(token.getId())) {
                return new GenericResponse(false, "not the owner");
            }
            users.findAll().forEach(u -> u.getFavorites().removeIf(i -> i.getId() == changed.getId()));
            items.deleteById(uuid);
            return new GenericResponse(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new GenericResponse(false);
    }


    /**
     * Add New Book
     *
     * @param token User token|java.lang.String
     * @param book  item's DTO, owner and id will be ignored
     * @return if the operation succeed
     */
    @RequestMapping("/books/add")
    public GenericResponse addBook(@AuthedUser BookUser token, BookDTO book) {
        var created = new Book();
        book.fillSets();
        created.setOwner(users.findById(token.getId()).orElseThrow());
        created.setDescription(book.getDescription());
        created.setName(book.getName());
        created.setTags(book.getTagIds().stream().map(t -> tags.findById(t).orElseThrow()).collect(Collectors.toSet()));
        created.setShared(book.getSharedIds().stream().map(t -> users.findById(t).orElseThrow()).collect(Collectors.toSet()));
        created.setPlace(storages.findById(book.getStorageId()).orElseThrow());
        created.setPublisher(book.getPublisher());
        created.setIsbn(book.getIsbn());
        created.setAuthor(book.getAuthor());
        created.setPublishedDate(book.getPublishedDate());
        created.setCategories(book.getCategoryIds().stream().map(t -> categories.findById(t).orElseThrow()).collect(Collectors.toSet()));
        items.save(created);
        return new GenericResponse(true);
    }

    /**
     * Update Exist Book
     *
     * @param token User token|java.lang.String
     * @param book  item's DTO, owner and id will be ignored
     * @return if the operation succeed, false if not exist yet
     */
    @RequestMapping("/books/update")
    public GenericResponse updateBook(@AuthedUser BookUser token, BookDTO book) {
        try {
            var id = book.getId();
            var changed = books.findById(id).orElseThrow();
            if (!changed.getOwner().getId().equals(token.getId())) {
                return new GenericResponse(false, "not the owner");
            }
            book.fillSets();
            changed.setDescription(book.getDescription());
            changed.setName(book.getName());
            changed.setTags(book.getTagIds().stream().map(t -> tags.findById(t).orElseThrow()).collect(Collectors.toSet()));
            changed.setShared(book.getSharedIds().stream().map(t -> users.findById(t).orElseThrow()).collect(Collectors.toSet()));
            changed.setPlace(storages.findById(book.getStorageId()).orElseThrow());
            changed.setPublisher(book.getPublisher());
            changed.setIsbn(book.getIsbn());
            changed.setAuthor(book.getAuthor());
            changed.setPublishedDate(book.getPublishedDate());
            changed.setCategories(book.getCategoryIds().stream().map(t -> categories.findById(t).orElseThrow()).collect(Collectors.toSet()));
            books.save(changed);
            return new GenericResponse(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new GenericResponse(false);
    }


    /**
     * Delete a Book
     *
     * @param token User token|java.lang.String
     * @param id    book's id
     * @return if the operation succeed
     */
    @RequestMapping("/books/delete")
    public GenericResponse deleteBook(@AuthedUser BookUser token, String id) {
        try {
            var uuid = UUID.fromString(id);
            var changed = books.findById(uuid).orElseThrow();
            if (!changed.getOwner().getId().equals(token.getId())) {
                return new GenericResponse(false, "not the owner");
            }
            users.findAll().forEach(u -> u.getFavorites().removeIf(i -> i.getId() == changed.getId()));
            books.deleteById(uuid);
            return new GenericResponse(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return new GenericResponse(false);
    }

    /**
     * Add/Update Tag
     *
     * @param token User token|java.lang.String
     * @param tag   tag's info
     * @return if operation succeed
     */
    @GetMapping("/tags/add")
    public GenericResponse addTag(@AuthedUser BookUser token, Tag tag) {
        if (token == null) {
            return new GenericResponse(false);
        }
        var result = tag.getId() == null ? tag : tags.findById(tag.getId()).orElseGet(() -> {
            tag.setId(null);
            return tag;
        });
        result.setName(tag.getName());
        tags.save(tag);
        return new GenericResponse(true);
    }

    /**
     * Add/Update Category
     *
     * @param token    User token|java.lang.String
     * @param category category's info
     * @return if operation succeed
     */
    @GetMapping("/categories/add")
    public GenericResponse addCategory(@AuthedUser BookUser token, Category category) {
        if (token == null) {
            return new GenericResponse(false);
        }
        var result = category.getId() == null ? category : categories.findById(category.getId()).orElseGet(() -> {
            category.setId(null);
            return category;
        });
        result.setName(category.getName());
        result.setDescription(category.getDescription());
        categories.save(category);
        return new GenericResponse(true);
    }

    /**
     * Add/Update Storage
     *
     * @param token   User token|java.lang.String
     * @param storage category's info
     * @return if operation succeed
     */
    @GetMapping("/storages/add")
    public GenericResponse addStorage(@AuthedUser BookUser token, Storage storage) {
        if (token == null || storage.getLocation() == null) {
            return new GenericResponse(false);
        }
        var result = storage.getId() == null ? storage : storages.findById(storage.getId()).orElseGet(() -> {
            storage.setId(null);
            return storage;
        });
        result.setLocation(storage.getLocation());
        storages.save(storage);
        return new GenericResponse(true);
    }
}
