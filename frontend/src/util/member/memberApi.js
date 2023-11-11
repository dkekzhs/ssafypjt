import  apiClient  from "@/util/axiosConfig";



function memberLogin(param, success, fail) {
    apiClient.post("/user/login", { params: param }).then(success).catch(fail);

}

export {
    memberLogin,
}