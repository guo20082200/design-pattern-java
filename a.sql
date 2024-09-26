CREATE TABLE "public"."im_friend"
(
    "id"                int8                                        NOT NULL DEFAULT nextval('im_friend_seq'::regclass),
    "user_id"           varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "friend_id"         varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "friend_nick_name"  varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "friend_head_image" text COLLATE "pg_catalog"."default",
    "create_time"       timestamp(0),
    "tenant_id"         int8,
    "update_time"       timestamp(0),
    "update_user"       varchar(255) COLLATE "pg_catalog"."default",
    "create_user"       varchar(255) COLLATE "pg_catalog"."default",
    "friend_tenant_id"  int8,
    "head_image_thumb"  text COLLATE "pg_catalog"."default",
    "user_remark"       varchar(255) COLLATE "pg_catalog"."default",
    "friend_remark"     varchar(255) COLLATE "pg_catalog"."default",
    CONSTRAINT "im_friend_pkey" PRIMARY KEY ("id")
);