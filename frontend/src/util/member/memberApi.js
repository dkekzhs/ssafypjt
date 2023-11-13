import apiClient from "@/util/axiosConfig";

function memberLogin(body, success, fail) {
  apiClient.post("/user/login", body).then(success).catch(fail);
}

function memberLogout(body, success, fail) {
  apiClient.post("/user/logout", body).then(success).catch(fail);
}

export { memberLogin, memberLogout };
