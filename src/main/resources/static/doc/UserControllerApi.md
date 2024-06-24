
# User Related Apis
## Register
**URL:** `/register`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Register



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
name|string|    username|true|-
password|string|SHA256ed password, will be stored with salted SHA256|true|-


**Request-example:**
```
curl -X GET -i /register?password=&name=
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Login
**URL:** `/login`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Login



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
name|string|    username|true|-
password|string|SHA256ed password|true|-


**Request-example:**
```
curl -X GET -i /login?name=&password=
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Logout
**URL:** `/logout`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Logout



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-


**Request-example:**
```
curl -X GET -i /logout?token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Remove Newcomer Flag
**URL:** `/came`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Remove Newcomer Flag



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-


**Request-example:**
```
curl -X GET -i /came?token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Delete User
**URL:** `/delete`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Delete User



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-


**Request-example:**
```
curl -X GET -i /delete?token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Retrieve Info
**URL:** `/info`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Retrieve Info



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-


**Request-example:**
```
curl -X GET -i /info?token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-
└─name|string|No comments found.|-
└─avatar|string|No comments found.|-
└─profile|string|No comments found.|-
└─uuid|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "name": "adalberto.towne",
    "avatar": "tec2rv",
    "profile": "696k3r",
    "uuid": "65ab7c28-b830-4325-a9dd-e854394ace85"
  }
}
```

## Retrieve Others' Info
**URL:** `/info/get`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Retrieve Others' Info



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-
uuid|string|No comments found.|false|-


**Request-example:**
```
curl -X GET -i /info/get?uuid=91154076-ef7e-4360-b289-1a3e9d30cf6c&token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-
└─name|string|No comments found.|-
└─avatar|string|No comments found.|-
└─profile|string|No comments found.|-
└─uuid|string|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "name": "adalberto.towne",
    "avatar": "tl7wva",
    "profile": "onnogt",
    "uuid": "4590c113-e78d-45ed-afff-c7fc9a9a48a4"
  }
}
```

## Search User By Name
**URL:** `/search`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Search User By Name



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
name|string|target user's name|true|-


**Request-example:**
```
curl -X GET -i /search?name=
```

**Response-example:**
```
{}
```

## List Items
**URL:** `/items/all`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** List Items



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-


**Request-example:**
```
curl -X GET -i /items/all?token=java.lang.String
```

**Response-example:**
```
{}
```

## List Books
**URL:** `/books/all`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** List Books



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-


**Request-example:**
```
curl -X GET -i /books/all?token=java.lang.String
```

**Response-example:**
```
{}
```

## Search Items (And)
**URL:** `/items/search/and`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Search Items (And)



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|      User token|false|-
location|string|   where it stores|true|-
name|string|       item's name|true|-
description|string|item's description|true|-
tag|string|        item's tag|true|-


**Request-example:**
```
curl -X GET -i /items/search/and?description=&tag=&location=&token=java.lang.String&name=
```

**Response-example:**
```
{}
```

## Search Items (Or)
**URL:** `/items/search/or`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Search Items (Or)



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-
location|string|No comments found.|true|-
name|string|No comments found.|true|-
description|string|No comments found.|true|-
tag|string|No comments found.|true|-


**Request-example:**
```
curl -X GET -i /items/search/or?token=java.lang.String&location=&description=&tag=&name=
```

**Response-example:**
```
{}
```

## Search Books (And)
**URL:** `/books/search/and`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Search Books (And)



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|      User token|false|-
location|string|   where it stores|true|-
name|string|       item's name|true|-
description|string|item's description|true|-
tag|string|        item's tag|true|-
isbn|string|       book's isbn|true|-
author|string|     book's author|true|-
publisher|string|  book's publisher|true|-
category|string|   book's category|true|-


**Request-example:**
```
curl -X GET -i /books/search/and?publisher=&name=&location=&isbn=&category=&token=java.lang.String&description=&tag=&author=
```

**Response-example:**
```
{}
```

## Search Books (Or)
**URL:** `/books/search/or`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Search Books (Or)



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|      User token|false|-
location|string|   where it stores|true|-
name|string|       item's name|true|-
description|string|item's description|true|-
tag|string|        item's tags|true|-
isbn|string|       book's isbn|true|-
author|string|     book's author|true|-
publisher|string|  book's publisher|true|-
category|string|   book's category|true|-


**Request-example:**
```
curl -X GET -i /books/search/or?publisher=&location=&tag=UUID[]&name=&description=&category=&token=java.lang.String&author=&isbn=
```

**Response-example:**
```
{}
```

## List Favorite Items
**URL:** `/favorites/items`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** List Favorite Items



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-


**Request-example:**
```
curl -X GET -i /favorites/items?token=java.lang.String
```

**Response-example:**
```
{}
```

## List Favorite Books
**URL:** `/favorites/books`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** List Favorite Books



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-


**Request-example:**
```
curl -X GET -i /favorites/books?token=java.lang.String
```

**Response-example:**
```
{}
```

## Add favorite item
**URL:** `/favorites/add`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Add favorite item



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-
uuid|string| item's uuid|true|-


**Request-example:**
```
curl -X GET -i /favorites/add?token=java.lang.String&uuid=91154076-ef7e-4360-b289-1a3e9d30cf6c
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Remove favorite item
**URL:** `/favorites/remove`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Remove favorite item



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-
uuid|string| item's uuid|true|-


