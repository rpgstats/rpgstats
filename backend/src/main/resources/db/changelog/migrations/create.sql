--liquibase formatted sql

--changeset nomelyanenko:create
CREATE TABLE "users" (
                         "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         "username" text UNIQUE NOT NULL,
                         "email" text UNIQUE,
                         "password" text NOT NULL
);

CREATE TABLE "systems" (
                           "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                           "name" text NOT NULL,
                           "description" text,
                           "created_at" timestamp NOT NULL,
                           "owner_id" integer NOT NULL,
                           "parent_system_id" integer
);

CREATE TABLE "system_parameters" (
                                     "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     "name" text NOT NULL,
                                     "min_value" double precision NOT NULL,
                                     "max_value" double precision NOT NULL,
                                     "created_at" timestamp NOT NULL,
                                     "system_id" integer NOT NULL
);

CREATE TABLE "system_items" (
                                "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                "name" text NOT NULL,
                                "is_present" boolean NOT NULL,
                                "system_id" integer NOT NULL
);

CREATE TABLE "system_tags" (
                               "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                               "name" text NOT NULL,
                               "system_id" integer NOT NULL
);

CREATE TABLE "system_items__system_tags" (
                                             "system_item_id" integer,
                                             "system_tag_id" integer,
                                             PRIMARY KEY ("system_item_id", "system_tag_id")
);

CREATE TABLE "parameter_modifiers" (
                                       "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                       "name" text NOT NULL,
                                       "value" double precision NOT NULL,
                                       "parameter_id" integer NOT NULL
);

CREATE TABLE "system_items__parameter_modifiers" (
                                                     "system_item_id" integer,
                                                     "parameter_modifier_id" integer,
                                                     PRIMARY KEY ("system_item_id", "parameter_modifier_id")
);

CREATE TABLE "system_attributes" (
                                     "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     "name" text NOT NULL,
                                     "is_present" boolean NOT NULL,
                                     "system_id" integer NOT NULL
);

CREATE TABLE "system_attributes__parameter_modifiers" (
                                                          "system_attribute_id" integer,
                                                          "parameter_modifier_id" integer,
                                                          PRIMARY KEY ("system_attribute_id", "parameter_modifier_id")
);

CREATE TABLE "attribute_constraints" (
                                         "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                         "has_tag" boolean NOT NULL,
                                         "tag_id" integer NOT NULL,
                                         "attribute_id" integer NOT NULL
);

CREATE TABLE "characters" (
                              "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                              "name" text NOT NULL,
                              "description" text,
                              "game_system_id" integer NOT NULL,
                              "session_id" integer,
                              "user_id" integer NOT NULL
);

CREATE TABLE "character_slots" (
                                   "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                   "name" text NOT NULL,
                                   "icon_url" text,
                                   "is_whitelisted" boolean NOT NULL,
                                   "character_id" integer NOT NULL,
                                   "item_id" integer
);

CREATE TABLE "character_slots__tags" (
                                         "slot_id" integer,
                                         "tag_id" integer,
                                         PRIMARY KEY ("slot_id", "tag_id")
);

CREATE TABLE "character__attributes" (
                                         "character_id" integer,
                                         "attribute_id" integer,
                                         PRIMARY KEY ("character_id", "attribute_id")
);

CREATE TABLE "admins" (
                          "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                          "username" text UNIQUE NOT NULL,
                          "email" text UNIQUE,
                          "password" text NOT NULL
);

CREATE TABLE "user_bans" (
                             "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                             "user_id" integer NOT NULL,
                             "admin_id" integer NOT NULL,
                             "blocked_at" timestamp NOT NULL,
                             "unblocked_at" timestamp,
                             "reason" text NOT NULL
);

CREATE TABLE "sessions" (
                            "id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                            "name" text NOT NULL,
                            "description" text,
                            "max_number_of_players" integer NOT NULL,
                            "creator_as_player" boolean NOT NULL,
                            "connection_link" text UNIQUE NOT NULL,
                            "created_at" timestamp NOT NULL,
                            "system_id" integer NOT NULL,
                            "creator_id" integer NOT NULL
);

