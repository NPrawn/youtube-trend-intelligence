create table video
(
    id                 bigserial primary key,
    youtube_video_id   varchar(32)  not null unique,
    title              varchar(500) not null,
    youtube_channel_id varchar(64)  not null,
    published_at       timestamptz  not null,
    duration_seconds   integer      not null,
    is_short_form      boolean      not null,
    create_at          timestamptz  not null default now(),
    updated_at         timestamptz  not null default now()
);

create table video_snapshot
(
    id              bigserial primary key,
    video_id        bigint      not null references video (id),
    collected_at    timestamptz not null,
    view_count      bigint      not null,
    like_count      bigint,
    comment_count   bigint,
    source_region   varchar(8)  not null,
    source_category varchar(32),
    source_rank     integer,
    created_at      timestamptz not null default now()
);

create index idx_video_snapshot_video_id on video_snapshot(video_id);
create index idx_video_snapshot_collected_at on video_snapshot (collected_at);
create index idx_video_snapshot_source_region on video_snapshot (source_region);
