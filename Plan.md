## Goals
> multi-threaded card playing simulation
1. thread safe Card Class
2. thread safe Player Class
(what do they define as threadsafe? Check, probs avoiding all the basic issues like deadlock, livelock etc.)
3. CardGame Class
4. CardDeck Class (and any further needed classes)

## Pre-requisites
- n-players; n decks of cards (n>1)
> deck is thread-safe FIFO, drawing from top of pack, and discard to bottom. Draw and discard are *ONE ATOMIC ACTION*

- 4 cards per deck
- a pack containing *8n* cards
- cards each have a face value

### Methodology
- ring topology, round robin distribution
- each player gets his on hand from the pack
- then the pack gets split into decks for each player (using same distribution method)
- at the end, each player hold 4 cards, and each deck's final contents are written to `deckX_output.txt`
#### Threadsafe
how to make threadsafe decks:
1. Deadlock (everything is dependent on something else, so everything gets stuck)
2. Livelock (2 threads are too busy responding to each other)
3. Starvation (1 thing is taking forever)
Points where it might go wrong:
- each player is drawing and discarding in one atomic action
- when a player wins, the others have to stop, but have 4 cards, which is why it is important for pick and and drop to be 1 atomic action

### Game-play
To win, a player needs four cards of the same value in their hand.
Each player willprefer card denominations which match their n value.
They do not dump their number, and they do not hold onto a number which is not theirs indefinetely. 

- command line interface