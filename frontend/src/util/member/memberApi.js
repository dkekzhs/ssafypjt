import  apiClient  from "@/util/axiosConfig";



function memberLogin(body, success, fail) {
    apiClient.post("/user/login", body).then(success).catch(fail);

}

export {
    memberLogin,
}