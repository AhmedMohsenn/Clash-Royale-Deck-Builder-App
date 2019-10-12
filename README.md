# Clash-Royale-Deck-Builder-App

First of all the following ports should be free(8080, 1992).  

## Start the Backend
navigate to the backend project and run the following command:
```
mvn spring-boot:run
```

## Start the frontend

first install node modules by running the following command:
```
npm install
```
then run:
```
npm run serve
```


# Design decisions and choices

I used Vue.js for the frontend because it's a lightweight progressive framework and it's component based which makes the code more manageable.  

I used Spring Boot because I'm more familiar with Java and Spring.  

I used H2database because it's in memory so it's so fast and is suitable for small projects.

