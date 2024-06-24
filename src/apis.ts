import axios, { type AxiosRequestConfig } from "axios";

/**
 * Contains all backend related apis
 */
export class Apis {
  /**
   * Get token from current user
   * @return {token} Current user's token
   */
  public static getToken(): string {
    return localStorage["lkToken"];
  }

  /**
   * Add a uuid to recent list
   * @param {string} id uuid
   */
  public static addRecent(id: string): void {
    const a = JSON.parse(
      localStorage.getItem("recent") || "[]"
    ) as Array<string>;
    const index = a.indexOf(id);
    if (index !== -1) {
      a.splice(index, 1);
    }
    a.push(id);
    localStorage.setItem("recent", JSON.stringify(a.slice(-3)));
  }

  /**
   * Get recently accessed items' uuid
   * @return {Array<String>} recently accessed items' uuid
   */
  public static getRecentItems(): Array<string> {
    return (
      JSON.parse(localStorage.getItem("recent") || "[]") as Array<string>
    ).reverse();
  }

  /**
   * Get all storages, if there is non, a default one will be added
   * @param {boolean} again if we should recheck again the default exist or not
   * @return {Array<Storage>}all storages
   */
  public static async getStorages(again = true): Promise<Array<Storage>> {
    const response = await axios.get("/api/storages", this.getAsUser({}));
    if (response.status == 200) {
      if (!response.data.length || response.data.length == 0) {
        await axios.get(
          "/api/user/storages/add",
          this.getAsUser({ location: "default" })
        );
        return again ? this.getStorages(false) : response.data;
      }
      return response.data;
    }
    return [];
  }

  /**
   * Get storage's location from its uuid
   * @param {uuid} id uuid of the storage
   * @return {string} the location of the storage
   */
  public static async getStorage(id: string): Promise<string> {
    let result = "";
    (await this.getStorages()).forEach((e) => {
      if (e.id == id) {
        result = e.location;
      }
    });
    return result;
  }

  /**
   * Get all tags
   * @return {Array<Tag>} all tags
   */
  public static async getTags(): Promise<Array<Tag>> {
    const response = await axios.get("/api/tags", this.getAsUser({}));
    if (response.status == 200) {
      return response.data;
    }
    return [];
  }

  /**
   * Get all books of current user
   * @return {Array<Book>} get all books current user can see
   */
  public static async getBooks(): Promise<Array<Book>> {
    const response = await axios.get("/api/user/books/all", this.getAsUser({}));
    if (response.status == 200) {
      return response.data;
    }
    return [];
  }
  /**
   * Get all items of current user
   * @return {Array<Item>}get all items current user can see
   */
  public static async getItems(): Promise<Array<Item>> {
    const response = await axios.get("/api/user/items/all", this.getAsUser({}));
    if (response.status == 200) {
      return response.data;
    }
    return [];
  }

  /**
   * Get all categories
   * @return {Array<Category>} get all categories current user can see
   */
  public static async getCategories(): Promise<Array<Category>> {
    const response = await axios.get("/api/categories", this.getAsUser({}));
    if (response.status == 200) {
      return response.data;
    }
    return [];
  }

  /**
   *
   * @return {Array<Book>} get all favorited book
   */
  public static async getFavBooks(): Promise<Array<Book>> {
    const response = await axios.get(
      "/api/user/favorites/books",
      this.getAsUser({})
    );
    if (response.status == 200) {
      return response.data;
    }
    return [];
  }

  /**
   * Get all favorited items
   * @return {Array<Item>} all fav items
   */
  public static async getFavItems(): Promise<Array<Item>> {
    const response = await axios.get(
      "/api/user/favorites/items",
      this.getAsUser({})
    );
    if (response.status == 200) {
      return response.data;
    }
    return [];
  }

  /**
   * Check if an item is fav or not
   * @param {String} uuid the uuid
   * @return {boolean} if is a fav item
   */
  public static async isFavItem(uuid: string): Promise<boolean> {
    return (await this.getFavItems()).filter((i) => i.id == uuid).length > 0;
  }

  /**
   * Get current user's info
   * @return {User} user's info
   */
  public static async getUserInfo(): Promise<User> {
    const response = await axios.get("/api/user/info", this.getAsUser({}));
    if (response.status == 200) {
      const { success, contents } = response.data;
      if (success) {
        return contents;
      }
    }
    throw new RangeError();
  }
  /**
   * Create an axios get config for current user, containing user's token
   * @param {object} any any object
   * @return {AxiosRequestConfig} a request config used in axios's get method
   */
  public static getAsUser<T>(any: T): AxiosRequestConfig {
    return {
      params: {
        any,
        token: this.getToken(),
      },
    };
  }
}

/**
 * User DTO
 */
class User {
  /**
   *
   * @param {string} name dto
   * @param {string} avatar dto
   * @param {string} profile dto
   * @param {string} uuid dto
   */
  constructor(
    readonly name: string,
    readonly avatar: string,
    readonly profile: string,
    readonly uuid: string
  ) {}
}
/**
 * Storage DTO
 */
class Storage {
  /**
   *
   * @param {string} id dto
   * @param {string} location dto
   */
  constructor(readonly id: string, readonly location: string) {}
}
/**
 * Tag DTO
 */
class Tag {
  /**
   *
   * @param {string} id dto
   * @param {string} name dto
   */
  constructor(readonly id: string, readonly name: string) {}
}
/**
 * category DTO
 */
class Category {
  /**
   *
   * @param {string} id
   * @param {string} name
   */
  constructor(readonly id: string, readonly name: string) {}
}

/**
 * Book DTO
 */
class Book {
  /**
   *
   * @param {string} name dto
   * @param {string} description dto
   * @param {string} storageId dto
   * @param {string} author dto
   * @param {string} publishedDate dto
   * @param {string} publisher dto
   * @param {string} isbn dto
   * @param {string} id dto
   * @param {string} ownerId dto
   * @param {string} sharedIds dto
   * @param {string} tagIds dto
   * @param {string} categoryIds dto
   */
  constructor(
    readonly name: string,
    readonly description: string,
    readonly storageId: string,
    readonly author: string,
    readonly publishedDate: string,
    readonly publisher: string,
    readonly isbn: string,
    readonly id: string,
    readonly ownerId: string,
    readonly sharedIds: Array<string>,
    readonly tagIds: Array<string>,
    readonly categoryIds: Array<string>
  ) {}
}
/**
 * Item DTO
 */
class Item {
  /**
   *
   * @param {string} name dto
   * @param {string} description dto
   * @param {string} storageId dto
   * @param {string} id dto
   * @param {string} ownerId dto
   * @param {string} sharedIds dto
   * @param {string} tagIds dto
   */
  constructor(
    readonly name: string,
    readonly description: string,
    readonly storageId: string,
    readonly id: string,
    readonly ownerId: string,
    readonly sharedIds: Array<string>,
    readonly tagIds: Array<string>
  ) {}
}

// For debug only
declare global {
  interface Window {
    api: Apis;
  }
}
window.api = Apis;
