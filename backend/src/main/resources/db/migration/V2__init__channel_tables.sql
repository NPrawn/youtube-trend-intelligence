create table channel
(
    id                  bigserial primary key,
    youtube_channel_id  varchar(64) not null unique,
    title               varchar(255) not null,
    created_at          timestamptz not null default now(),
    updated_at          timestamptz not null default now()
);

create table channel_snapshot
(
    id                      bigserial primary key,
    channel_id              bigint     not null references channel (id),
    collected_at            timestamptz not null,
    subscriber_count        bigint,
    hidden_subscriber_count boolean    not null,
    view_count              bigint     not null,
    video_count             bigint     not null,
    created_at              timestamptz not null default now()
);

create index idx_channel_snapshot_channel_id on channel_snapshot (channel_id);
create index idx_channel_snapshot_collected_at on channel_snapshot (collected_at);
