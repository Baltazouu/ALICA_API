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




## à Faire :
Documentation pour la gestion des autorisations
[Documentation Baeldung](https://www.baeldung.com/role-and-privilege-for-spring-security-registration)

