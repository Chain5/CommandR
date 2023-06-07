--------------------------- PLAYER TABLES ---------------------------
-- Player Table
CREATE TABLE player
(
    id         SERIAL PRIMARY KEY,
    nickname   VARCHAR(50) NOT NULL,
    first_name VARCHAR,
    last_name  VARCHAR,
    password   VARCHAR     NOT NULL,
    CONSTRAINT player_unique UNIQUE (nickname)
);

------------------------- TOURNAMENT TABLES -------------------------
-- Tournament Table
CREATE TABLE tournament
(
    id                      SERIAL PRIMARY KEY,
    tournament_name         VARCHAR(100) NOT NULL,
    start_date              DATE,
    end_date                DATE,
    generated_match_counter INTEGER      NOT NULL DEFAULT 0,
    started                 BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT tournament_unique UNIQUE (tournament_name)
);

-- Tournament_Score_Board_Table (it's like a link table between player and tournament)
CREATE TABLE tournament_score_board
(
    id                 SERIAL PRIMARY KEY,
    tournament_id      INTEGER REFERENCES tournament (id),
    player_id          INTEGER REFERENCES player (id),
    player_total_score INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT tournament_score_board_unique UNIQUE (tournament_id, player_id)
);

--------------------------- MATCH TABLES ---------------------------

-- Game_Table Table
CREATE TABLE game_table
(
    id            SERIAL PRIMARY KEY,
    tournament_id INTEGER REFERENCES tournament (id),
    table_number  INTEGER NOT NULL,
    is_finished   BOOLEAN NOT NULL DEFAULT FALSE,
    updated_score BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT game_table_unique UNIQUE (tournament_id, table_number)
);

-- Player_Match Table
CREATE TABLE player_match
(
    id             SERIAL PRIMARY KEY,
    game_table_id  INTEGER REFERENCES game_table (id),
    player_id      INTEGER REFERENCES player (id),
    score          INTEGER NOT NULL DEFAULT 0,
    approved_score BOOLEAN NOT NULL DEFAULT FALSE,
    met_players    VARCHAR,
    CONSTRAINT player_match_unique UNIQUE (game_table_id, player_id)
);