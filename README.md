# sqat_selenium_testing_gtabase

## Commands:

### Launch all containers:

```bash
sudo docker compose up
```

### Launch into the developer container:

```bash
sudo docker exec -it sqat_selenium_testing_gtabase-ubuntu-1 bash
```

### Inside the development conatiner for the first time run:

```bash
cd tests
gradle build
```

### Inside the development container run the tests like this (from inside the "tests" folder):

```bash
gradle clean && gradle test
```

### The Selenium tests can be viewed through a web browser by going to:

[here](http://localhost:8081/)

# Runs locally on the gradle setup!