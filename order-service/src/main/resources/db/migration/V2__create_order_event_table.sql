CREATE SEQUENCE order_event_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE order_events
(
    id           BIGINT DEFAULT NEXTVAL('order_event_id_seq') NOT NULL,
    order_number text                                         NOT NULL REFERENCES orders (order_number),
    event_id     text                                         NOT NULL UNIQUE,
    event_type   text                                         NOT NULL,
    payload      text                                         NOT NULL,
    created_at   TIMESTAMP                                    NOT NULL,
    updated_at   TIMESTAMP,
    PRIMARY KEY (id)
);