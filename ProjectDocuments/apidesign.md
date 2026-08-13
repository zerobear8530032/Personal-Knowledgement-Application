# Personal Knowledge Management System API Design

## Base Information

**Base URL**

```text
/api/v1
```

**Authentication**

* Current Version: None
* Future Version: JWT Bearer Token

> **Current Development Note**
>
> Authentication is not implemented yet. User IDs are currently passed through the API where required. Once JWT authentication is implemented, the backend will obtain the authenticated user from the security context instead of trusting a user ID supplied by the client.

---

# Standard Response Format

## Success

```json
{
  "success": true,
  "message": "Operation completed successfully.",
  "data": {},
  "time": "2026-08-08T18:30:00"
}
```

## Error

```json
{
  "success": false,
  "error": "ResourceNotFoundException",
  "message": "Requested resource was not found.",
  "time": "2026-08-08T18:30:00"
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

Example:

```json
{
  "name": "abc",
  "email": "abc@gmail.com",
  "password": "abc@123"
}
```

### Response DTO

**UserResponse**

| Field |
| ----- |
| id    |
| name  |
| email |

> Password is never returned in the response. The password is encoded before being stored in the database.

### Status Code

```text
201 Created
```

### Possible Errors

* 400 ValidationException
* 409 EmailAlreadyRegisteredException

---

## Get All Users

### Endpoint

| Property       | Value        |
| -------------- | ------------ |
| Method         | GET          |
| URL            | `/users`     |
| Authentication | No (Testing) |

### Query Parameters

| Name      | Required | Default | Description                |
| --------- | -------- | ------- | -------------------------- |
| page      | No       | 0       | Page number                |
| size      | No       | 5       | Number of records per page |
| sortBy    | No       | ID      | Field used for sorting     |
| direction | No       | DESC    | ASC or DESC                |

### Pagination Rules

* Negative page numbers are converted to `0`.
* Page size below `1` defaults to `5`.
* Page size cannot exceed the configured maximum.
* Sorting is controlled through `sortBy` and `direction`.

### Response

```text
List<UserResponse>
```

### Status Code

```text
200 OK
```

### Possible Errors

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

### Status Code

```text
200 OK
```

### Possible Errors

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

| Field | Type   | Required |
| ----- | ------ | -------- |
| name  | String | Yes      |
| email | String | Yes      |

### Example

```json
{
  "name": "Updated Name",
  "email": "updated@gmail.com"
}
```

### Behavior

* User must exist.
* Email must not already belong to another user.
* Keeping the user's existing email is allowed.
* Password is not updated through this endpoint.

### Response

```text
UserResponse
```

### Status Code

```text
200 OK
```

### Possible Errors

* 400 ValidationException
* 404 UserNotFoundException
* 409 EmailAlreadyRegisteredException

---

## Delete User

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | DELETE        |
| URL      | `/users/{id}` |

### Status Code

```text
204 No Content
```

### Possible Errors

* 404 UserNotFoundException

### Relationship Behavior

A User owns their Notes.

When permanent user deletion is implemented, deleting a User is intended to delete the Notes belonging to that User through the JPA relationship cascade.

---

# Note APIs

**Current Base URL**

```text
/api/v1/notes
```

> **Current Development Note**
>
> Authentication is not implemented yet. The user ID is therefore passed in the URL when creating or retrieving notes belonging to a specific user.
>
> The current implementation uses:
>
> ```text
> /notes/users/{userId}
> ```
>
> This can later be simplified when JWT authentication is introduced.

---

# Note Data Rules

### Title

* Required
* Cannot be `null`
* Cannot be empty
* Cannot contain only whitespace

### Content

* Required
* Cannot be `null`
* Empty content is allowed

Example of a valid empty note:

```json
{
  "title": "Spring Boot",
  "content": ""
}
```

This allows a note to be created before the user starts writing its content.

---

## Create Note

### Endpoint

| Property | Value                   |
| -------- | ----------------------- |
| Method   | POST                    |
| URL      | `/notes/users/{userId}` |

### Path Variables

| Name   | Type |
| ------ | ---- |
| userId | Long |

### Request DTO

**CreateNoteRequest**

| Field   | Type   | Required |
| ------- | ------ | -------- |
| title   | String | Yes      |
| content | String | Yes      |

### Example

```json
{
  "title": "Java Programming",
  "content": "Spring Boot Notes..."
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
| userId    |

### Status Code

```text
201 Created
```

### Possible Errors

* 400 ValidationException
* 404 UserNotFoundException

> `folderId` will be added when the Folder entity and Note → Folder relationship are implemented.

---

## Get All Notes

### Endpoint

| Property | Value    |
| -------- | -------- |
| Method   | GET      |
| URL      | `/notes` |

### Query Parameters

| Name      | Required | Default | Description                     |
| --------- | -------- | ------- | ------------------------------- |
| page      | No       | 0       | Page number                     |
| size      | No       | 5       | Number of records per page      |
| sortBy    | No       | ID      | id, title, createdAt, updatedAt |
| direction | No       | ASC     | ASC or DESC                     |

### Pagination Rules

* Negative page numbers are converted to `0`.
* Page size below `1` defaults to `5`.
* Page size cannot exceed the configured maximum.
* Sorting is supported.

### Response

```text
List<NoteResponse>
```

### Status Code

```text
200 OK
```

### Possible Errors

* 400 InvalidQueryParameterException

> Pagination currently returns a `List<NoteResponse>`. The implementation may later return `Page<NoteResponse>` so pagination metadata such as total elements and total pages can be exposed.

---

## Get Lightweight Note List

This endpoint is intended for UI components that only need to display a list of notes without loading potentially large note content.

### Endpoint

| Property | Value          |
| -------- | -------------- |
| Method   | GET            |
| URL      | `/notes/names` |

### Query Parameters

| Name      | Required | Default | Description       |
| --------- | -------- | ------- | ----------------- |
| page      | No       | 0       | Page number       |
| size      | No       | 5       | Number of records |
| sortBy    | No       | ID      | Sort field        |
| direction | No       | ASC     | ASC or DESC       |

### Response DTO

**NoteNameResponse**

| Field |
| ----- |
| id    |
| title |

### Example Response Data

```json
[
  {
    "id": 1,
    "title": "Spring Boot"
  },
  {
    "id": 2,
    "title": "JPA Relationships"
  }
]
```

### Status Code

```text
200 OK
```

---

## Get Notes Belonging To A User

### Endpoint

| Property | Value                   |
| -------- | ----------------------- |
| Method   | GET                     |
| URL      | `/notes/users/{userId}` |

### Path Variables

| Name   | Type |
| ------ | ---- |
| userId | Long |

### Query Parameters

| Name      | Required | Default | Description                     |
| --------- | -------- | ------- | ------------------------------- |
| page      | No       | 0       | Page number                     |
| size      | No       | 5       | Number of records per page      |
| sortBy    | No       | ID      | id, title, createdAt, updatedAt |
| direction | No       | ASC     | ASC or DESC                     |

### Response

```text
List<NoteResponse>
```

### Status Code

```text
200 OK
```

### Possible Errors

* 400 InvalidQueryParameterException
* 404 UserNotFoundException

### Implementation

Notes are retrieved directly using the user's ID rather than loading the user's entire `userNotes` collection.

Conceptually:

```text
User ID
   ↓
NoteRepository.findByUserId(...)
   ↓
Page<Note>
   ↓
Page/List<NoteResponse>
```

---

## Get Lightweight Notes Belonging To A User

This endpoint is intended for displaying a user's note list without loading note content.

### Endpoint

| Property | Value                         |
| -------- | ----------------------------- |
| Method   | GET                           |
| URL      | `/notes/users/{userId}/names` |

### Path Variables

| Name   | Type |
| ------ | ---- |
| userId | Long |

### Query Parameters

| Name      | Required | Default | Description       |
| --------- | -------- | ------- | ----------------- |
| page      | No       | 0       | Page number       |
| size      | No       | 5       | Number of records |
| sortBy    | No       | ID      | Sort field        |
| direction | No       | ASC     | ASC or DESC       |

### Response DTO

**NoteNameResponse**

| Field |
| ----- |
| id    |
| title |

### Response

```text
List<NoteNameResponse>
```

### Status Code

```text
200 OK
```

### Possible Errors

* 400 InvalidQueryParameterException
* 404 UserNotFoundException

---

## Get Note By Id

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | GET           |
| URL      | `/notes/{id}` |

### Path Variables

| Name | Type |
| ---- | ---- |
| id   | Long |

### Response

```text
NoteResponse
```

### Status Code

```text
200 OK
```

### Possible Errors

* 404 NoteNotFoundException

---

## Update Note

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | PUT           |
| URL      | `/notes/{id}` |

### Path Variables

| Name | Type |
| ---- | ---- |
| id   | Long |

### Request DTO

**UpdateNoteRequest**

| Field   | Type   | Required |
| ------- | ------ | -------- |
| title   | String | Yes      |
| content | String | Yes      |

### Example

```json
{
  "title": "Updated Spring Boot Notes",
  "content": "Updated content..."
}
```

### Behavior

The endpoint replaces the editable note fields:

* title
* content

The `updatedAt` timestamp is updated when the note is modified.

### Response

```text
NoteResponse
```

### Status Code

```text
200 OK
```

### Possible Errors

* 400 ValidationException
* 404 NoteNotFoundException

---

## Delete Note

### Endpoint

| Property | Value         |
| -------- | ------------- |
| Method   | DELETE        |
| URL      | `/notes/{id}` |

### Path Variables

| Name | Type |
| ---- | ---- |
| id   | Long |

### Status Code

```text
204 No Content
```

### Possible Errors

* 404 NoteNotFoundException

> Future versions may replace permanent deletion with soft deletion/recycle-bin behavior.

---

# Current Entity Relationships

## User → Note

```text
User 1 ─────────── * Note
```

### User

```text
User
├── id
├── name
├── email
├── password
└── userNotes
```

### Note

```text
Note
├── id
├── title
├── content
├── createdAt
├── updatedAt
└── user
```

The `Note` owns the database relationship through:

```java
@ManyToOne
@JoinColumn(name = "user_id", nullable = false)
private User user;
```

The `User` contains the inverse relationship:

```java
@OneToMany(mappedBy = "user")
private List<Note> userNotes;
```
# Folder APIs

## Base URL

```text
/api/v1/users/{userId}/folders
```

## Folder Relationship

A User can own multiple Folders.

```text
User 1 ─────────── * Folder
```

The relationship is currently **unidirectional** from `Folder → User`.

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;
```

The `User` entity does not maintain a `folders` collection.

---

## Folder Data

```text
Folder
├── id
├── name
├── color
├── createdAt
├── isDeleted
└── user
```

---

## Create Folder

### Endpoint

| Property | Value                     |
| -------- | ------------------------- |
| Method   | POST                      |
| URL      | `/users/{userId}/folders` |

### Path Variables

| Name   | Type |
| ------ | ---- |
| userId | Long |

### Request DTO

**FolderRequest**

```json
{
  "folderName": "DSA"
}
```

### Response DTO

**FolderResponse**

Contains the folder information returned by `Folder.toDTO()`.

### Status Code

```text
201 Created
```

### Possible Errors

* 404 UserNotFoundException

---

## Get All User Folders

### Endpoint

| Property | Value                     |
| -------- | ------------------------- |
| Method   | GET                       |
| URL      | `/users/{userId}/folders` |

### Path Variables

| Name   | Type |
| ------ | ---- |
| userId | Long |

### Behavior

Returns all **non-deleted folders** belonging to the specified user.

Deleted folders are excluded using:

```text
findByUserIdAndIsDeletedFalse(userId)
```

Pagination is not currently used because the number of folders for a user is expected to remain relatively small compared with notes.

### Response

```text
List<FolderResponse>
```

### Status Code

```text
200 OK
```

---

## Rename Folder

### Endpoint

| Property | Value                                |
| -------- | ------------------------------------ |
| Method   | PUT                                  |
| URL      | `/users/{userId}/folders/{folderId}` |

### Path Variables

| Name     | Type |
| -------- | ---- |
| userId   | Long |
| folderId | Long |

### Request DTO

**FolderRequest**

```json
{
  "folderName": "Advanced DSA"
}
```

### Behavior

The folder can only be renamed when:

* The folder exists.
* The folder belongs to the specified user.
* The folder has not been deleted.

The repository lookup uses:

```text
findByIdAndUserIdAndIsDeletedFalse(folderId, userId)
```

### Response

```text
FolderResponse
```

### Status Code

```text
200 OK
```

### Possible Errors

* 404 FolderDoesNotExistException

---

## Delete Folder

### Endpoint

| Property | Value                                |
| -------- | ------------------------------------ |
| Method   | DELETE                               |
| URL      | `/users/{userId}/folders/{folderId}` |

### Path Variables

| Name     | Type |
| -------- | ---- |
| userId   | Long |
| folderId | Long |

### Behavior

Folders use **soft deletion**.

Deleting a folder does not physically remove it from the database. Instead:

```text
isDeleted = true
```

The folder is then excluded from normal folder listings.

The repository lookup verifies that the folder belongs to the specified user:

```text
findByIdAndUserId(folderId, userId)
```

### Delete Behavior

Deleting an active folder:

```text
isDeleted = false
        ↓
isDeleted = true
```

Deleting the same folder again has no additional effect.

If the folder does not exist, or does not belong to the specified user, a `FolderDoesNotExistException` is thrown.

### Status Code

```text
204 No Content
```

---

## Folder Repository Queries

The current Folder operations use repository queries based on ownership and deletion state:

```text
findByIdAndUserId(...)
findByIdAndUserIdAndIsDeletedFalse(...)
findByUserIdAndIsDeletedFalse(...)
```

This ensures that folder operations are performed only on folders belonging to the requested user.

---

## Current Folder API Summary

```text
POST   /users/{userId}/folders
GET    /users/{userId}/folders
PUT    /users/{userId}/folders/{folderId}
DELETE /users/{userId}/folders/{folderId}
```

### Current Design Decisions

* Folder → User relationship is unidirectional.
* Folders use soft deletion.
* Deleted folders are excluded from normal listings.
* DELETE is idempotent for an existing folder.
* Folder operations verify user ownership.
* Folder listing does not use pagination.
* Nested folders are not implemented yet.
* Restoring deleted folders is not implemented yet.
* Assigning notes to folders is not implemented yet.

---

# Planned Entities

The following entities are planned but are **not implemented yet**:

* Attachment
* Tag
* Study Session
* Flashcard

Planned relationships will be designed before implementation.

---

# Planned Features

## Folder

* Assign notes to folders
* Move notes between folders

## Attachments

* Attach files to notes
* Store attachment metadata in database
* Store actual files separately
* Retrieve attachment information

## Tags

* Create and manage tags
* Assign multiple tags to notes
* Filter notes by tags

## Search

Planned search capabilities:

* Search notes by title
* Search notes by content
* Filter by folder
* Filter by tags

## Learning Features

Future phases:

* Study sessions
* Spaced repetition
* Flashcards
* Quizzes
* Note summaries

## AI Features

Future phase:

* Note summarization
* Automatic tag generation
* Study question generation
* Knowledge extraction

---

# API Design Principles

1. Request and response models are separated.

```text
CreateNoteRequest
UpdateNoteRequest
NoteResponse
NoteNameResponse
```

2. JPA entities are not directly exposed through API responses.

3. Large note content should not be returned when only note identification information is required.

4. Pagination and sorting are supported for collection endpoints.

5. User-owned resources will eventually be protected through JWT authentication.

6. Resource relationships should not automatically imply nested response DTOs. Each response should contain only the information required by that API.

7. Entity relationships and deletion behavior will be explicitly defined before adding new entities.
