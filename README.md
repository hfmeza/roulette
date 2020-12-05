## Roulette Application

Run start.sh to spin up redis and application containers. run stop.sh to stop and remove the containers.

## *EndPoints*

### POST localhost:8080/roulette/

Creates a new Roulette and returns the id

### POST localhost:8080/roulette/{id}/open

Opens the roulette associated to the id

### POST localhost:8080/roulette/{id}/bet?number=xx&&color=xx&&value=xx

Places a bet on a roulette, the parameters number and color are optional since a bet can be placed on a number or a color 
but not both. Value is required.

### POST localhost:8080/roulette/{id}/close

Closes a roulette and returns the results of each of the bets. The winning number is always the same as the roulette id
to make it easier to test, unless the id is over the maximum bettable number, in which case a random number will be used

### GET localhost:8080/roulette

Returns a list of the existing roulettes.