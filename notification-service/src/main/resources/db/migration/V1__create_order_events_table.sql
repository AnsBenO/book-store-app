CREATE SEQUENCE order_event_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE order_events
(
    id           BIGINT DEFAULT NEXTVAL('order_event_id_seq') NOT NULL,
    event_id     text                                         NOT NULL UNIQUE,
    created_at   TIMESTAMP                                    NOT NULL,
    updated_at   TIMESTAMP,
    PRIMARY KEY (id)
);