CREATE TABLE "users__sessions" (
                                   "user_id" integer,
                                   "session_id" integer,
                                   PRIMARY KEY ("user_id", "session_id")
);

CREATE UNIQUE INDEX ON "systems" ("name", "owner_id");

CREATE UNIQUE INDEX ON "system_parameters" ("name", "system_id");

CREATE UNIQUE INDEX ON "system_items" ("name", "system_id");

CREATE UNIQUE INDEX ON "system_tags" ("name", "system_id");

CREATE UNIQUE INDEX ON "parameter_modifiers" ("name", "parameter_id");

CREATE UNIQUE INDEX ON "system_attributes" ("name", "system_id");

ALTER TABLE "systems" ADD FOREIGN KEY ("owner_id") REFERENCES "users" ("id");

ALTER TABLE "systems" ADD FOREIGN KEY ("parent_system_id") REFERENCES "systems" ("id");

ALTER TABLE "system_parameters" ADD FOREIGN KEY ("system_id") REFERENCES "systems" ("id");

ALTER TABLE "system_items" ADD FOREIGN KEY ("system_id") REFERENCES "systems" ("id");

ALTER TABLE "system_tags" ADD FOREIGN KEY ("system_id") REFERENCES "systems" ("id");

ALTER TABLE "system_items__system_tags" ADD FOREIGN KEY ("system_item_id") REFERENCES "system_items" ("id");

ALTER TABLE "system_items__system_tags" ADD FOREIGN KEY ("system_tag_id") REFERENCES "system_tags" ("id");

ALTER TABLE "parameter_modifiers" ADD FOREIGN KEY ("parameter_id") REFERENCES "system_parameters" ("id");

ALTER TABLE "system_items__parameter_modifiers" ADD FOREIGN KEY ("system_item_id") REFERENCES "system_items" ("id");

ALTER TABLE "system_items__parameter_modifiers" ADD FOREIGN KEY ("parameter_modifier_id") REFERENCES "parameter_modifiers" ("id");

ALTER TABLE "system_attributes" ADD FOREIGN KEY ("system_id") REFERENCES "systems" ("id");

ALTER TABLE "system_attributes__parameter_modifiers" ADD FOREIGN KEY ("system_attribute_id") REFERENCES "system_attributes" ("id");

ALTER TABLE "system_attributes__parameter_modifiers" ADD FOREIGN KEY ("parameter_modifier_id") REFERENCES "parameter_modifiers" ("id");

ALTER TABLE "attribute_constraints" ADD FOREIGN KEY ("tag_id") REFERENCES "system_tags" ("id");

ALTER TABLE "attribute_constraints" ADD FOREIGN KEY ("attribute_id") REFERENCES "system_attributes" ("id");

ALTER TABLE "characters" ADD FOREIGN KEY ("game_system_id") REFERENCES "systems" ("id");

ALTER TABLE "characters" ADD FOREIGN KEY ("session_id") REFERENCES "sessions" ("id");

ALTER TABLE "characters" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "character_slots" ADD FOREIGN KEY ("character_id") REFERENCES "characters" ("id");

ALTER TABLE "character_slots" ADD FOREIGN KEY ("item_id") REFERENCES "system_items" ("id");

ALTER TABLE "character_slots__tags" ADD FOREIGN KEY ("slot_id") REFERENCES "character_slots" ("id");

ALTER TABLE "character_slots__tags" ADD FOREIGN KEY ("tag_id") REFERENCES "system_tags" ("id");

ALTER TABLE "character__attributes" ADD FOREIGN KEY ("character_id") REFERENCES "characters" ("id");

ALTER TABLE "character__attributes" ADD FOREIGN KEY ("attribute_id") REFERENCES "system_attributes" ("id");

ALTER TABLE "user_bans" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_bans" ADD FOREIGN KEY ("admin_id") REFERENCES "admins" ("id");

ALTER TABLE "sessions" ADD FOREIGN KEY ("system_id") REFERENCES "systems" ("id");

ALTER TABLE "sessions" ADD FOREIGN KEY ("creator_id") REFERENCES "users" ("id");

ALTER TABLE "users__sessions" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "users__sessions" ADD FOREIGN KEY ("session_id") REFERENCES "sessions" ("id");
