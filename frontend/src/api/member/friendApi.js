import apiClient from "@/util/axiosConfig";

function friendPendingCount(success, fail) {
  apiClient.get("/friend/count").then(success).catch(fail);
}

function friendPending(success, fail) {
  apiClient.get("/friend/friendRequestPending").then(success).catch(fail);
}
function friendAccept(from, success, fail) {
  apiClient.post("/friend/accept", from).then(success).catch(fail);
}
function friendList( success, fail) {
  apiClient.get("/friend/myfriend").then(success).catch(fail);
}

function addFriend(data, success, fail) {
  apiClient.post("/friend/add" , data).then(success).catch(fail);
}
function refuseFriend(data, success, fail) {
  apiClient.post("/friend/refuse", data).then(success).catch(fail);
}

export { friendPending ,friendPendingCount, friendAccept,friendList, addFriend, refuseFriend};