**Request-example:**
```
curl -X GET -i /favorites/remove?token=java.lang.String&uuid=91154076-ef7e-4360-b289-1a3e9d30cf6c
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Add New Item
**URL:** `/items/add`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Add New Item



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-
id|string|No comments found.|false|-
ownerId|string|No comments found.|false|-
sharedIds|array|No comments found.|false|-
tagIds|array|No comments found.|false|-
name|string|No comments found.|false|-
description|string|No comments found.|false|-
storageId|string|No comments found.|false|-


**Request-example:**
```
curl -X GET -i /items/add?token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Update Exist Item
**URL:** `/items/update`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Update Exist Item



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-
id|string|No comments found.|false|-
ownerId|string|No comments found.|false|-
sharedIds|array|No comments found.|false|-
tagIds|array|No comments found.|false|-
name|string|No comments found.|false|-
description|string|No comments found.|false|-
storageId|string|No comments found.|false|-


**Request-example:**
```
curl -X GET -i /items/update?token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Add New Book
**URL:** `/books/add`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Add New Book



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-
id|string|No comments found.|false|-
ownerId|string|No comments found.|false|-
sharedIds|array|No comments found.|false|-
tagIds|array|No comments found.|false|-
name|string|No comments found.|false|-
description|string|No comments found.|false|-
storageId|string|No comments found.|false|-
author|string|No comments found.|false|-
publishedDate|string|No comments found.|false|-
isbn|string|No comments found.|false|-
publisher|string|No comments found.|false|-
categoryIds|array|No comments found.|false|-


**Request-example:**
```
curl -X GET -i /books/add?token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Update Exist Book
**URL:** `/books/update`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Update Exist Book



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-
id|string|No comments found.|false|-
ownerId|string|No comments found.|false|-
sharedIds|array|No comments found.|false|-
tagIds|array|No comments found.|false|-
name|string|No comments found.|false|-
description|string|No comments found.|false|-
storageId|string|No comments found.|false|-
author|string|No comments found.|false|-
publishedDate|string|No comments found.|false|-
isbn|string|No comments found.|false|-
publisher|string|No comments found.|false|-
categoryIds|array|No comments found.|false|-


**Request-example:**
```
curl -X GET -i /books/update?token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Add/Update Tag
**URL:** `/tags/add`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Add/Update Tag



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|User token|false|-
id|string|No comments found.|false|-
name|string|No comments found.|false|-


**Request-example:**
```
curl -X GET -i /tags/add?token=java.lang.String&name=adalberto.towne&id=21d842b4-c33c-4e1a-b52a-a96729acbc35
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Add/Update Category
**URL:** `/categories/add`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Add/Update Category



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|   User token|false|-
id|string|No comments found.|false|-
name|string|No comments found.|false|-
description|string|No comments found.|false|-


**Request-example:**
```
curl -X GET -i /categories/add?name=adalberto.towne&description=kz1on6&id=e0084707-3513-46b3-8e9b-1b3d0665e8d0&token=java.lang.String
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

## Add/Update Storage
**URL:** `/storages/add`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Add/Update Storage



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
token|object|  User token|false|-
id|string|No comments found.|false|-
location|string|No comments found.|false|-


**Request-example:**
```
curl -X GET -i /storages/add?token=java.lang.String&location=26pt72&id=1d1e48cc-0f4d-447c-b1da-12056c8916a2
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
success|boolean|No comments found.|-
contents|object|No comments found.|-

**Response-example:**
```
{
  "success": true,
  "contents": {
    "waring": "You may have used non-display generics."
  }
}
```

