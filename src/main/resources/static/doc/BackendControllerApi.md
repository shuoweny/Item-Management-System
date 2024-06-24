
# General Apis
## Hello World!
**URL:** `/greeting`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** Hello World!



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
name|string|your name!|true|-


**Request-example:**
```
curl -X GET -i /greeting?name=World
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
id|int64|No comments found.|-
content|string|No comments found.|-

**Response-example:**
```
{
  "id": 283,
  "content": "c7oujx"
}
```

## List Tags
**URL:** `/tags`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** List Tags





**Request-example:**
```
curl -X GET -i /tags
```

**Response-example:**
```
{}
```

## List Storages
**URL:** `/storages`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** List Storages





**Request-example:**
```
curl -X GET -i /storages
```

**Response-example:**
```
{}
```

## List Categories
**URL:** `/categories`

**Type:** `GET`


**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** List Categories





**Request-example:**
```
curl -X GET -i /categories
```

**Response-example:**
```
{}
```

