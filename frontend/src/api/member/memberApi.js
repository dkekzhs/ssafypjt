import apiClient from "@/util/axiosConfig";

function memberLogin(body, success, fail) {
  apiClient.post("/user/login", body).then(success).catch(fail);
}

function memberLogout(body, success, fail) {
  apiClient.post("/user/logout", body).then(success).catch(fail);
}

function memberJwtLogin(body, success, fail) {
  apiClient.post("/user/jwtlogin", body).then(success).catch(fail);
}


function memberRegist(body, success, fail) {
  apiClient.post("/user/insert", body).then(success).catch(fail);
}


 async function getPublicKey() {
   return await apiClient.post("/user/getPublicKey");
}


export { memberLogin, memberLogout, memberJwtLogin,getPublicKey,memberRegist};
