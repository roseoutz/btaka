// 사용자 계정 생성
use admin

db.createUser({
    user: "firefly",
    pwd: "qkseltqnfdl",
    roles: ["dbAdminAnyDatabase"]
})
