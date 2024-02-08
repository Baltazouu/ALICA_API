# Alica - REST SPRINGBOOT API  - Documentation

[![Build Status](https://codefirst.iut.uca.fr/api/badges/SportsSpot/SportsSpot/status.svg)](https://codefirst.iut.uca.fr/SportsSpot/SportsSpot)

### Documentation : 
swagger accessible à :
**http://8080/swagger-ui.html**

Base url : **http://localhost:8080**

Contrat d'interface : 

*le développement est en cours est d'autres endpoints arriveront prochainement*




### ACCESS URL : localhost:8080


## ALICA REST API - Contrat d’interface

### Alumnis :

| Action | EndPoint | HTTP VERB | Request Body | Return Code | Response Body |
| --- | --- | --- | --- | --- | --- |
| Alumnis :  | /alumni |  |  |  |  |
| findAll | /alumni | GET |  | 200 OK  | Page of Alumni (see swagger for json details) |
| FindById | /alumni/{id} | GET |  | 200 OK  | Alumni finded |
| Create Alumni | /alumni | POST | alumni voir swagger | 201 CREATED | Alumni created |
| Update Alumni | /alumni/{id} | PUT | alumni voir swagger | 200 OK  | Alumni updated |
| Delete Alumni | /alumni/{id} | DELETE |  | 204 NO CONTENT |  |


## Endpoints Offers : 

| Action         | EndPoint           | HTTP VERB | Request Body       | Return Code      | Response Body          |
|----------------|--------------------|-----------|--------------------|------------------|------------------------|
| findAll        | /offers (page)     | GET       |                    | 200 OK           | page of find           |
| findById       | /offers/{id}       | GET       |                    | 200 OK           | offer find             |
| createOffer    | /offers            | POST      | offer see swagger  | 201 CREATED      | offer created          |
| updateOffer    | /offers/{id}       | PUT       | offer see swagger  | 200 OK           | offer updated          |
| delete offer   | /offers/{id}       | DELETE    |                    | 204 NO CONTENT   |                        |
| findByAlumni   | /offers/{id}       | GET       |                    | 200 OK           | page of find           |


## Endpoints Formations : 

| Action | EndPoint | HTTP VERB | Request Body | Return Code | Response Body |
| --- | --- | --- | --- | --- | --- |
| findAll | /formations (page) | GET |  | 200 OK  | Page find |
| findById | /formations/{id} | GET |  | 200 OK  | Formation find |
| createFormation | /formations | POST | formation see swagger | 201 CREATED | formation created |
| update formation | /formations/{id} | PUT | formation see swagger | 200 OK  | formation updated |
| delete | /formations/{id} | DELETE |  | 204 NO COTENT |  |
| findByAlumniId | /formations/alumni/{id} (page) | GET |  | 200 OK  | page of find |


## Endpoints Articles 

| Action | EndPoint | HTTP VERB | Request Body | Return Code | Response Body |
| --- | --- | --- | --- | --- | --- |
| findAll | /articles (page) | GET |  | 200 OK  | page Articles find |
| findByID | /articles/{id} | GET |  | 200 OK  | article find |
| findByAlumni | /articles/alumni/{id} | GET |  | 200 OK  | Page of articles find |
| CreateArticle | /articles | POST | Article see swagger | 201 CREATED | Article created |
| Update Article | /articles | PUT | Article see swagger | 200 OK  | Article updated |
| Delete Article | /articles/{id} | DELETE |  | 204 NO CONTENT |  |

## Endpoints Event 

| Action | EndPoint | HTTP VERB | Request Body | Return Code | Response Body |
| --- | --- | --- | --- | --- | --- |
| findAll | /events (?page) | GET |  | 200 OK  | page of Events |
| findById | /events/{id} | GET |  | 200 OK  | event find |
| createEvent | /event | POST | event see swagger | 201 CREATED | event created |
| updateEvent | /event/{id} | PUT | event see swagger | 200 OK  | event updated |
| deleteEvent | /event/{id} | DELETE |  | 204 NO CONTENT |  |
| findByAlumni( organisateur)  | /event/alumni/{id} | GET |  | 200 OK  | page of events find |
| subscribe | /events/subscribe/{eventId}/alumni/{alumniId} | GET |  | 200 OK |  |
| unsubscribe | /events/unsubscribe/{eventId}/alumni/{alumniId} | GET |  | 200 OK |  |
| find subscribers | /events/subscribers/{eventId} | GET |  | 200 OK  | List of Alumnis |
## à Faire :

Endpoint with query paramters (ContractType, Experience & Studies ) for offers

Endpoint with query of title for events

some others....

TO DO : manage authorizations 
[Baeldung documentation](https://www.baeldung.com/ERole-and-privilege-for-spring-security-registration)

