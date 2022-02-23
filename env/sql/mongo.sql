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