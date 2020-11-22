# bingo-java

Bingo java is simple spring boot java based workshop project which contains several components for functional bingo game. 

## Introduction

### Game

### Game description 
  1. Each bingo round draws 40 random numbers in the range of 1 to 80, which are unique
  2. Draw interval is 1 minute
  3. Each ticket contains numbers in the range between 1 and 80
  4. Each ticket type have specific odds and price
  5. Ticket selections are generated automatically
  6. Ticket is winning if at least 10 of the selected number on ticket are drawn in that draw

## Requirements

Only requirement for this project is docker engine and docker-compose tool.

## Build and run

1. Build docker images and run docker containers

```bash
docker-compose up -d
````

2. Insert ticket configurations

```sql
INSERT INTO `ticket_type` (`id`, `description`, `number_of_selections`, `odds`, `price`) VALUES ('1', 'ticket 1', '10', '15.0', '1');
INSERT INTO `ticket_type` (`id`, `description`, `number_of_selections`, `odds`, `price`) VALUES ('2', 'ticket 2', '20', '7.5', '5');
INSERT INTO `ticket_type` (`id`, `description`, `number_of_selections`, `odds`, `price`) VALUES ('3', 'ticket 3', '30', '2', '10');
```

## Packages

Project has been done in monolith arhitecture but it contains several java packages:

1. **core** - random number generator and common constants
2. **database** - persistance layer, repositories and entity classes
3. **security** - oauth2 success handler class and websecurity configuration
4. **services** - application services
5. **controllers** - API controllers and request models

## Entry points

1. Login page **http://localhost:8080/**
2. Swagger API doc **http://localhost:8080/swagger-ui.html**