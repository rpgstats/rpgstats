## Build instructions:

### Server:

1. Have `docker` and `docker-compose-plugin` installed

2. 
    
    $ mvn -f backend/ clean package -DskipTests
    $ docker build --tag=rpgstats/backend:latest ./backend/
    $ docker compose up -d
