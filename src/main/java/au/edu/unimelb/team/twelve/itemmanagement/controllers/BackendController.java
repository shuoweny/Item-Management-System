package au.edu.unimelb.team.twelve.itemmanagement.controllers;

import au.edu.unimelb.team.twelve.itemmanagement.Greeting;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Category;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Storage;
import au.edu.unimelb.team.twelve.itemmanagement.entities.Tag;
import au.edu.unimelb.team.twelve.itemmanagement.repositories.CategoryRepository;
import au.edu.unimelb.team.twelve.itemmanagement.repositories.StorageRepository;
import au.edu.unimelb.team.twelve.itemmanagement.repositories.TagRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * General Apis
 */
@RestController
public class BackendController {
    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final TagRepository tags;
    private final CategoryRepository categories;
    private final StorageRepository storages;

    public BackendController(TagRepository tags, CategoryRepository categories, StorageRepository storages) {
        this.tags = tags;
        this.categories = categories;
        this.storages = storages;
    }

    /**
     * Hello World!
     *
     * @param name your name!
     * @return hello world message
     */
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
    }

    /**
     * List Tags
     *
     * @return all tags
     */
    @GetMapping("/tags")
    public Iterable<Tag> tags() {
        return tags.findAll();
    }

    /**
     * List Storages
     *
     * @return all storages
     */
    @GetMapping("/storages")
    public Iterable<Storage> storages() {
        return storages.findAll();
    }

    /**
     * List Categories
     *
     * @return all categories
     */
    @GetMapping("/categories")
    public Iterable<Category> categories() {
        return categories.findAll();
    }
}