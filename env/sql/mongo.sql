/* 사용자 계정 생성 */
use admin


db.createUser({
    user: "firefly",
    pwd: "qkseltqnfdl",
    roles: ["readWrite"]
})

use btaka_user
use btaka_board_dev
use test

db.grantRolesToUser("firefly",
    [
        {role: "readWrite", db: "btaka_user"}
    ])

db.grantRolesToUser("firefly",
    [
        {role: "readWrite", db: "btaka_board_dev"}
    ])

db.btaka_user.insertOne(
  {
    "address": "경기 용인시 수지구 현암로125번길 7",
    "addressDetail": "12121",
    "email": "bandisnc@gmail.com",
    "gender": "M",
    "mobile": "01041232131",
    "password": "$2a$10$hJp3ovGtL/nI/30vDNENieLg55DHbaLR4hYs5YDRKJZDQO/MkdmiK",
    "postNum": "16875",
    "roles": "ROLE_USER",
    "username": "김동규"
  })

db.btaka_user_oauth.insertMany([
    {
        "email": "roseoutz@naver.com",
        "oauthId": "2130950816",
        "oauthSite": "kakao",
        "userOid": "62136192e4f442352558e735"
    },
    {
        "email": "roseoutz",
        "oauthId": "29092884",
        "oauthSite": "github",
        "userOid": "62136192e4f442352558e735"
    },
    {
        "email": "zeedoutladzz@gmail.com",
        "oauthId": "108639397108192419664",
        "oauthSite": "google",
        "userOid": "62136192e4f442352558e735"
    }
])

db.btaka_config.insertMany([
{
  "key": "auth.token.secret",
  "value": "btaka-jwt-service-hahahahahahahaha",
  "group": "auth.token",
  "description": "Token Secret 값"
},
{
  "key": "auth.token.max.valid.time",
  "value": "3600",
  "group": "auth.token",
  "description": "Token 최대 유효 시간"
}]
)