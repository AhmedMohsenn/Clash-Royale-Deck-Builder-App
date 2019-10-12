# Clash-Royale-Deck-Builder-App

First of all the following ports should be free(8080, 1992).  

## Start the Backend
navigate to the backend project and run the following command:
```
mvn spring-boot:run
```

## Start the Frontend

first install node modules by running the following command:
```
npm install
```
then run:
```
npm run serve
```


# Design Decisions and Choices

## Frameworks
I used Vue.js for the frontend because it's a lightweight progressive framework and it's component based which makes the code more manageable.  

I used Spring Boot because I'm more familiar with Java and Spring.  

I used H2database because it's in memory so it's so fast and is suitable for small projects.  

## Initializing The H2database
On startup of the app I will initialize my h2database tables by calling Clash Royale end points to make sure that there's no change or missing data and after that any API call it will be with my h2database.

## Frontend Design
The frontend is a component based, every thing is a component and they communicate with each other by passing data and firing events.
