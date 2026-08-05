# Personal Knowledge Management System API Design

## Base Information

**Base URL**

```text
/api/v1
```

**Authentication**

* Current Version: None
* Future Version: JWT Bearer Token

---

# Standard Response Format

## Success

```json
{
  "success": true,
  "message": "Operation completed successfully.",
  "data": {},
  "time": "2026-08-05T18:30:00"
}
```

## Error

```json
{
  "success": false,
  "error": "ResourceNotFoundException",
  "message": "Requested resource was not found.",
  "time": "2026-08-05T18:30:00"
}
```

---

# User APIs

**Base URL**

```text
/api/v1/users
```

---

## Register User

### Endpoint

| Property       | Value    |
| -------------- | -------- |
| Method         | POST     |
| URL            | `/users` |
| Authentication | No       |

### Request DTO

**RegisterUserRequest**

| Field    | Type   | Required | Validation        |
| -------- | ------ | -------- | ----------------- |
| name     | String | Yes      | Not Blank, Max 50 |
| email    | String | Yes      | Valid Email       |
| password | String | Yes      | Min 8 Characters  |

Example

```json
{
    "name":"abc",
    "email":"abc@gmail.com",
    "password":"abc@123"
}
```

### Response DTO

**UserResponse**

| Field |
| ----- |
| id    |
| name  |
| email |

Status Code

```text
201 Created
```

Possible Errors

* 400 ValidationException
* 409 EmailAlreadyExistsException

---

## Get All Users

### Endpoint

| Property       | Value        |
| -------------- | ------------ |
| Method         | GET          |
| URL            | `/users`     |
| Authentication | No (Testing) |

### Query Parameters

| Name      | Required | Description     |
| --------- | -------- | --------------- |
| page      | No       | Page Number     |
| size      | No       | Page Size       |
| sortBy    | No       | id, name, email |
| direction | No       | asc, desc       |

### Response

```text
Page<UserResponse>
```

Status Code

```text
200 OK
```

Possible Errors

* 400 InvalidQueryParameterException

---

## Get User By Id

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | GET           |
| URL      | `/users/{id}` |

### Path Variables

| Name | Type |
| ---- | ---- |
| id   | Long |

### Response

```text
UserResponse
```

Status Code

```text
200 OK
```

Possible Errors

* 404 UserNotFoundException

---

## Update User

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | PUT           |
| URL      | `/users/{id}` |

### Request DTO

**UpdateUserRequest**

| Field | Required |
| ----- | -------- |
| name  | Yes      |
| email | Yes      |

Example

```json
{
    "name":"Updated Name",
    "email":"updated@gmail.com"
}
```

### Response

```text
UserResponse
```

Status Code

```text
200 OK
```

Possible Errors

* 400 ValidationException
* 404 UserNotFoundException
* 409 EmailAlreadyExistsException

---

## Delete User

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | DELETE        |
| URL      | `/users/{id}` |

Status Code

```text
204 No Content
```

Possible Errors

* 404 UserNotFoundException

---

# Note APIs

> **Current Version**
>
> User authentication is not implemented yet, therefore `userId` is passed in the URL.
>
> After JWT authentication is added, the backend will obtain the user from the authenticated request and `userId` will be removed from these endpoints.

**Base URL**

```text
/api/v1/users/{userId}/notes
```

---

## Create Note

### Endpoint

| Property | Value                   |
| -------- | ----------------------- |
| Method   | POST                    |
| URL      | `/users/{userId}/notes` |

### Request DTO

**CreateNoteRequest**

| Field    | Type   | Required |
| -------- | ------ | -------- |
| title    | String | Yes      |
| content  | String | Yes      |
| folderId | Long   | No       |

Example

```json
{
    "title":"Java Programming",
    "content":"Spring Boot Notes...",
    "folderId":3
}
```

### Response DTO

**NoteResponse**

| Field     |
| --------- |
| id        |
| title     |
| content   |
| createdAt |
| updatedAt |
| folderId  |

Status Code

```text
201 Created
```

Possible Errors

* 400 ValidationException
* 404 UserNotFoundException
* 404 FolderNotFoundException

---

## Get All Notes

### Endpoint

| Property | Value                   |
| -------- | ----------------------- |
| Method   | GET                     |
| URL      | `/users/{userId}/notes` |

### Query Parameters

| Name      | Required | Description                     |
| --------- | -------- | ------------------------------- |
| page      | No       | Page Number                     |
| size      | No       | Page Size                       |
| sortBy    | No       | id, title, createdAt, updatedAt |
| direction | No       | asc, desc                       |
| title     | No       | Search by title                 |
| folderId  | No       | Filter by folder                |

### Response

```text
Page<NoteResponse>
```

Status Code

```text
200 OK
```

Possible Errors

* 400 InvalidQueryParameterException
* 404 UserNotFoundException

---

## Get Note By Id

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | GET           |
| URL      | `/notes/{id}` |

### Response

```text
NoteResponse
```

Status Code

```text
200 OK
```

Possible Errors

* 404 NoteNotFoundException

---

## Update Note

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | PUT           |
| URL      | `/notes/{id}` |

### Request DTO

**UpdateNoteRequest**

| Field    | Required |
| -------- | -------- |
| title    | Yes      |
| content  | Yes      |
| folderId | No       |

### Response

```text
NoteResponse
```

Status Code

```text
200 OK
```

Possible Errors

* 400 ValidationException
* 404 NoteNotFoundException
* 404 FolderNotFoundException

---

## Delete Note

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | DELETE        |
| URL      | `/notes/{id}` |

Status Code

```text
204 No Content
```

Possible Errors

* 404 NoteNotFoundException
